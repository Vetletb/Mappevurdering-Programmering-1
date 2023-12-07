package edu.ntnu.stud;

/**
 * This is the main class for the train dispatch application.
 */
public class TrainDispatchApp {
  public static void main(String[] args) {
    var userInterface = new UserInterface();
    userInterface.init();
    userInterface.start();
  }
}
