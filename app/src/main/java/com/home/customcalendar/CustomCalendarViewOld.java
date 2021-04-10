package com.home.customcalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.ScreenUtils;
import com.home.customcalendar.calendar.DateEntity;

import java.util.List;

/**
 * Created by ChenYesheng On 2021/4/9 9:28
 * Desc:
 */
@Deprecated
class CustomCalendarViewOld extends LinearLayoutCompat {
//    private CalendarTool mCalendarTool = new CalendarTool(getContext());
//    //private Point mNowCalendarPoint = mCalendarTool.getNowCalendar();
//    private List<YearMonthEntity> mDateEntityList;
//    private ViewPager2 viewPager2;


    public CustomCalendarViewOld(@NonNull Context context) {
        super(context);
        initAttr(context, null, 0);
    }

    public CustomCalendarViewOld(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs, 0);
    }

    public CustomCalendarViewOld(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {

        //mDateEntityList = mCalendarTool.getDateEntityList(mNowCalendarPoint.x, mNowCalendarPoint.y);
//        mDateEntityList = mCalendarTool.getCalendarList();//获取日历
//        initView(context);
    }
//
//    private void initView(Context context) {
//        setOrientation(VERTICAL);
//        setBackground(ContextCompat.getDrawable(context, R.color.white));
//        LinearLayoutCompat mWeekLine = new LinearLayoutCompat(context);
//        mWeekLine.setOrientation(LinearLayoutCompat.HORIZONTAL);
//        mWeekLine.setWeightSum(7);
//        mWeekLine.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        //星期
//        for (int weekItem = 0; weekItem < 7; weekItem++) {
//            TextView weekView = new TextView(context);
//            weekView.setText(CalendarTool.weekDayRow[weekItem]);
//            weekView.getPaint().setFakeBoldText(true);//粗体
//            weekView.setGravity(Gravity.CENTER);
//            weekView.setPadding(dp2px(5), dp2px(10), dp2px(5), dp2px(10));
//            LayoutParams textViewParam = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
//            textViewParam.weight = 1;
//            weekView.setLayoutParams(textViewParam);
//            weekView.setTextSize(sp2px(6));
//            if (CalendarTool.weekDayRow[weekItem].equals(CalendarTool.SUNDAY) | CalendarTool.weekDayRow[weekItem].equals(CalendarTool.SATURDAY)) {
//                weekView.setTextColor(Color.parseColor("#208CF9"));//蓝色
//            } else {
//                weekView.setTextColor(Color.parseColor("#6A6A6B"));//偏黑色
//            }
//            mWeekLine.addView(weekView);
//        }
//        View line = new View(context);
//        line.setPadding(0, dp2px(5), 0, 0);
//        line.setBackgroundColor(Color.parseColor("#DBDDDF"));
//        line.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
//        //设置可滑动
//        viewPager2 = new ViewPager2(context);
//        viewPager2.setOverScrollMode(OVER_SCROLL_NEVER);
//        viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
//        viewPager2.setId(generateViewId());
//        LayoutParams viewPagerParam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//        viewPager2.setLayoutParams(viewPagerParam);
//        addView(mWeekLine);
//        addView(line);
//        addView(viewPager2);
//        viewPager2.setAdapter(new Pager2Adapter());
//        //设置一屏多页
//        viewPager2.setOffscreenPageLimit(1);
//        //重新获取到rv控件,重设部分属性，和LinearLayoutManager，并且设置反序排列（下标反序，最底部的项是下标0）
//        RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, true));
//        recyclerView.setPadding(0, 0, 0, (int) (ScreenUtils.getAppScreenHeight() / 2.2f));
//        recyclerView.setClipToPadding(false);
//        //设置当前显示项（设置了反序了，现在是正数显示到第二项了）
//        viewPager2.setCurrentItem(1);
//        //viewPager2.scrollTo(0,mDateEntityList.size()-2);//可不要
//    }
//
//    /**
//     * 初始化日历
//     *
//     * @param context
//     */
//    private View createCalendar(Context context) {
//        LinearLayoutCompat mCalendarBlock = new LinearLayoutCompat(context);
//        mCalendarBlock.setOrientation(LinearLayoutCompat.VERTICAL);
//        mCalendarBlock.setId(generateViewId());
//        //这里要用MATCH_PARENT设置高度，否则出错。
//        mCalendarBlock.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//        TextView mCalendarTitle = new TextView(context);
//        mCalendarTitle.setId(generateViewId());
//        mCalendarTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        mCalendarTitle.setGravity(Gravity.CENTER);
//        mCalendarTitle.setTextColor(Color.parseColor("#000000"));
//        mCalendarTitle.setPadding(dp2px(5), dp2px(10), dp2px(5), dp2px(10));
//        mCalendarTitle.setTextSize(sp2px(7));
//        View line2 = new View(context);
//        line2.setPadding(0, dp2px(5), 0, 0);
//        line2.setBackgroundColor(Color.parseColor("#DBDDDF"));
//        line2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
//        //日历网格视图
//        RecyclerView calendarRvView = new RecyclerView(context);
//        calendarRvView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        calendarRvView.setId(generateViewId());
//        calendarRvView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
//        calendarRvView.setLayoutManager(new GridLayoutManager(context, 7));
//        calendarRvView.addItemDecoration(new CalendarGridItemDecoration(7, dp2px(5), dp2px(5), dp2px(5)));
//        mCalendarBlock.addView(mCalendarTitle);
//        mCalendarBlock.addView(line2);
//        mCalendarBlock.addView(calendarRvView);
//        return mCalendarBlock;
//    }
//
//    /**
//     * 子项等框高间距
//     */
//    private static class CalendarGridItemDecoration extends RecyclerView.ItemDecoration {
//        private int spanCount, dividerWidthTop, dividerWidthBot, dividerWidth;
//
//        public CalendarGridItemDecoration(int spanCount, int dividerWidthTop, int dividerWidthBot, int dividerWidth) {
//            this.spanCount = spanCount;
//            this.dividerWidthTop = dividerWidthTop;
//            this.dividerWidthBot = dividerWidthBot;
//            this.dividerWidth = dividerWidth;
//        }
//
//
//        @Override
//        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
//            int pos = parent.getChildAdapterPosition(view);
//            int column = (pos) % spanCount + 1;// 计算这个child 处于第几列
//
//            outRect.top = dividerWidthTop;
//            outRect.bottom = dividerWidthBot;
//            //注意这里一定要先乘 后除  先除数因为小于1然后强转int后会为0
//            outRect.left = (column - 1) * dividerWidth / spanCount; //左侧为(当前条目数-1)/总条目数*divider宽度
//            outRect.right = (spanCount - column) * dividerWidth / spanCount;//右侧为(总条目数-当前条目数)/总条目数*divider宽度
//        }
//    }
//
//
//    /**
//     * viewpager的适配器
//     */
//    private class Pager2Adapter extends RecyclerView.Adapter<Pager2Adapter.Pager2ViewHolder> {
//
//        @NonNull
//        @Override
//        public Pager2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View mCalendar = createCalendar(parent.getContext());
//            return new Pager2ViewHolder(mCalendar);
//        }
//
//        @SuppressLint("SetTextI18n")
//        @Override
//        public void onBindViewHolder(@NonNull Pager2ViewHolder holder, int position) {
////            holder.title.setText(mNowCalendarPoint.x + "年"
////                    + mNowCalendarPoint.y + "月");
//            holder.title.setText(mDateEntityList.get(position).getCurrentYear() + "年"
//                    + mDateEntityList.get(position).getCurrentMonth() + "月");
//            holder.calendarRvView.setAdapter(new CalendarRvViewAdapter(mCalendarTool.getDateEntityList(mDateEntityList.get(position).getCurrentYear()
//                    , mDateEntityList.get(position).getCurrentMonth())));
//        }
//
//        @Override
//        public int getItemCount() {
//            return mDateEntityList.size();
//        }
//
//
//        private class Pager2ViewHolder extends RecyclerView.ViewHolder {
//            private LinearLayoutCompat layout;
//            private TextView title;
//            private RecyclerView calendarRvView;
//
//            public Pager2ViewHolder(@NonNull View itemView) {
//                super(itemView);
//                layout = itemView.findViewById(itemView.getId());
//                title = itemView.findViewById(layout.getChildAt(0).getId());
//                calendarRvView = itemView.findViewById(layout.getChildAt(2).getId());
//            }
//        }
//    }
//
//    /**
//     * 日历的网格适配器
//     */
//    private class CalendarRvViewAdapter extends RecyclerView.Adapter<CalendarRvViewAdapter.CalendarViewHolder> {
//        /**
//         * 日期实体集合
//         */
//        private List<DateEntity> mDataList;
//
//        public CalendarRvViewAdapter(List<DateEntity> mDataList) {
//            this.mDataList = mDataList;
//        }
//
//        /**
//         * 设置日期数据
//         */
//        public void setDateList(List<DateEntity> dataList) {
//            this.mDataList = dataList;
//        }
//
//        @NonNull
//        @Override
//        public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            CalendarTextView dayView = new CalendarTextView(parent.getContext());
//            dayView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp2px(50)));
//            dayView.setTextColor(Color.parseColor("#000000"));
//            dayView.setSingleLine();
//            dayView.setIncludeFontPadding(false);//去除默认边距
//            dayView.getPaint().setFakeBoldText(true);//粗体
//            dayView.setBackgroundColor(Color.WHITE);
//            dayView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            dayView.setGravity(Gravity.CENTER);
//            return new CalendarViewHolder(dayView);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
//            if (position >= 0 && position < mDataList.size()) {
//                DateEntity entity = mDataList.get(position);
//                holder.onBind(entity, position);
//                ///todo
//                //holder.setIsRecyclable(false);//不让recyclerview进行复用，复用会出问题
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return mDataList.size();
//        }
//
//        private class CalendarViewHolder extends RecyclerView.ViewHolder {
//            private CalendarTextView dayView;
////            private int mSelectTime = 0;//点击次数
////            private int startPos = -1;
////            private int endPos = -1;
//
//            public CalendarViewHolder(@NonNull View itemView) {
//                super(itemView);
//                dayView = (CalendarTextView) itemView;
//            }
//
//            protected void onBind(DateEntity entity, int position) {
//                dayView.setText(String.valueOf(entity.day));
//                //如果是周末
//                if (mCalendarTool.isWeekend(entity.weekDay)) {
//                    // 周末字体为天蓝色
//                    dayView.setTextColor(Color.parseColor("#208CF9"));
//                }
//                // 如果为当前天数，则设置为白色背景并，字体为蓝色
//                if (entity.isNowDate
//                        && entity.isSelfMonthDate) {
//                    GradientDrawable drawable = new GradientDrawable();
//                    drawable.setColor(Color.parseColor("#208CF9"));
//                    drawable.setCornerRadius(dp2px(5));
//                    dayView.setBackground(drawable);
//                    dayView.setTextColor(Color.WHITE);
//                    dayView.setText("今天");
//                }
//                // 是否为本月的号数，不是本月号数显示白色或者透明，及不显示
//                if (!entity.isSelfMonthDate) {
//                    dayView.setTextColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
//                    dayView.setClickable(false);
//                    dayView.setEnabled(false);
//                }
//                //点击事件
//                dayView.setOnClickListener(v -> {
//                    //增加一次点击次数
////                    mSelectTime++;
//                    GradientDrawable drawable = new GradientDrawable();
//                    drawable.setColor(Color.parseColor("#208CF9"));
//                    drawable.setCornerRadius(dp2px(5));
//                    dayView.setBackground(drawable);
//                    dayView.setTextColor(Color.WHITE);
////                    if (mSelectTime == 1) startPos = position;
////                    if (mSelectTime == 2) endPos = position;
////                    if (mSelectTime == 3) {
////                        mSelectTime = 0;
////                        startPos = -1;
////                        endPos = -1;
////                    }
//
//
//                });
//
//            }
//        }
//    }
//
//
//    private int dp2px(float dpValue) {
//        float scale = getContext().getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }
//
//    private int sp2px(float spValue) {
//        float scale = getContext().getResources().getDisplayMetrics().scaledDensity;
//        return (int) (spValue * scale + 0.5f);
//    }
//
//
}
