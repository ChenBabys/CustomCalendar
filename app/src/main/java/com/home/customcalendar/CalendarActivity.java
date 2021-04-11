package com.home.customcalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import com.home.customcalendar.calendar.CustomCalendarView;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CustomCalendarView customCalendarView = findViewById(R.id.calendar);
//        customCalendarView.setDaySelectedBackgroundColor(Color.parseColor("#000000"));
//        customCalendarView.setShowAfterTodayDate(false);
//        customCalendarView.setDayTextColor(Color.parseColor("#A362DA"));
//        customCalendarView.setDayTextSize(10);
//        customCalendarView.setTitleTextColor(Color.parseColor("#A362DA"));
//        customCalendarView.setTitleTextSize(12);
//        customCalendarView.setWeekEndTextColor(Color.parseColor("#000000"));
//        customCalendarView.setSelectedBetweenBackgroundColor(Color.parseColor("#DA6262"));
//        customCalendarView.setTodayBackgroundColor(Color.parseColor("#DA6262"));
        customCalendarView.setOnDateSelected((startDate, endDate) -> {
            Toast.makeText(this, "开始" + startDate + "--结束" + endDate, Toast.LENGTH_SHORT).show();
        });
    }
}