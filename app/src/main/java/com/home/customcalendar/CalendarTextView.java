package com.home.customcalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by ChenYesheng On 2021/4/9 20:43
 * Desc:
 */
public class CalendarTextView extends AppCompatTextView {

    public boolean isToday;
    private boolean isSTime;
    private boolean isETime;
    private Context context;

    public void setEmptyColor(int emptyColor) {
        this.emptyColor = emptyColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    private int emptyColor = Color.parseColor("#00ff00");
    private int fillColor = Color.parseColor("#00ff00");

    private Paint mPaintSTime;
    private Paint mPaintETime;

    public CalendarTextView(@NonNull Context context) {
        super(context);
        initview(context);
    }


    public CalendarTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initview(context);
    }

    public CalendarTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview(context);
    }

    private void initview(Context context) {
        mPaintSTime = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSTime.setStyle(Paint.Style.FILL);
        mPaintSTime.setColor(Color.parseColor("#E9F3FF"));
        mPaintSTime.setStrokeWidth(2);

        mPaintETime = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintETime.setStyle(Paint.Style.FILL);
        mPaintETime.setColor(Color.parseColor("#E9F3FF"));
        mPaintETime.setStrokeWidth(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //根据当前逻辑开始时间必须先绘制结束时间
        if (isETime) {
            canvas.save();
            //移动到当前控件的中心，以中心为圆点绘制实心圆
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(0, 0, getWidth() / 2 , mPaintETime);
            //canvas.drawRoundRect(new RectF(), getWidth(), getWidth(), mPaintETime);
            canvas.restore();
            //此处必须将圆移动回开始位置，否则文本显示会受到影响
            canvas.translate(0, 0);
        }

        if (isSTime) {
            canvas.save();
            //移动到当前控件的中心，以中心为圆点绘制实心圆
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(0, 0, getWidth() / 2 , mPaintSTime);
//            canvas.drawRoundRect(new RectF(), getWidth(), getWidth(), mPaintSTime);
            canvas.restore();
            //此处必须将圆移动回开始位置，否则文本显示会受到影响
            canvas.translate(0, 0);
        }
    }

    public void setToday(boolean today) {
        isToday = today;
        this.setTextColor(Color.parseColor("#208CF9"));
    }

    public void isETime(boolean etime) {
        isETime = etime;
        this.setTextColor(Color.parseColor("#208CF9"));
    }

    public void isSTime(boolean stime) {
        isSTime = stime;
        this.setTextColor(Color.parseColor("#208CF9"));
    }

}
