package edu.ntnu.stud;

import java.time.LocalTime;

/**
 * This class contains methods for validating input.
 */
public class Validation {

  /**
   * Validates the number is positive.
   *
   * @param number the number to validate
   * @param name   the name of the input
   * @throws IllegalArgumentException if number is zero or less
   */
  public static void validatePositiveNumber(int number, String name) {
    if (number <= 0) {
      throw new IllegalArgumentException(name + " cannot be under zero");
    }
  }

  /**
   * Validates the number is zero or positive.
   *
   * @param number the number to validate
   * @param name   the name of the input
   * @throws IllegalArgumentException if number is less than zero
   */
  public static void validateZeroOrPositiveNumber(int number, String name) {
    if (number < 0) {
      throw new IllegalArgumentException(name + " cannot be negative");
    }
  }

  /**
   * Validates the number is positive, unless -1.
   *
   * @param number the number to validate
   * @param name   the name of the input
   * @throws IllegalArgumentException if number is zero or less, unless -1
   */
  public static void validatePositiveUnlessNegativeOne(int number, String name) {
    if (number <= 0 && number != -1) {
      throw new IllegalArgumentException(name + " cannot be zero or less, unless -1");
    }
  }

  /**
   * Validates the string is not blank.
   *
   * @param string the string to validate
   * @param name   the name of the input
   * @throws IllegalArgumentException if string is blank
   */
  public static void validateStringNotBlank(String string, String name) {
    if (string.isBlank()) {
      throw new IllegalArgumentException(name + " cannot be blank");
    }
  }

  /**
   * Validates the object is not null.
   *
   * @param object the object to validate
   * @param name   the name of the input
   * @throws IllegalArgumentException if object is null
   */
  public static void validateNotNull(Object object, String name) {
    if (object == null) {
      throw new IllegalArgumentException(name + " cannot be null");
    }
  }

  /**
   * Validates the new time is after the old time.
   *
   * @param oldTime the old time
   * @param newTime the new tim
   * @throws IllegalArgumentException if new time is before old time
   */
  public static void validateTimeAfter(LocalTime oldTime, LocalTime newTime) {
    if (newTime.isBefore(oldTime)) {
      throw new IllegalArgumentException("Time cannot be before: " + oldTime);
    }
  }
}
