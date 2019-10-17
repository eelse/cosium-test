package nl.schiphol.jaxb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/** Some date/time util methods for parsing and formatting dates. */
public final class DateUtil {
  private static final String ISO_DATETIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

  // if assigned: a fixed 'current' LocalTime for (JUnit) testing
  private static Date testDateTime = null;

  /** Restrictive constructor */
  private DateUtil() {}

  public static SimpleDateFormat createDateFormat(String format) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setLenient(false);
    return dateFormat;
  }

  /**
   * Returns the current date/time. Use <code>setTestDateTime()</code> to fixed this value for
   * testing purposes.
   *
   * @return the current date/time
   */
  public static Date getCurrentDateTime() {
    if (testDateTime == null) {
      // return actual time
      return new Date();
    } else {
      // return fixed LocalTime for JUnit testing
      return new Date(testDateTime.getTime());
    }
  }

  public static ZonedDateTime getCurrentZonedDateTime() {
    return convert(getCurrentDateTime());
  }

  public static String formatXmlDateTime(LocalDateTime dateTime) {
    return formatXmlDateTime(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant()));
  }

  public static String formatXmlDateTime(Date dateTime) {
    return dateTime != null ? new SimpleDateFormat(ISO_DATETIME).format(dateTime) : null;
  }

  public static String formatXmlDateTime(ZonedDateTime date) {
    return formatXmlDateTime(convert(date));
  }

  /**
   * Rounds the specified date to minutes.<br>
   * Examples:
   *
   * <ul>
   *   <li>{@code 2008-12-31T23:59:29.499+01:00} rounds to {@code 2008-12-31T23:59:00.000+01:00}
   *   <li>{@code 2008-12-31T23:59:29.500+01:00} rounds to {@code 2009-01-01T00:00:00.000+01:00}
   * </ul>
   *
   * @param date {@link Date} to round to the minute
   * @return The rounded {@link Date}
   */
  public static Date roundMinute(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    if (cal.get(Calendar.MILLISECOND) >= 500) {
      cal.add(Calendar.SECOND, 1);
    }
    cal.set(Calendar.MILLISECOND, 0);
    if (cal.get(Calendar.SECOND) >= 30) {
      cal.add(Calendar.MINUTE, 1);
    }
    cal.set(Calendar.SECOND, 0);
    return cal.getTime();
  }

  /**
   * Do NOT use in production code! Assigns a fixed 'current' dateTime for testing purpose.
   * Subsequent calls to getCurrentDateTime() will return the assigned fixed date/time instead of
   * the actual date/time.
   *
   * @param dateTime the fixed date/time (null to unassign)
   */
  @Deprecated
  public static void setTestDateTime(Date dateTime) {
    testDateTime = dateTime;
  }

  /**
   * Parses a date/time string with the format yyyy-MM-ddTHH:mm:ss.S(Z|(+|-)HH:mm) and returns a
   * <code>java.util.Date</code>
   *
   * @param dateTime a date/time string
   * @return the parsed date as <code>java.util.Date</code>
   * @throws ParseException if date/time is not in the format yyyy-MM-ddTHH:mm:ss.S(Z|(+|-)HH:mm)
   */
  public static Date getDateTime(String dateTime) throws ParseException {
    return dateTime != null ? new SimpleDateFormat(ISO_DATETIME).parse(dateTime) : null;
  }

  public static ZonedDateTime getZonedDateTime(
      int year, int month, int day, int hour, int min, int sec) {
    return ZonedDateTime.of(year, month, day, hour, min, sec, 0, ZoneId.systemDefault());
  }

  public static Date getDate(int year, int month, int day, int hour, int min, int sec) {
    return convert(getZonedDateTime(year, month, day, hour, min, sec));
  }

  public static Date getDate(int year, int month, int day, int hour, int min) {
    return getDate(year, month, day, hour, min, 0);
  }

  public static Date getDate(int year, int month, int day, int hour) {
    return getDate(year, month, day, hour, 0, 0);
  }

  public static Date getDate(int year, int month, int day) {
    return getDate(year, month, day, 0, 0, 0);
  }

  public static Date convert(ZonedDateTime date) {
    if (date == null) {
      return null;
    }
    return new Date(date.toInstant().toEpochMilli());
  }

  public static <T> T convert(Class<T> type, Calendar date) {
    if (date == null) {
      return null;
    }
    Instant instant = Instant.ofEpochMilli(date.getTimeInMillis());
    ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.of(date.getTimeZone().getID()));
    if (type == ZonedDateTime.class) {
      return (T) zdt;
    } else if (type == LocalTime.class) {
      return (T) zdt.toLocalTime().withNano(0);
    } else {
      String err = "Cannot convert to specified type.";
      throw new IllegalArgumentException(err);
    }
  }

  public static ZonedDateTime convert(Calendar date) {
    return convert(ZonedDateTime.class, date);
  }

  public static <T> T convert(Class<T> type, Date date) {
    if (date == null) {
      return null;
    }
    Instant instant = Instant.ofEpochMilli(date.getTime());
    ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    if (type == ZonedDateTime.class) {
      return type.cast(zdt);
    } else if (type == LocalTime.class) {
      return type.cast(zdt.toLocalTime().withNano(0));
    } else if (type == LocalDate.class) {
      return type.cast(zdt.toLocalDate());
    }
    String err = "Cannot convert date '" + date + "' to specified type '" + type + '\'';
    throw new IllegalArgumentException(err);
  }

  public static ZonedDateTime convert(Date date) {
    return convert(ZonedDateTime.class, date);
  }

  public static Date copy(Date date) {
    return date != null ? new Date(date.getTime()) : null;
  }

  public static Date getDate(LocalDate date, LocalTime time) {
    if (date == null || time == null) {
      return null;
    }
    return convert(date.atTime(time).atZone(ZoneId.systemDefault()));
  }

  /**
   * Returns new Castor LocalTime filled with hour, minute and second data from Calendar argument.
   * Milliseconds are deliberately not filled in!
   *
   * @return Time
   */
  @Deprecated
  public static LocalTime getTime(Calendar cal) {
    return LocalTime.of(
        cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
  }

  /**
   * Returns new Castor LocalTime filled with hour, minute and second data from Date argument.
   * Milliseconds are deliberately not filled in!
   *
   * @return Time
   */
  @Deprecated
  public static LocalTime getTime(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return getTime(cal);
  }

  public static long differenceInSeconds(Date dateTime1, Date dateTime2) {
    long milliseconds = dateTime1.getTime() - dateTime2.getTime();
    return milliseconds / 1000;
  }

  public static String formatDateStringWithPattern(Date date, String pattern) {
    return createDateFormat(pattern).format(date);
  }

  public static Date parseStringToDate(String date, String pattern) throws ParseException {
    return createDateFormat(pattern).parse(date);
  }

  /** Returns true if date1 is after date2 ignoring seconds and millseconds. */
  public static boolean after(Date date1, Date date2) {
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(date1);
    cal1.clear(Calendar.SECOND);
    cal1.clear(Calendar.MILLISECOND);

    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(date2);
    cal2.clear(Calendar.SECOND);
    cal2.clear(Calendar.MILLISECOND);

    return cal1.after(cal2);
  }

  /** Returns true if date1 is before date2 ignoring seconds and millseconds. */
  public static boolean before(Date date1, Date date2) {
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(date1);
    cal1.clear(Calendar.SECOND);
    cal1.clear(Calendar.MILLISECOND);

    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(date2);
    cal2.clear(Calendar.SECOND);
    cal2.clear(Calendar.MILLISECOND);

    return cal1.before(cal2);
  }

  /** Returns true if cal1 is after cal2 ignoring seconds and milliseconds. */
  public static boolean after(Calendar cal1, Calendar cal2) {
    // Save seconds and milliseconds
    int seconds1 = cal1.get(Calendar.SECOND);
    int millis1 = cal1.get(Calendar.MILLISECOND);
    int seconds2 = cal2.get(Calendar.SECOND);
    int millis2 = cal2.get(Calendar.MILLISECOND);

    cal1.clear(Calendar.SECOND);
    cal1.clear(Calendar.MILLISECOND);
    cal2.clear(Calendar.SECOND);
    cal2.clear(Calendar.MILLISECOND);

    boolean after = cal1.after(cal2);

    // Set seconds and milliseconds back to original values
    cal1.set(Calendar.SECOND, seconds1);
    cal1.set(Calendar.MILLISECOND, millis1);
    cal2.set(Calendar.SECOND, seconds2);
    cal2.set(Calendar.MILLISECOND, millis2);

    return after;
  }

  /** Returns true if cal1 is before cal2 ignoring seconds and milliseconds. */
  public static boolean before(Calendar cal1, Calendar cal2) {
    // Save seconds and milliseconds
    int seconds1 = cal1.get(Calendar.SECOND);
    int millis1 = cal1.get(Calendar.MILLISECOND);
    int seconds2 = cal2.get(Calendar.SECOND);
    int millis2 = cal2.get(Calendar.MILLISECOND);

    cal1.clear(Calendar.SECOND);
    cal1.clear(Calendar.MILLISECOND);
    cal2.clear(Calendar.SECOND);
    cal2.clear(Calendar.MILLISECOND);

    boolean before = cal1.before(cal2);

    // Set seconds and milliseconds back to original values
    cal1.set(Calendar.SECOND, seconds1);
    cal1.set(Calendar.MILLISECOND, millis1);
    cal2.set(Calendar.SECOND, seconds2);
    cal2.set(Calendar.MILLISECOND, millis2);

    return before;
  }

  /**
   * Nullpointer-proof equals-implementation for two {@link Date} instances. When both are null,
   * they are considered equal.
   *
   * @param date1 The one {@link Date}
   * @param date2 The other {@link Date}
   * @return {@code true} if either both are null or {@link Date#equals(Object)} is true, else
   *     {@code false}
   * @see Date#equals(Object)
   */
  public static boolean equals(Date date1, Date date2) {
    return (date1 == null && date2 == null) || (date1 != null && date1.equals(date2));
  }

  public static LocalDateTime toLocalDateTime(Calendar calendar) {
    if (calendar == null) {
      return null;
    }
    TimeZone tz = calendar.getTimeZone();
    ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
    return LocalDateTime.ofInstant(calendar.toInstant(), zid);
  }

  public static Date convertToDate(LocalDateTime dateToConvert) {
    return java.util.Date
        .from(dateToConvert.atZone(ZoneId.systemDefault())
            .toInstant());
  }

  public static Date convertToDate(LocalDate dateToConvert) {
    return java.util.Date.from(dateToConvert.atStartOfDay()
        .atZone(ZoneId.systemDefault())
        .toInstant());
  }

  public LocalDateTime convertToLocalDateTime(Date dateToConvert) {
    return dateToConvert.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime();
  }
}
