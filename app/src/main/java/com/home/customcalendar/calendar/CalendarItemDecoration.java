package com.home.customcalendar.calendar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义分割工具，包含吸顶悬停~
 */
public class CalendarItemDecoration extends RecyclerView.ItemDecoration {
    Paint paint = new Paint();
    Paint colorPaint = new Paint();
    Paint linePaint = new Paint();

    public CalendarItemDecoration() {
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStyle(Paint.Style.FILL);
        colorPaint.setFakeBoldText(true);
        colorPaint.setColor(Color.parseColor("#000000"));
        colorPaint.setAntiAlias(true);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.parseColor("#dddddd"));
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (parent.getChildCount() <= 0) {
            return;
        }

        //头部的高度
        int height = 50;
        final float scale = parent.getContext().getResources().getDisplayMetrics().density;
        height = (int) (height * scale + 0.5f);

        //获取第一个可见的view，通过此view获取其对应的月份
        CustomCalendarView.CalendarRvViewAdapter viewAdapter = (CustomCalendarView.CalendarRvViewAdapter) parent.getAdapter();
        View fistView = parent.getChildAt(0);
        String text = viewAdapter.mDataList.get(parent.getChildAdapterPosition(fistView)).getMonthStr();

        String fistMonthStr = "";
        int fistViewTop = 0;
        //查找当前可见的itemView中第一个月份类型的item
        for (int i = 0; i < parent.getChildCount(); i++) {
            View v = parent.getChildAt(i);
            if (2 == parent.getChildViewHolder(v).getItemViewType()) {
                fistMonthStr = viewAdapter.mDataList.get(parent.getChildAdapterPosition(v)).getMonthStr();
                fistViewTop = v.getTop();
                break;
            }
        }

        //计算偏移量
        int topOffset = 0;
        if (!fistMonthStr.equals(text) && fistViewTop < height) {
            //前提是第一个可见的月份item不是当前显示的月份和距离的顶部的距离小于头部的高度
            topOffset = height - fistViewTop;
        }
        int t = 0 - topOffset;

        //绘制头部区域
        c.drawRect(parent.getLeft(), t, parent.getRight(), t + height, paint);

        colorPaint.setTextAlign(Paint.Align.CENTER);
        colorPaint.setTextSize(15 * scale + 0.5f);
        //绘制头部月份文字
        c.drawText(text, parent.getRight() / 2, (t + height) / 2, colorPaint);

        //绘制分割线(每行都会有)
//        if(fistViewTop!=height) {
//            linePaint.setStrokeWidth(scale * 0.5f + 0.5f);
//            c.drawLine(parent.getLeft(), t + height, parent.getRight(), t + height, linePaint);
//        }

    }

}
