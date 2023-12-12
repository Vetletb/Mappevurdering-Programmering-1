package edu.ntnu.stud.view;

import java.util.HashMap;

/**
 * This class represents a menu builder. It contains a map of menus and methods to manage them.
 *
 * @author 10065
 * @version 1.0
 * @since 1.0
 */
public class MenuBuilder {
  private final HashMap<String, Menu> menus = new HashMap<>();

  /**
   * Adds a menu.
   *
   * @param name the name of the menu
   */
  public void addMenu(String name) {
    Menu menu = new Menu();
    menus.put(name, menu);
  }

  /**
   * Adds a menu option to a menu.
   *
   * @param menuName the menu name
   * @param option   the option
   * @param choice   the choice
   * @param command  the command
   */
  public void addMenuOption(String menuName, String option, int choice, String command) {
    Menu menu = menus.get(menuName);
    menu.addMenuOption(option, choice, command);
  }

  /**
   * Sets the prompt of a menu.
   *
   * @param menuName the menu name
   * @param prompt   the prompt
   */
  public void setPrompt(String menuName, String prompt) {
    Menu menu = menus.get(menuName);
    menu.setPrompt(prompt);
  }

  /**
   * Displays a menu.
   *
   * @param menuName the name of the menu to display
   */
  public void displayMenu(String menuName) {
    Menu menu = menus.get(menuName);
    menu.displayMenu();
  }

  /**
   * Selects a menu option from a menu.
   *
   * @param menuName the name of the menu to select from
   * @param choice   the choice selected
   * @return the command of the menu option
   */
  public String selectOption(String menuName, int choice) {
    Menu menu = menus.get(menuName);
    return menu.select(choice);
  }
}
