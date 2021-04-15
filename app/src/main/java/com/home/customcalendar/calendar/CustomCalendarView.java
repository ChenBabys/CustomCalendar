package com.home.customcalendar.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.home.customcalendar.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ChenYesheng On 2021/4/9 9:28
 * Desc: 自定义日历~
 */
public class CustomCalendarView extends LinearLayoutCompat {
    private int titleTextColor, dayTextColor, daySelectedBackgroundColor, todayBackgroundColor,
            weekEndTextColor, selectedBetweenBackgroundColor;
    private float dayTextSize, titleTextSize;
    private boolean isShowAfterTodayDate;
    private OnDateSelected onDateSelected;//选中监听
    private Integer showYearCount;
    private DateEntity startDate;//开始时间
    private DateEntity endDate;//结束时间
    private CalendarRvViewAdapter adapter;
    private RecyclerView calendarRvView;
    @SuppressLint("SimpleDateFormat")
    //格式化本VIew返回给接口的时间。
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");


    public CustomCalendarView(@NonNull Context context) {
        super(context);
        initAttr(context, null, 0);
    }

    public CustomCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs, 0);
    }

    public CustomCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    @SuppressLint("RestrictedApi")
    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.CustomCalendarView, defStyleAttr, 0);
            titleTextColor = tintTypedArray.getColor(R.styleable.CustomCalendarView_titleTextColor, Color.parseColor("#000000"));
            dayTextColor = tintTypedArray.getColor(R.styleable.CustomCalendarView_dayTextColor, Color.parseColor("#000000"));
            daySelectedBackgroundColor = tintTypedArray.getColor(R.styleable.CustomCalendarView_daySelectedBackgroundColor, Color.parseColor("#3A8AFD"));
            selectedBetweenBackgroundColor = tintTypedArray.getColor(R.styleable.CustomCalendarView_selectedBetweenBackgroundColor, Color.parseColor("#E9F3FF"));
            todayBackgroundColor = tintTypedArray.getColor(R.styleable.CustomCalendarView_todayBackgroundColor, Color.parseColor("#3A8AFD"));
            weekEndTextColor = tintTypedArray.getColor(R.styleable.CustomCalendarView_weekEndTextColor, Color.parseColor("#208CF9"));
            dayTextSize = tintTypedArray.getDimension(R.styleable.CustomCalendarView_dayTextSize, dp2px(6));
            titleTextSize = tintTypedArray.getDimension(R.styleable.CustomCalendarView_titleTextSize, dp2px(6));
            isShowAfterTodayDate = tintTypedArray.getBoolean(R.styleable.CustomCalendarView_isShowAfterTodayDate, false);//默认隐藏
            showYearCount = tintTypedArray.getInteger(R.styleable.CustomCalendarView_showYearCount, 2);//默认两年
            tintTypedArray.recycle();
        }
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
        setBackground(ContextCompat.getDrawable(context, R.color.white));
        LinearLayoutCompat mWeekLine = new LinearLayoutCompat(context);
        mWeekLine.setOrientation(LinearLayoutCompat.HORIZONTAL);
        mWeekLine.setPadding(dp2px(5), 0, dp2px(5), 0);
        mWeekLine.setWeightSum(7);
        mWeekLine.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        //星期
        for (int weekItem = 0; weekItem < 7; weekItem++) {
            TextView weekView = new TextView(context);
            weekView.setText(CalendarTools.weekDayRow[weekItem]);
            weekView.getPaint().setFakeBoldText(true);//粗体
            weekView.setGravity(Gravity.CENTER);
            weekView.setPadding(dp2px(5), dp2px(10), dp2px(5), dp2px(10));
            LayoutParams textViewParam = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
            textViewParam.weight = 1;
            weekView.setLayoutParams(textViewParam);
            weekView.setTextSize(sp2px(6));
            if (CalendarTools.weekDayRow[weekItem].equals(CalendarTools.SUNDAY) | CalendarTools.weekDayRow[weekItem].equals(CalendarTools.SATURDAY)) {
                weekView.setTextColor(getWeekEndTextColor());//蓝色
            } else {
                weekView.setTextColor(Color.parseColor("#6A6A6B"));//偏黑色
            }
            mWeekLine.addView(weekView);
        }
        View line = new View(context);
        line.setPadding(0, dp2px(5), 0, 0);
        line.setBackgroundColor(Color.parseColor("#DBDDDF"));
        line.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
        //日历月份标题和网格日历视图
        calendarRvView = new RecyclerView(context);
        calendarRvView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        calendarRvView.setId(generateViewId());
        calendarRvView.setPadding(dp2px(5), 0, dp2px(5), dp2px(100));
        calendarRvView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        //适配器
        adapter = new CalendarRvViewAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(context, 7);
        //设置每行的格子数目~
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (DateEntity.item_type_month ==
                        adapter.mDataList.get(position).getItemType()) return 7;
                else return 1;
            }
        });
        calendarRvView.setLayoutManager(layoutManager);
        calendarRvView.addItemDecoration(new CalendarItemDecoration());
        calendarRvView.setAdapter(adapter);
        setShowYearCount(showYearCount);//下面这两句放到这里面执行
//        adapter.setDateList(CalendarTools.getInstance().getDateEntities(Calendar.getInstance().getTime(), getShowYearCount()));
//        //默认显示到最后一项
//        calendarRvView.scrollToPosition(adapter.getItemCount() - 1);
        adapter.setOnItemClickListener((view, position) -> {
            onItemClick(adapter, adapter.mDataList.get(position));
        });
        //添加到跟布局
        addView(mWeekLine);
        addView(line);
        addView(calendarRvView);
    }

    /**
     * 点击事件的处理
     *
     * @param dateEntity
     */
    private void onItemClick(CalendarRvViewAdapter adapter, DateEntity dateEntity) {
        //如果点击的是月份或者天数为空的时候则点击无效。
        if (dateEntity.getItemType() == DateEntity.item_type_month || TextUtils.isEmpty(dateEntity.getDay())) {
            return;
        }
        if (startDate == null) {
            startDate = dateEntity;
            dateEntity.setItemState(DateEntity.ITEM_STATE_BEGIN_DATE);
        } else if (endDate == null) {
            //如果选中了开始日期但没有选中结束日期，本次操作选中结束日期
            //如果当前点击的结束日期跟开始日期一致 则不做操作
            if (startDate == dateEntity) {

            } else if (dateEntity.getDate().getTime() < startDate.getDate().getTime()) {
                //当前点选的日期小于当前选中的开始日期 则本次操作重新选中开始日期
                startDate.setItemState(DateEntity.ITEM_STATE_NORMAL);
                startDate = dateEntity;
                startDate.setItemState(DateEntity.ITEM_STATE_BEGIN_DATE);
            } else {
                //选中结束日期
                endDate = dateEntity;
                endDate.setItemState(DateEntity.ITEM_STATE_END_DATE);
                createSelectState(adapter);
                if (onDateSelected != null) {
                    onDateSelected.selected(simpleDateFormat.format(startDate.getDate()), simpleDateFormat.format(endDate.getDate()));
                }
            }
        } else {
            //结束日期和开始日期都已选中
            clearSelectState(adapter);
            //重新选择开始日期,结束日期清除
            startDate.setItemState(DateEntity.ITEM_STATE_NORMAL);
            startDate = dateEntity;
            startDate.setItemState(DateEntity.ITEM_STATE_BEGIN_DATE);

            endDate.setItemState(DateEntity.ITEM_STATE_NORMAL);
            endDate = null;
        }
        //刷新日历
        adapter.notifyDataSetChanged();
    }

    /**
     * 选中中间的日期
     */
    private void createSelectState(CalendarRvViewAdapter adapter) {
        if (endDate != null && startDate != null) {
            int start = adapter.mDataList.indexOf(startDate);
            start += 1;
            int end = adapter.mDataList.indexOf(endDate);
            for (; start < end; start++) {
                DateEntity entity = adapter.mDataList.get(start);
                if (!TextUtils.isEmpty(entity.getDay())) {
                    entity.setItemState(DateEntity.ITEM_STATE_SELECTED);
                }
            }
        }
    }

    /**
     * 取消选中状态
     */
    private void clearSelectState(CalendarRvViewAdapter adapter) {
        if (endDate != null && startDate != null) {
            int start = adapter.mDataList.indexOf(startDate);
            start += 1;
            int end = adapter.mDataList.indexOf(endDate);
            for (; start < end; start++) {
                DateEntity entity = adapter.mDataList.get(start);
                entity.setItemState(DateEntity.ITEM_STATE_NORMAL);
            }
        }
    }


    /**
     * 日历的适配器
     */
    protected class CalendarRvViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        /**
         * 日期实体集合
         */
        protected List<DateEntity> mDataList;
        private OnItemClickListener onItemClickListener;

        public OnItemClickListener getOnRecyclerviewItemClick() {
            return onItemClickListener;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        /**
         * 设置日期数据
         */
        public void setDateList(List<DateEntity> dataList) {
            this.mDataList = dataList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == DateEntity.item_type_day) {
                View rootView = createDayView(parent);
                DayViewHolder dayViewHolder = new DayViewHolder(rootView);
                dayViewHolder.setIsRecyclable(false);//不给复用，否则上下大幅度滑动时候有几率出现错乱的填充项
                dayViewHolder.itemView.setOnClickListener(v -> {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, dayViewHolder.getLayoutPosition());
                    }
                });
                return dayViewHolder;

            } else if (viewType == DateEntity.item_type_month) {
                View rootView = createMonthTitleView(parent);
                MonthViewHolder monthViewHolder = new MonthViewHolder(rootView);
                monthViewHolder.setIsRecyclable(false);//不给复用，否则上下大幅度滑动时候有几率出现错乱的填充项
                monthViewHolder.itemView.setOnClickListener(v -> {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, monthViewHolder.getLayoutPosition());
                    }
                });
                return monthViewHolder;
            }
            return null;
        }


        private View createMonthTitleView(@NonNull ViewGroup parent) {
            TextView mMonthTitle = new TextView(parent.getContext());
            mMonthTitle.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(50)));
            mMonthTitle.setTextColor(getTitleTextColor());
            //标题项加分割线
            GradientDrawable drawable = new GradientDrawable();
            drawable.setStroke(1, Color.parseColor("#E2E2E2"));
            mMonthTitle.setBackground(drawable);
            mMonthTitle.setSingleLine();
            mMonthTitle.setIncludeFontPadding(false);//去除默认边距
            mMonthTitle.getPaint().setFakeBoldText(true);//粗体
            mMonthTitle.setTextSize(getTitleTextSize());
            mMonthTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mMonthTitle.setGravity(Gravity.CENTER);
            return mMonthTitle;
        }

        private View createDayView(@NonNull ViewGroup parent) {
            LinearLayoutCompat mViewParent = new LinearLayoutCompat(parent.getContext());
            mViewParent.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(50)));
            mViewParent.setGravity(Gravity.CENTER);
            //mViewParent.setId(generateViewId());
            mViewParent.setBackgroundColor(Color.WHITE);
            mViewParent.setOrientation(VERTICAL);
            //天
            TextView dayView = new TextView(parent.getContext());
            dayView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            dayView.setTextColor(getDayTextColor());
            dayView.setSingleLine();
            //dayView.setId(generateViewId());
            dayView.setTextSize(getDayTextSize());
            dayView.setIncludeFontPadding(false);//去除默认边距
            dayView.getPaint().setFakeBoldText(true);//粗体
            dayView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            dayView.setGravity(Gravity.CENTER);
            //底部的标记文本（开始或者结束）
            TextView mCheckSignText = new TextView(parent.getContext());
            LayoutParams mCheckSignTextParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mCheckSignTextParam.topMargin = dp2px(2);
            mCheckSignText.setLayoutParams(mCheckSignTextParam);
            mCheckSignText.setTextColor(Color.WHITE);
            mCheckSignText.setSingleLine();
            // mCheckSignText.setId(generateViewId());
            mCheckSignText.setVisibility(GONE);//默认是隐藏的
            mCheckSignText.setTextSize(sp2px(4));
            mCheckSignText.setIncludeFontPadding(false);//去除默认边距
            mCheckSignText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mCheckSignText.setGravity(Gravity.CENTER);
            mViewParent.addView(dayView);
            mViewParent.addView(mCheckSignText);
            return mViewParent;
        }


        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MonthViewHolder) {
                DateEntity entity = mDataList.get(position);
                ((MonthViewHolder) holder).onBindMonthTitle(entity);
            } else {
                DateEntity entity = mDataList.get(position);
                ((DayViewHolder) holder).onBindDay(entity);
            }
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mDataList.get(position).getItemType();
        }


        protected class DayViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvDay;
            private final TextView mCheckSignText;

            public DayViewHolder(@NonNull View itemView) {
                super(itemView);
                LinearLayoutCompat layoutCompat = (LinearLayoutCompat) itemView;
                tvDay = (TextView) layoutCompat.getChildAt(0);
                mCheckSignText = (TextView) layoutCompat.getChildAt(1);
            }

            protected void onBindDay(DateEntity entity) {
                tvDay.setText(entity.getDay());
                mCheckSignText.setVisibility(GONE);
                //如果是开始或者结束
                if (entity.getItemState() == DateEntity.ITEM_STATE_BEGIN_DATE || entity.getItemState() == DateEntity.ITEM_STATE_END_DATE) {
                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setColor(getDaySelectedBackgroundColor());
                    //设置图片四个角圆形半径：1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
                    //drawable.setCornerRadii(new float[]{dp2px(5), dp2px(5), 0, 0, 0, 0, dp2px(5), dp2px(5)});
                    drawable.setCornerRadius(dp2px(2));
                    itemView.setBackground(drawable);
                    tvDay.setTextColor(Color.WHITE);
                    mCheckSignText.setVisibility(VISIBLE);
                    if (entity.getItemState() == DateEntity.ITEM_STATE_END_DATE) {
                        mCheckSignText.setText("结束");
                    } else {
                        mCheckSignText.setText("开始");
                    }
                } else if (entity.getItemState() == DateEntity.ITEM_STATE_SELECTED) {
                    //选中开始和结束，中间日期的状态
                    itemView.setBackgroundColor(getSelectedBetweenBackgroundColor());
                    tvDay.setTextColor(Color.BLACK);
                    //如果是今天
                    if (entity.isIsToday()) {
                        tvDay.setText("今天");
                        tvDay.setTextColor(Color.parseColor("#3A8AFD"));//这个有待商榷，因为设计图中不变色，依旧是黑色
                    }
                } else if (entity.isIsToday()) {
                    //如果是今天
                    tvDay.setText("今天");
                    GradientDrawable drawable = new GradientDrawable();
                    drawable.setColor(getTodayBackgroundColor());
                    //设置图片四个角圆形半径：1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
                    //drawable.setCornerRadii(new float[]{dp2px(5), dp2px(5), 0, 0, 0, 0, dp2px(5), dp2px(5)});
                    drawable.setCornerRadius(dp2px(2));
                    itemView.setBackground(drawable);
                    tvDay.setTextColor(Color.WHITE);
                } else if (entity.isAfterToday() && !isShowAfterTodayDate()) {
                    //如果是今天之后的日期
                    tvDay.setClickable(false);
                    tvDay.setEnabled(false);
                    itemView.setClickable(false);
                    itemView.setEnabled(false);
                    tvDay.setTextColor(Color.parseColor("#CDCDCD"));//天不可用的时候的填充颜色！
                } else if (entity.isAreWeekEnd()) {
                    //如果是周末
                    tvDay.setTextColor(getWeekEndTextColor());
                } else {
                    //正常状态
                    itemView.setBackgroundColor(Color.WHITE);
                    tvDay.setTextColor(getDayTextColor());
                }
            }
        }

        protected class MonthViewHolder extends RecyclerView.ViewHolder {
            public TextView tvMonth;

            public MonthViewHolder(@NonNull View itemView) {
                super(itemView);
                tvMonth = (TextView) itemView;
            }

            protected void onBindMonthTitle(DateEntity entity) {
                tvMonth.setText(entity.getMonthStr());
            }
        }
    }

    protected interface OnItemClickListener {
        void onItemClick(View v, int position);
    }


    protected int dp2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    protected int sp2px(float spValue) {
        float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    public interface OnDateSelected {
        void selected(String startDate, String endDate);
    }

    public void setOnDateSelected(OnDateSelected onDateSelected) {
        this.onDateSelected = onDateSelected;
    }


    /**
     * 属性设置方法外露给控件
     *
     * @return
     */
    private int getTitleTextColor() {
        return titleTextColor;
    }

    /**
     * 设置标题的文本颜色
     *
     * @param titleTextColor
     */
    public void setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
    }

    private int getDayTextColor() {
        return dayTextColor;
    }

    /**
     * 设置今天的文本颜色
     *
     * @param dayTextColor
     */
    public void setDayTextColor(int dayTextColor) {
        this.dayTextColor = dayTextColor;
    }

    private int getDaySelectedBackgroundColor() {
        return daySelectedBackgroundColor;
    }

    /**
     * 设置今天被选中后的背景色
     *
     * @param daySelectedBackgroundColor
     */
    public void setDaySelectedBackgroundColor(int daySelectedBackgroundColor) {
        this.daySelectedBackgroundColor = daySelectedBackgroundColor;
    }

    private int getTodayBackgroundColor() {
        return todayBackgroundColor;
    }

    /**
     * 设置今天的背景色
     *
     * @param todayBackgroundColor
     */
    public void setTodayBackgroundColor(int todayBackgroundColor) {
        this.todayBackgroundColor = todayBackgroundColor;
    }

    private int getWeekEndTextColor() {
        return weekEndTextColor;
    }

    /**
     * 设置周末的文本颜色
     *
     * @param weekEndTextColor
     */
    public void setWeekEndTextColor(int weekEndTextColor) {
        this.weekEndTextColor = weekEndTextColor;
    }

    private int getSelectedBetweenBackgroundColor() {
        return selectedBetweenBackgroundColor;
    }

    /**
     * 设置选中开始和结束后中间的日期背景色
     *
     * @param selectedBetweenBackgroundColor
     */
    public void setSelectedBetweenBackgroundColor(int selectedBetweenBackgroundColor) {
        this.selectedBetweenBackgroundColor = selectedBetweenBackgroundColor;
    }

    private float getDayTextSize() {
        return dayTextSize;
    }

    /**
     * 设置今天的文本大小
     *
     * @param dayTextSize
     */
    public void setDayTextSize(float dayTextSize) {
        this.dayTextSize = dayTextSize;
    }

    private float getTitleTextSize() {
        return titleTextSize;
    }

    /**
     * 设置标题文本大小
     *
     * @param titleTextSize
     */
    public void setTitleTextSize(float titleTextSize) {
        this.titleTextSize = titleTextSize;
    }

    private boolean isShowAfterTodayDate() {
        return isShowAfterTodayDate;
    }

    /**
     * 是否显示今天之后的日期
     *
     * @param showAfterTodayDate
     */
    public void setShowAfterTodayDate(boolean showAfterTodayDate) {
        isShowAfterTodayDate = showAfterTodayDate;
    }

    private Integer getShowYearCount() {
        return showYearCount;
    }

    /**
     * 设置从当前月份往回数，显示的年数
     *
     * @param showYearCount
     */
    public void setShowYearCount(Integer showYearCount) {
        this.showYearCount = showYearCount;
        adapter.setDateList(CalendarTools.getInstance().getDateEntities(Calendar.getInstance().getTime(), getShowYearCount()));
        calendarRvView.scrollToPosition(adapter.getItemCount() - 1);
    }
}
