package com.home.customcalendar;

import java.io.Serializable;

/**
 * Created by ChenYesheng On 2021/4/9 15:01
 * Desc:  用来记录年份和月份的
 */
public class YearMonthEntity implements Serializable {

    private int currentYear;
    private int currentMonth;

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }

    public int getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(int currentMonth) {
        this.currentMonth = currentMonth;
    }
}
