package com.home.customcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.home.customcalendar.calendar.CustomCalendarView;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CustomCalendarView customCalendarView = findViewById(R.id.calendar);
        customCalendarView.setOnDateSelected((startDate, endDate) -> {
            Toast.makeText(this, "开始" + startDate + "--结束" + endDate, Toast.LENGTH_SHORT).show();
        });
    }
}