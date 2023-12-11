package edu.ntnu.stud;

import java.time.LocalTime;

public class Validation {

  /**
   * Validates the number is positive.
   *
   * @param number the number
   * @param name   the name
   */
  public static void validatePositiveNumber(int number, String name) {
    if (number < 0) {
      throw new IllegalArgumentException(name + " cannot be negative");
    }
  }

  public static void validatePositiveUnlessNegativeOne(int number, String name) {
    if (number <= 0 && number != -1) {
      throw new IllegalArgumentException(name + " cannot be zero or less, unless -1");
    }
  }

  /**
   * Validates the string is not blank.
   *
   * @param string the string
   * @param name   the name
   */
  public static void validateStringNotBlank(String string, String name) {
    if (string.isBlank()) {
      throw new IllegalArgumentException(name + " cannot be blank");
    }
  }

  /**
   * Validates the object is not null.
   *
   * @param object the object
   * @param name   the name
   */
  public static void validateNotNull(Object object, String name) {
    if (object == null) {
      throw new IllegalArgumentException(name + " cannot be null");
    }
  }

  public static void validateTimeAfter(LocalTime oldTime, LocalTime newTime) {
    if (newTime.isBefore(oldTime)) {
      throw new IllegalArgumentException("Time cannot be before: " + oldTime);
    }
  }
}
