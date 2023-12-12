package edu.ntnu.stud.view;

import edu.ntnu.stud.view.MenuOption;
import java.util.ArrayList;

/**
 * This class represents a menu. It contains a list of menu options and methods to manage them.
 *
 * @author 10065
 * @version 1.0
 * @since 1.0
 */
public class Menu {

  private final ArrayList<MenuOption> menuOptions = new ArrayList<>();
  private String prompt = "Please select an option:";

  /**
   * Adds a menu option.
   *
   * @param option  the option
   * @param choice  the choice
   * @param command the command
   */
  public void addMenuOption(String option, int choice, String command) {
    MenuOption menuOption = new MenuOption(option, choice, command);
    menuOptions.add(menuOption);
  }

  /**
   * Sets the prompt.
   *
   * @param prompt the prompt
   */
  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  /**
   * Displays the menu.
   */
  public void displayMenu() {
    System.out.println("-".repeat(40));
    System.out.println(prompt);
    System.out.println("-".repeat(40));
    menuOptions.forEach(System.out::println);
    System.out.println("-".repeat(40));
  }

  /**
   * Selects a menu option.
   *
   * @param choice the choice selected
   * @return the command of the menu option
   */
  public String select(int choice) {
    return menuOptions.stream()
        .filter(options -> options.getChoice() == choice)
        .findFirst()
        .map(MenuOption::getCommand)
        .orElse("");
  }
}
