package edu.ntnu.stud;

import java.util.Scanner;

public class UserInput {
  private static final Scanner scanner = new Scanner(System.in);

  public static String readString() {
    return scanner.nextLine();
  }

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
        scanner.nextLine();
      }
    }
    return intInput;
  }

  public static int promptInt(String prompt) {
    System.out.println(prompt);
    return readInt();
  }

  public static String promptString(String prompt) {
    System.out.println(prompt);
    return readString();
  }
}