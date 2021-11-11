package com.primary_education_system.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static final DateFormat DATE_CSV_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");

    public static Date getDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String getDateCSVAsString(Date date) {
        return getTimeAsString(date, DATE_CSV_FORMAT);
    }


    private static String getTimeAsString(Date date, DateFormat dateFormat) {
        if (date == null)
            return null;

        return dateFormat.format(date);
    }

    public static Calendar getDateWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;

    }

    public static Long minusMinutesDate(Date date, long minutes) {
        long t = date.getTime();
        long reuseTime = minutes * 60000;
        return t - reuseTime;
    }

    //convert yyyy/MM/dd HH:mm:ss to yyyy/MM/dd
    public static Date convertDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = dateFormat.format(date);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date minusTenMinutes() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -10);
        return cal.getTime();
    }

    public static Date minusDay(int day) {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static Date getToDay() {
        Date input = new Date();
        LocalDate start = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getTomorrow() {
        Date input = new Date();
        LocalDate start = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = start.plusDays(1);
        return Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getCurrentTime(String pattern) {
        SimpleDateFormat fm = new SimpleDateFormat(pattern);
        return fm.format(new Date());
    }

    public static String getCurrentTimePlusOneSecond(String pattern) {
        SimpleDateFormat fm = new SimpleDateFormat(pattern);
        return fm.format(new Date(new Date().getTime() + 1000));
    }


    public static Date plusDayFromToday(int numberIncrease) {
        Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, numberIncrease);
        return cal.getTime();
    }

    public static Date plusDayFromDate(int numberIncrease, Date dateStart) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStart);
        cal.add(Calendar.DATE, numberIncrease);
        return cal.getTime();
    }

    //convert yyyy/MM/dd HH:mm:ss to yyyy/MM/dd
    public static String convertDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(date);
    }

    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static int getWeeKNumberOfMonthByDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_MONTH);
    }

}
