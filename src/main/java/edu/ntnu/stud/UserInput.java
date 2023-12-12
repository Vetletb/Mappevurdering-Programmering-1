package edu.ntnu.stud;

import java.util.Scanner;

/**
 * This class represents user input. It contains methods for reading input from the user.
 */
public class UserInput {
  private static final Scanner scanner = new Scanner(System.in);

  /**
   * Reads a string from the user.
   *
   * @return the inputted string
   */
  public static String readString() {
    return scanner.nextLine();
  }

  /**
   * Reads an integer from the user.
   *
   * @return the inputted integer
   */
  public static int readInt() {
    String stringInput;
    boolean valid = false;
    int intInput = 0;
    while (!valid) {
      stringInput = scanner.nextLine();
      try {
        intInput = Integer.parseInt(stringInput);
        valid = true;
      } catch (NumberFormatException e) {
        System.out.println("Please enter a number.");
      }
    }
    return intInput;
  }

  /**
   * Prompts the user for an integer.
   *
   * @param prompt the prompt to display
   * @return the inputted integer
   */
  public static int promptInt(String prompt) {
    System.out.println(prompt);
    return readInt();
  }

  /**
   * Prompts the user for a string.
   *
   * @param prompt the prompt to display
   * @return the inputted string
   */
  public static String promptString(String prompt) {
    System.out.println(prompt);
    return readString();
  }
}