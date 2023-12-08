package edu.ntnu.stud;

import java.time.LocalTime;

public class UserInterface {
  private final MenuBuilder menus = new MenuBuilder();
  private final TrainDepartureRegistry trainDepartureRegistry = new TrainDepartureRegistry();
  private LocalTime currentTime = LocalTime.of(0, 0);
  private String selectedMenu = "mainMenu";

  public void init() {
    menus.addMenu("mainMenu");
    menus.setPrompt("mainMenu", "Main menu");
    menus.addMenuOption("mainMenu", "Configure train departures", 1, "go trainMenu");
    menus.addMenuOption("mainMenu", "Set time", 2, "time prompt");
    menus.addMenuOption("mainMenu", "View information board", 3, "train informationBoard");
    menus.addMenuOption("mainMenu", "Help", 4, "help");
    menus.addMenuOption("mainMenu", "Quit", 5, "quit");

    menus.addMenu("trainMenu");
    menus.addMenuOption("trainMenu", "Add departure", 1, "train add prompt");
    menus.addMenuOption("trainMenu", "Edit departure", 2, "go editTrainMenu");
    menus.addMenuOption("trainMenu", "Search departure", 3, "go trainSearchMenu");
    menus.addMenuOption("trainMenu", "List all", 4, "train list");
    menus.addMenuOption("trainMenu", "Main menu", 5, "go mainMenu");

    menus.addMenu("trainSearchMenu");
    menus.addMenuOption("trainSearchMenu", "Search by train number", 1, "train search trainNumber prompt");
    menus.addMenuOption("trainSearchMenu", "Search by destination", 2, "train search destination prompt");
    menus.addMenuOption("trainSearchMenu", "Train Configuration", 3, "go trainMenu");
    menus.addMenuOption("trainSearchMenu", "Main menu", 4, "go mainMenu");

    menus.addMenu("editTrainMenu");
    menus.addMenuOption("editTrainMenu", "Add delay", 1, "train edit addDelay prompt");
    menus.addMenuOption("editTrainMenu", "Set track", 2, "train edit setTrack prompt");
    menus.addMenuOption("editTrainMenu", "Train Configuration", 3, "go trainMenu");
    menus.addMenuOption("editTrainMenu", "Main menu", 4, "go mainMenu");


    trainDepartureRegistry.newTrainDeparture(10, "A4", "Trondheim", LocalTime.of(6, 15));
    trainDepartureRegistry.newTrainDeparture(4, "A7", "Oslo", LocalTime.of(14, 45));
    trainDepartureRegistry.newTrainDeparture(54, "F6", "Bergen", LocalTime.of(6, 0));
    trainDepartureRegistry.newTrainDeparture(109, "B4", "Kristiansand", LocalTime.of(14, 45));
    trainDepartureRegistry.newTrainDeparture(1, "A2", "Stavanger", LocalTime.of(20, 5));
    trainDepartureRegistry.newTrainDeparture(16, "G8", "Fredrikstad", LocalTime.of(5, 50));
  }

  public void start() {
    while (true) {
      String command = goToMenu(selectedMenu);
      runCommand(command);
    }
  }


  private String goToMenu(String menuName) {
    menus.displayMenu(menuName);
    int choice = UserInput.promptInt("Enter one of the options above:");
    return menus.selectOption(menuName, choice);
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

  private void setCurrentTime(LocalTime time) {
    validateTimeNotBefore(currentTime, time);
    updateDeparted();
    currentTime = time;
  }

  public LocalTime timeFromString(String timeString) {
    String[] timeArray = timeString.split(":");
    int hours = Integer.parseInt(timeArray[0]);
    int minutes = Integer.parseInt(timeArray[1]);
    return LocalTime.of(hours, minutes);
  }

  public void validateTimeNotBefore(LocalTime currentTime, LocalTime newTime) {
    if (newTime.isBefore(currentTime)) {
      throw new IllegalArgumentException("Time cannot be before current time.");
    }
  }


  private void runCommand(String command) {
    String [] commands = command.split(" ");
    String primaryCommand = commands[0];
    switch (primaryCommand) {
      case "go":
        goCommand(commands);
        break;
      case "quit":
        quitCommand();
        break;
      case "train":
        trainCommand(commands);
        break;
      case "time":
        timeCommand(commands);
        break;
      default:
        System.out.println("Unknown command: " + command);
        break;
    }
  }
  }

  private void promptAddDeparture() {
    int trainNumber = promptTrainNumber();
    String line = promptLine();
    String destination = promptDestination();
    LocalTime departureTime = promptTime();
    addDeparture(trainNumber, line, destination, departureTime);
  }

  private void promptAddDelay() {
    int trainNumber = promptTrainNumber();
    LocalTime delay = promptTime();
    addDelay(trainNumber, delay);
    System.out.println("Updated train info:");
    searchByTrainNumber(trainNumber);
  }

  private void promptSetTrack() {
    int trainNumber = promptTrainNumber();
    int track = promptTrack();
    setTrack(trainNumber, track);
    System.out.println("Updated train info:");
    searchByTrainNumber(trainNumber);
  }

  private void promptSearchByTrainNumber() {
    int trainNumber = promptTrainNumber();
    searchByTrainNumber(trainNumber);
  }

  private void promptSearchByDestination() {
    String destination = promptDestination();
    searchByDestination(destination);
  }

  private void promptSetCurrentTime() {
    LocalTime time = promptTime();
    setCurrentTime(time);
    System.out.println("Time set to " + time);
  }

  private int promptTrainNumber() {
    return UserInput.promptInt("Enter train number:");
  }

  private String promptDestination() {
    return UserInput.promptString("Enter destination:");
  }

  private LocalTime promptTime() {
    String timeString = UserInput.promptString("Enter time:");
    return timeFromString(timeString);
  }

  private String promptLine() {
    return UserInput.promptString("Enter line:");
  }

  private int promptTrack() {
    return UserInput.promptInt("Enter track:");
  }
}

