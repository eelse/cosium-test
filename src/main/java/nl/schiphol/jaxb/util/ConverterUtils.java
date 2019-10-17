/*
 * Copyright (c) 2015 Schiphol Group
 */
package nl.schiphol.jaxb.util;

import org.joda.time.*;
import org.joda.time.format.*;

/** Utility class for converting values from and to strings. */
public final class ConverterUtils {
  private static final String DATE_PATTERN = "yyyy-MM-dd";
  private static final String TIME_PATTERN = "HH:mm:ss";
  private static final String TIME_PATTERN_MINUTES = "HH:mm";
  private static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";

  private static final DateTimeFormatter DATE_FORMATTER =
      new DateTimeFormatterBuilder().appendPattern(DATE_PATTERN).toFormatter();
  private static final DateTimeFormatter TIME_FORMATTER =
      new DateTimeFormatterBuilder().appendPattern(TIME_PATTERN).toFormatter();
  private static final DateTimeFormatter DATETIME_FORMATTER =
      new DateTimeFormatterBuilder().appendPattern(DATETIME_PATTERN).toFormatter();
  private static final DateTimeFormatter TIME_FORMATTER_MINUTES =
      new DateTimeFormatterBuilder().appendPattern(TIME_PATTERN_MINUTES).toFormatter();

  public static final String MSG_INVALID_DATE =
      "Invalid date format ''{0}'' for ''{1}''. Expected date format: " + DATE_PATTERN;
  public static final String MSG_INVALID_TIME =
      "Invalid time format ''{0}'' for ''{1}''. Expected time format: " + TIME_PATTERN_MINUTES;

  private ConverterUtils() {}

  /**
   * Converts a String to a LocalDate.
   *
   * @param date String representation of the date (yyyy-MM-dd)
   * @return LocalDate
   */
  public static final LocalDate toDate(String date) {
    return DATE_FORMATTER.parseLocalDate(date);
  }

  /**
   * Converts a LocalDate to a String.
   *
   * @param date Date
   * @return String representation of the date (yyyy-MM-dd)
   */
  public static String fromDate(LocalDate date) {
    return DATE_FORMATTER.print(date);
  }

  /**
   * Converts a String to a LocalTime.
   *
   * @param time String representation of the time (HH:mm:ss)
   * @return LocalTime
   */
  public static final LocalTime toTime(String time) {
    return TIME_FORMATTER.parseLocalTime(time);
  }

  /**
   * Converts a String to a LocalTime.
   *
   * @param time String representation of the time (HH:mm:ss)
   * @return LocalTime
   */
  public static final LocalTime toTimeMinutes(String time) {
    return TIME_FORMATTER_MINUTES.parseLocalTime(time);
  }

  /**
   * Converts a LocalTime to a String.
   *
   * @param time Time
   * @return String representation of the time (HH:mm:ss)
   */
  public static String fromTime(LocalTime time) {
    return TIME_FORMATTER.print(time);
  }

  /**
   * Converts a String to a DateTime.
   *
   * @param dateTime String representation of the date time (yyyy-MM-dd'T'HH:mm:ss.SSSZZ)
   * @return DateTime
   */
  public static final DateTime toDateTime(String dateTime) {
    return DATETIME_FORMATTER.parseDateTime(dateTime);
  }

  /**
   * Converts a DateTime to a String.
   *
   * @param dateTime Date time
   * @return String representation of the date time (yyyy-MM-dd'T'HH:mm:ss.SSSZZ)
   */
  public static String fromDateTime(DateTime dateTime) {
    return DATETIME_FORMATTER.print(dateTime);
  }
}
