package com.testapp.socialuserinfo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateHelper {

    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FROMAT_DD_MM_YY_HH_MM = "dd.MM.yy, HH:mm";
    public static final String FROMAT_DD_MM_YY_HH_MM_SS = "dd.MM.yy, HH:mm:ss";
    public static final String FORMAT_DD_MMMM_HH_MM = "dd MMMM, HH:mm";
    public static final String FORMAT_DD_MM_YY = "dd.MM.yy";
    public static final String FORMAT_DD_MM_YYYY = "dd.MM.yyyy";
    public static final String FORMAT_HH_MM_SS = "HH:mm:ss";
    public static final String FORMAT_HH_MM = "HH:mm";
    public static final String SERVER_TIME_ZONE = "Etc/GMT-0";
    private static final Locale LOCALE_RU = new Locale("ru");
    private static int sDifferenceInTimeWithServer = 0;

    public static void setDiffWithServer(String serverTime) {
        Date date = getDateFromString(serverTime);
        Calendar calendar = Calendar.getInstance();

        int currentHours = calendar.get(Calendar.HOUR_OF_DAY);

        calendar.setTime(date);
        int serverHours = calendar.get(Calendar.HOUR_OF_DAY);

        sDifferenceInTimeWithServer = currentHours - serverHours;
    }

    public static Date getDateFromString(String dateString) {
        try {
            DateFormat format = new SimpleDateFormat(DateHelper.FORMAT_YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String correctDateWithDifference(String serverTime) {
        Date date = getDateFromString(serverTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, sDifferenceInTimeWithServer);
        return getStringFromDate(calendar.getTime(), FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public static String getStringFromDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, LOCALE_RU);
        return dateFormat.format(date);
    }

    public static String getCurrentDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS, LOCALE_RU);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }

    public static Date getCurrentDateMinusHours(int hours) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -hours);
        return cal.getTime();
    }

    public static String getStringMinutesAndSeconds(long timeInMillis) {
        long timeInSec = timeInMillis / 1000;
        long minutes = (timeInSec % 3600) / 60;
        long seconds = timeInSec % 60;
        return String.format(LOCALE_RU, "%02d : %02d", minutes, seconds);
    }

    public static void setCalendarDayMinimum(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
    }

    public static void setCalendarDayMaximum(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getMaximum(Calendar.MILLISECOND));
    }

    public static String getStartDateOfWeek(String dateString) {
        Date date = getDateFromString(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Calendar resultCalendar = Calendar.getInstance();
        resultCalendar.clear();
        resultCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        resultCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        resultCalendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR));
        resultCalendar.add(Calendar.WEEK_OF_YEAR, 1);

        return getStringFromDate(resultCalendar.getTime(), FORMAT_DD_MM_YY);
    }

    public static String getEndDateOfWeek(String dateString) {
        Date date = getDateFromString(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Calendar resultCalendar = Calendar.getInstance();
        resultCalendar.clear();
        resultCalendar.setFirstDayOfWeek(Calendar.MONDAY);
        resultCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        resultCalendar.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR));
        resultCalendar.add(Calendar.WEEK_OF_YEAR, 1);
        resultCalendar.add(Calendar.DAY_OF_YEAR, 6);
        return getStringFromDate(resultCalendar.getTime(), FORMAT_DD_MM_YY);
    }

    public static int differenceBetweenInMinutes(Date start, Date end) {
        long duration = end.getTime() - start.getTime();
        return (int) TimeUnit.MILLISECONDS.toMinutes(duration);
    }

    public static int differenceBetweenInHours(Date start, Date end) {
        long duration = end.getTime() - start.getTime();
        return (int) TimeUnit.MILLISECONDS.toHours(duration);
    }

    public static String getStringFromDateLocalToServerTimeZone(Date date, String format) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        f.setTimeZone(TimeZone.getTimeZone(SERVER_TIME_ZONE));
        return f.format(date);
    }

    public static String getStringFromDateLocalToServerTimeZone(Date date, String format, String timeZone) {
        SimpleDateFormat f = new SimpleDateFormat(format);
        f.setTimeZone(TimeZone.getTimeZone(timeZone));
        return f.format(date);
    }

    /**
     * converts the date from the server to the local time zone
     */
    public static Date getDateFromDateLocalToServerTimeZone(String dateString) {
        try {
            SimpleDateFormat f = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HH_MM_SS);
            f.setTimeZone(TimeZone.getTimeZone(SERVER_TIME_ZONE));
            return f.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
