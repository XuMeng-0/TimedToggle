package com.xumeng.timedtoggle;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimedUtil {
    static boolean[] toggleButtonsIsChecked = new boolean[7];

    static Date date;

    private static String[] dateStringArray;

    private static String startTime = "";

    private static String endTime = "";

    //计算定时任务触发时间
    static long computeTime(String time, boolean isStart) {
        if (isStart) {
            TimedUtil.startTime = time;
        } else {
            TimedUtil.endTime = time;
        }

        Log.i("TimedUtil", time);
        Log.i("TimedUtil", "weekChecked:");
        for (boolean isChecked : toggleButtonsIsChecked) {
            Log.i("TimedUtil", String.valueOf(isChecked));
        }

        long timeAtMillis = computeTimeIgnoreFrequency(time);
        if (timeAtMillis == -1L) {
            return -1L;
        }
        if (toggleButtonsIsCheckedNotContainsTrue()) {
            Log.i("TimedUtil", String.valueOf(timeAtMillis));
            return timeAtMillis;
        }
        //计算当前星期几
        String weekDayString = dateStringArray[5];
        int weekday = -1;
        switch (weekDayString) {
            case "Monday": {
                weekday = 0;
            }
            break;
            case "Tuesday": {
                weekday = 1;
            }
            break;
            case "Wednesday": {
                weekday = 2;
            }
            break;
            case "Thursday": {
                weekday = 3;
            }
            break;
            case "Friday": {
                weekday = 4;
            }
            break;
            case "Saturday": {
                weekday = 5;
            }
            break;
            case "Sunday": {
                weekday = 6;
            }
            break;
        }
        Log.i("TimedUtil", String.valueOf(weekday));
        //如果当前星期数选中，不修正
        if (toggleButtonsIsChecked[weekday]) {
            Log.i("TimedUtil", String.valueOf(timeAtMillis));
            return timeAtMillis;
        }
        //判断下一个星期数是否选中，选中则修正并返回，否则继续，直到当前星期数的前一天
        int temp = weekday;
        do {
            temp = (temp + 1) % 7;
            timeAtMillis += 24 * 3600 * 1000;
            Log.i("TimedUtil", "+24h");
        } while (!toggleButtonsIsChecked[temp]);
        Log.i("TimedUtil", String.valueOf(timeAtMillis));
        return timeAtMillis;
    }

    //再次计算定时任务触发时间
    static long computeTimeAgain(boolean isStart) {
        String time;
        if (isStart) {
            time = TimedUtil.startTime;
        } else {
            time = TimedUtil.endTime;
        }
        Log.i("TimedUtil", time);
        Log.i("TimedUtil", "weekChecked:");
        for (boolean isChecked : toggleButtonsIsChecked) {
            Log.i("TimedUtil", String.valueOf(isChecked));
        }

        if (time.equals("")) {
            return -1L;
        }
        long timeAtMillis = computeTimeIgnoreFrequency(time);
        if (timeAtMillis == -1L) {
            return -1L;
        }
        if (toggleButtonsIsCheckedNotContainsTrue()) {
            Log.i("TimedUtil", String.valueOf(timeAtMillis));
            return timeAtMillis;
        }

        int weekday = computeTodaySequenceInWeek();

        //判断下一个星期数是否选中，选中则修正并返回，否则继续，直到下一个当前星期数
        do {
            weekday = (weekday + 1) % 7;
            timeAtMillis += 24 * 3600 * 1000;
            Log.i("TimedUtil", "+24h");
        } while (!toggleButtonsIsChecked[weekday]);
        Log.i("TimedUtil", String.valueOf(timeAtMillis));
        return timeAtMillis;
    }

    //判断开关按钮的状态是否全部为“关闭”,即用户是否选择了重复频率
    private static boolean toggleButtonsIsCheckedNotContainsTrue() {
        for (boolean isChecked : toggleButtonsIsChecked) {
            if (isChecked) {
                return false;
            }
        }
        return true;
    }

    //计算不考虑重复频率情况下的触发时间
    private static long computeTimeIgnoreFrequency(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm:EEEE");
        String dateString = simpleDateFormat.format(new Date());
        dateStringArray = dateString.split(":");
        dateStringArray[3] = time.split(":")[0];
        dateStringArray[4] = time.split(":")[1];
        dateString = dateStringArray[0] + ":" + dateStringArray[1] + ":" + dateStringArray[2] + ":" +
                dateStringArray[3] + ":" + dateStringArray[4] + ":" + dateStringArray[5];
        Log.i("TimedUtil", dateString);
        try {
            return simpleDateFormat.parse(dateString).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1L;
    }

    //计算今天周几 Monday 0, Tuesday 1 …… Sunday 6 以此类推, 为了和数组中当天是否选中标记的索引对应
    private static int computeTodaySequenceInWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        weekday = (weekday + 5) % 7;
        Log.i("TimedUtil weekday", String.valueOf(weekday));
        return weekday;
    }
}
