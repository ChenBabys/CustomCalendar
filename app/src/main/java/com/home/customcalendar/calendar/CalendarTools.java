package com.home.customcalendar.calendar;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ChenYesheng On 2021/4/9 11:42
 * Desc: 获取日历。
 */
public class CalendarTools {
    public static final String MONDAY = "一";
    public static final String TUESDAY = "二";
    public static final String WEDNESDAY = "三";
    public static final String THURSDAY = "四";
    public static final String FRIDAY = "五";
    public static final String SATURDAY = "六";
    public static final String SUNDAY = "日";
    public static final String[] weekDayRow = {SUNDAY, MONDAY, TUESDAY,
            WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};
    private static CalendarTools tools;

    public static CalendarTools getInstance() {
        if (tools == null) {
            synchronized (CalendarTools.class) {
                if (tools == null) {
                    tools = new CalendarTools();
                }
            }
        }
        return tools;
    }

    /**
     * 获取指定区间的日历~
     * mCurrentDate 当前系统时间.
     * YearCount 获取从此刻月份往回数YearCount 年
     *
     * @return
     */
    public List<DateEntity> getDateEntities(Date mCurrentDate, int mYearCount) {
        List<DateEntity> dateEntities = new ArrayList<>();
        try {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            //日期格式化
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat formatYYYYMM = new SimpleDateFormat("yyyy-MM");
            //当前系统时间
            int currentYear = Integer.parseInt(format.format(mCurrentDate).split("-")[0]);
            int currentMonth = Integer.parseInt(format.format(mCurrentDate).split("-")[1]);
            int currentDay = Integer.parseInt(format.format(mCurrentDate).split("-")[2]);
            //起始日期(默认根据当前时间-该获取多少年的月份) 这里的格式是 年月日
            calendar.set(currentYear - mYearCount, (currentMonth - 1), 1);
            Date startDate = new Date(calendar.getTimeInMillis());
            calendar.setTime(startDate);

            //结束日期
            calendar.add(Calendar.YEAR, mYearCount);
            Date endDate = new Date(calendar.getTimeInMillis());

            //格式化开始日期和结束日期为 yyyy-mm-dd格式
            String endDateStr = format.format(endDate);
            endDate = format.parse(endDateStr);
            String startDateStr = format.format(startDate);
            startDate = format.parse(startDateStr);
            calendar.setTime(startDate);

            calendar.set(Calendar.DAY_OF_MONTH, 1);
            java.util.Calendar monthCalendar = java.util.Calendar.getInstance();

            //按月生成日历 每行7个 最多6行 42个
            //每一行有七个日期  日 一 二 三 四 五 六 的顺序
            for (; calendar.getTimeInMillis() <= endDate.getTime(); ) {

                //月份item
                DateEntity monthDateEntity = new DateEntity();
                monthDateEntity.setDate(calendar.getTime());
                monthDateEntity.setMonthStr(formatYYYYMM.format(monthDateEntity.getDate()));
                monthDateEntity.setItemType(DateEntity.item_type_month);
                dateEntities.add(monthDateEntity);

                //获取一个月结束的日期和开始日期
                monthCalendar.setTime(calendar.getTime());
                monthCalendar.set(Calendar.DAY_OF_MONTH, 1);
                //Date startMonthDay = calendar.getTime();

                monthCalendar.add(Calendar.MONTH, 1);
                monthCalendar.add(Calendar.DAY_OF_MONTH, -1);
                Date endMonthDay = monthCalendar.getTime();

                //重置为本月开始
                monthCalendar.set(Calendar.DAY_OF_MONTH, 1);

                for (; monthCalendar.getTimeInMillis() <= endMonthDay.getTime(); ) {
                    //生成单个月的日历

                    //处理一个月开始的第一天
                    if (monthCalendar.get(Calendar.DAY_OF_MONTH) == 1) {
                        //看某个月第一天是周几
                        int weekDay = monthCalendar.get(Calendar.DAY_OF_WEEK);
                        switch (weekDay) {
                            case 1:
                                //周日
                                break;
                            case 2:
                                //周一
                                addDatePlaceholder(dateEntities, 1, monthDateEntity.getMonthStr());
                                break;
                            case 3:
                                //周二
                                addDatePlaceholder(dateEntities, 2, monthDateEntity.getMonthStr());
                                break;
                            case 4:
                                //周三
                                addDatePlaceholder(dateEntities, 3, monthDateEntity.getMonthStr());
                                break;
                            case 5:
                                //周四
                                addDatePlaceholder(dateEntities, 4, monthDateEntity.getMonthStr());
                                break;
                            case 6:
                                addDatePlaceholder(dateEntities, 5, monthDateEntity.getMonthStr());
                                //周五
                                break;
                            case 7:
                                addDatePlaceholder(dateEntities, 6, monthDateEntity.getMonthStr());
                                //周六
                                break;
                        }
                    }

                    //生成某一天日期实体 日item
                    DateEntity dateEntity = new DateEntity();
                    dateEntity.setDate(monthCalendar.getTime());
                    dateEntity.setDay(monthCalendar.get(Calendar.DAY_OF_MONTH) + "");
                    dateEntity.setMonthStr(monthDateEntity.getMonthStr());
                    //月份要加上1，因为月份是0-11的
                    if (monthCalendar.get(Calendar.DAY_OF_MONTH) == currentDay
                            && (monthCalendar.get(Calendar.MONTH) + 1) == currentMonth
                            && monthCalendar.get(Calendar.YEAR) == currentYear) {
                        dateEntity.setIsToday(true);
                    }
                    //如果大于或者等于本年，并且大于或等于本月，并且是今天之后的日期则
                    if (monthCalendar.get(Calendar.DAY_OF_MONTH) > currentDay
                            && (monthCalendar.get(Calendar.MONTH) + 1) >= currentMonth
                            && monthCalendar.get(Calendar.YEAR) >= currentYear) {
                        dateEntity.setAfterToday(true);
                    }
                    //如果是周末则
                    if (monthCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                            || monthCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                        dateEntity.setAreWeekEnd(true);
                    }
                    dateEntities.add(dateEntity);


                    //处理一个月的最后一天
                    if (monthCalendar.getTimeInMillis() == endMonthDay.getTime()) {
                        //看某个月第一天是周几
                        int weekDay = monthCalendar.get(Calendar.DAY_OF_WEEK);
                        switch (weekDay) {
                            case 1:
                                //周日
                                addDatePlaceholder(dateEntities, 6, monthDateEntity.getMonthStr());
                                break;
                            case 2:
                                //周一
                                addDatePlaceholder(dateEntities, 5, monthDateEntity.getMonthStr());
                                break;
                            case 3:
                                //周二
                                addDatePlaceholder(dateEntities, 4, monthDateEntity.getMonthStr());
                                break;
                            case 4:
                                //周三
                                addDatePlaceholder(dateEntities, 3, monthDateEntity.getMonthStr());
                                break;
                            case 5:
                                //周四
                                addDatePlaceholder(dateEntities, 2, monthDateEntity.getMonthStr());
                                break;
                            case 6:
                                addDatePlaceholder(dateEntities, 1, monthDateEntity.getMonthStr());
                                //周5
                                break;
                        }
                    }

                    //天数加1
                    monthCalendar.add(Calendar.DAY_OF_MONTH, 1);
                }
                //月份加1
                calendar.add(Calendar.MONTH, 1);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateEntities;
    }

    /**
     * 添加空的日期占位
     *
     * @param dateBeans
     * @param count
     * @param monthStr
     */
    private void addDatePlaceholder(List<DateEntity> dateBeans, int count, String monthStr) {
        for (int i = 0; i < count; i++) {
            DateEntity dateEntity = new DateEntity();
            dateEntity.setMonthStr(monthStr);
            dateBeans.add(dateEntity);
        }
    }

    /**
     * 获取周几字符
     *
     * @param mWay
     * @return
     */
    private String getWeekStr(String mWay) {
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return mWay;
    }


}
