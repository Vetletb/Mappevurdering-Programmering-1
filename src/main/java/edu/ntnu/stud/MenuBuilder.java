package edu.ntnu.stud;

import java.util.HashMap;

public class MenuBuilder {
  private final HashMap<String, Menu> menus = new HashMap<>();

  public void addMenu(String name) {
    Menu menu = new Menu();
    menus.put(name, menu);
  }

  public void addMenuOption(String menuName, String option, int choice, String command) {
    Menu menu = menus.get(menuName);
    menu.addMenuOption(option, choice, command);
  }

  public void setPrompt(String menuName, String prompt) {
    Menu menu = menus.get(menuName);
    menu.setPrompt(prompt);
  }

  public void displayMenu(String menuName) {
    Menu menu = menus.get(menuName);
    menu.displayMenu();
  }

  public String selectOption(String menuName, int choice) {
    Menu menu = menus.get(menuName);
    return menu.select(choice);
  }
}
