package edu.ntnu.stud;

public class MenuOption {
  private final String option;
  private final int choice;
  private final String command;

  public MenuOption(String option, int choice, String command) {
    this.option = option;
    this.choice = choice;
    this.command = command;
  }

  public int getChoice() {
    return choice;
  }

  public String getCommand() {
    return command;
  }

  @Override
  public String toString() {
    return "'" + choice + "'" + " -> " + option + ":";
  }
}
