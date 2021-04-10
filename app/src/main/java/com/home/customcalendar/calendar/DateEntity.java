package com.home.customcalendar.calendar;

import java.io.Serializable;
import java.util.Date;

/**
 * 日历实体类，添加的参数在获取日历实体集合的时候设置
 */
public class DateEntity implements Serializable {
    //item类型
    public static int item_type_day = 1;//日期item
    public static int item_type_month = 2;//月份item
    int itemType = 1;//默认是日期item

    //item状态
    public static int ITEM_STATE_BEGIN_DATE = 1;//开始日期
    public static int ITEM_STATE_END_DATE = 2;//结束日期
    public static int ITEM_STATE_SELECTED = 3;//选中状态
    public static int ITEM_STATE_NORMAL = 4;//正常状态
    public int itemState = ITEM_STATE_NORMAL;

    private Date date;//具体日期
    private String day;//一个月的某天
    private String monthStr;//月份
    private boolean isToday;//是否是时间上的今天
    private boolean afterToday;//是否是今天之后
    private boolean areWeekEnd;//是否是周末

    public boolean isAreWeekEnd() {
        return areWeekEnd;
    }

    public void setAreWeekEnd(boolean areWeekEnd) {
        this.areWeekEnd = areWeekEnd;
    }

    public boolean isAfterToday() {
        return afterToday;
    }

    public void setAfterToday(boolean afterToday) {
        this.afterToday = afterToday;
    }

    public boolean isIsToday() {
        return isToday;
    }

    public void setIsToday(boolean isToday) {
        this.isToday = isToday;
    }

    public static int getItemTypeDay() {
        return item_type_day;
    }

    public static void setItemTypeDay(int item_type_day) {
        DateEntity.item_type_day = item_type_day;
    }

    public static int getItemTypeMonth() {
        return item_type_month;
    }

    public static void setItemTypeMonth(int item_type_month) {
        DateEntity.item_type_month = item_type_month;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonthStr() {
        return monthStr;
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getItemState() {
        return itemState;
    }

    public void setItemState(int itemState) {
        this.itemState = itemState;
    }
}