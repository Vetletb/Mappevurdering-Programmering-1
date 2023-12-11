package edu.ntnu.stud;

import java.util.ArrayList;

public class Menu {

  private final ArrayList<MenuOption> menuOptions = new ArrayList<>();
  private String prompt = "Please select an option:";

  public void addMenuOption(String option, int choice, String command) {
    MenuOption menuOption = new MenuOption(option, choice, command);
    menuOptions.add(menuOption);
  }

  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  public void displayMenu() {
    System.out.println("-".repeat(40));
    System.out.println(prompt);
    System.out.println("-".repeat(40));
    menuOptions.forEach(System.out::println);
    System.out.println("-".repeat(40));
  }

  public String select(int choice) {
    return menuOptions.stream()
        .filter(options -> options.getChoice() == choice)
        .findFirst()
        .map(MenuOption::getCommand)
        .orElse("");
  }
}
