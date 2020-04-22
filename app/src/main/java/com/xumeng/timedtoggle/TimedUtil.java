package com.xumeng.timedtoggle;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimedUtil {
    static boolean[] toggleButtonsIsChecked = new boolean[7];

    static Date date;

    private static String[] dateStringArray;

    //计算定时任务触发时间
    public static long computeTime(String time) {
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

    //TODO 再次计算定时任务触发时间
    public static long computeTimeAgain(String time) {
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
        String dateString = simpleDateFormat.format(date);
        //String[] dateStringArray = dateString.split(":");
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
}
