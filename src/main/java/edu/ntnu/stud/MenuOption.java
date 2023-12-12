package edu.ntnu.stud;

/**
 * This class represents a menu option.
 */
public class MenuOption {
  private final String option;
  private final int choice;
  private final String command;

  /**
   * Constructor for menu option, creates a new menu option.
   *
   * @param option  the option
   * @param choice  the choice
   * @param command the command
   */
  public MenuOption(String option, int choice, String command) {
    this.option = option;
    this.choice = choice;
    this.command = command;
  }

  /**
   * Gets the choice.
   *
   * @return the choice
   */
  public int getChoice() {
    return choice;
  }

  /**
   * Gets the command.
   *
   * @return the command
   */
  public String getCommand() {
    return command;
  }

  /**
   * Returns a string representation of the object.
   *
   * @return a string representation of the object
   */
  @Override
  public String toString() {
    return "'" + choice + "'" + " -> " + option + ":";
  }
}
