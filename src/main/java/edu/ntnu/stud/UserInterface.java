package edu.ntnu.stud;

import java.time.LocalTime;

public class UserInterface {
  private final MenuBuilder menus = new MenuBuilder();
  private final TrainDepartureRegistry trainDepartureRegistry = new TrainDepartureRegistry();
  private LocalTime currentTime = LocalTime.of(0, 0);

  public void init() {
    menus.addMenu("mainMenu");
    menus.setPrompt("mainMenu", "Main menu");
    menus.addMenuOption("mainMenu", "Configure train departures", 1, "go trainMenu");
    menus.addMenuOption("mainMenu", "Set time", 2, "time prompt");
    menus.addMenuOption("mainMenu", "View information board", 3, "train informationBoard");
    menus.addMenuOption("mainMenu", "Help", 4, "help");
    menus.addMenuOption("mainMenu", "Quit", 5, "quit");

    trainDepartureRegistry.newTrainDeparture(10, "A4", "Trondheim", LocalTime.of(6, 15));
    trainDepartureRegistry.newTrainDeparture(4, "A7", "Oslo", LocalTime.of(14, 45));
    trainDepartureRegistry.newTrainDeparture(54, "F6", "Bergen", LocalTime.of(6, 0));
    trainDepartureRegistry.newTrainDeparture(109, "B4", "Kristiansand", LocalTime.of(14, 45));
    trainDepartureRegistry.newTrainDeparture(1, "A2", "Stavanger", LocalTime.of(20, 5));
    trainDepartureRegistry.newTrainDeparture(16, "G8", "Fredrikstad", LocalTime.of(5, 50));
  }

  public void start() {
    menus.displayMenu("mainMenu");
  }

  private void addDeparture(int trainNumber, String line, String destination, LocalTime departureTime) {
    trainDepartureRegistry.newTrainDeparture(trainNumber, line, destination, departureTime);
  }

  private void addDelay(int trainNumber, LocalTime delay) {
    trainDepartureRegistry.addDelay(trainNumber, delay);
  }

  private void setTrack(int trainNumber, int track) {
    trainDepartureRegistry.setTrack(trainNumber, track);
  }

  private void searchByTrainNumber(int trainNumber) {
    String departure = trainDepartureRegistry.getTrainDepartureString(trainNumber);
    System.out.println(departure);
  }

  private void searchByDestination(String destination) {
    TrainDepartureRegistry trainDepartures = trainDepartureRegistry
        .getTrainDeparturesByDestination(destination);
    System.out.println(trainDepartures);
  }

  private void updateDeparted() {
    trainDepartureRegistry.removeTrainDeparturesBeforeTime(currentTime);
  }

  private void printInformationBoard() {
    System.out.println(trainDepartureRegistry);
  }
}

