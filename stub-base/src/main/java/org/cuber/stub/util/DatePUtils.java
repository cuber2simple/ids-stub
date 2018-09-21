package org.cuber.stub.util;

import org.apache.commons.lang3.time.DateUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

public class DatePUtils {

    public static Date trans2Date(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }

    public static long trans2Mills(LocalDateTime localDateTime) {
        return trans2Date(localDateTime).getTime();
    }

    public static LocalDateTime trans2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        return localDateTime;
    }

    public static boolean isSameMonth(Date date, Date other) {
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(other);
        return isSameMonth(cal1, cal2);
    }

    public static boolean isSameMonth(Calendar date, Calendar other) {
        return date.get(Calendar.MONTH) == other.get(Calendar.MONTH) &&
                date.get(Calendar.YEAR) == other.get(Calendar.YEAR);
    }

    public static void main(String[] args) {
        Date date = new Date();
        Date date1 = DateUtils.addDays(date, -1);
        System.out.println(isSameMonth(date,date1));
    }

}
