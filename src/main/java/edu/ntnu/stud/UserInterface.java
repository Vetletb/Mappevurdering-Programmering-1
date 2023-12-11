package edu.ntnu.stud;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserInterface {
  private final MenuBuilder menus = new MenuBuilder();
  private final TrainDepartureRegistry trainDepartureRegistry = new TrainDepartureRegistry();
  private LocalTime currentTime = LocalTime.of(0, 0);
  private String selectedMenu = "mainMenu";
  private boolean exit = false;

  public void init() {
    menus.addMenu("mainMenu");
    menus.setPrompt("mainMenu", "Main menu");
    menus.addMenuOption("mainMenu", "Configure train departures", 1, "go trainMenu");
    menus.addMenuOption("mainMenu", "Set time", 2, "time prompt");
    menus.addMenuOption("mainMenu", "View information board", 3, "train informationBoard");
    menus.addMenuOption("mainMenu", "Quit", 4, "quit");

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
    System.out.println("Train Departure Application v1.0");
    while (!exit) {
      String command = goToMenu(selectedMenu);
      runCommand(command);
    }
    System.out.println("\nExiting Train Departure Application...");
    System.exit(0);
  }


  private String goToMenu(String menuName) {
    System.out.println("\n");
    System.out.println("Current time " + currentTime);
    menus.displayMenu(menuName);
    int choice = UserInput.promptInt("Enter one of the options above:");
    return menus.selectOption(menuName, choice);
  }

  private void waitForUser() {
    UserInput.promptString("\nPress enter to continue...");
  }


  private boolean addDeparture(int trainNumber, String line, String destination, LocalTime departureTime) {
    boolean success = false;
    try {
      Validation.validateNotNull(departureTime, "Departure time");
      Validation.validateTimeAfter(currentTime, departureTime);
      trainDepartureRegistry.newTrainDeparture(trainNumber, line, destination, departureTime);
      success = true;
    } catch (IllegalArgumentException e) {
      System.out.println("\n" + "Departure not added, reason: " + e.getMessage());
    }
    return success;
  }

  private boolean setTrack(int trainNumber, int track) {
    boolean success = false;
    try {
      trainDepartureRegistry.setTrack(trainNumber, track);
      success = true;
    } catch (IllegalArgumentException e) {
      System.out.println("\n" + "Track not set, reason: " + e.getMessage());
    }
    return success;
  }

  private boolean addDelay(int trainNumber, int delay) {
    boolean success = false;
    try {
      trainDepartureRegistry.addDelay(trainNumber, delay);
      success = true;
    } catch (IllegalArgumentException e) {
      System.out.println("\n" + "Delay not added, reason: " + e.getMessage());
    }
    return success;
  }

  private ArrayList<Integer> sortedTrainDepartures() {
    return trainDepartureRegistry.sortedByDepartureTime();
  }

  private HashMap<String, String> trainDepartureInfo(int trainNumber) {
    return trainDepartureRegistry.getAllFromTrainNumber(trainNumber);
  }

  private boolean searchByTrainNumber(int trainNumber) {
    boolean success = false;
    try {
      String departure = trainDepartureRegistry.getTrainDepartureString(trainNumber);
      System.out.println(departure);
      success = true;
    } catch (IllegalArgumentException e) {
      System.out.println("\n" + "Train departure not found, reason: " + e.getMessage());
    }
    return success;
  }

  private void searchByDestination(String destination) {
    TrainDepartureRegistry trainDepartures = trainDepartureRegistry
        .getTrainDeparturesByDestination(destination);
    System.out.println(trainDepartures);
  }

  private void printInformationBoard() {
    System.out.printf("%-56s%s\n", "Information board", "Current time " + currentTime);
    System.out.println("-".repeat(75));
    System.out.printf("%-12s%-11s%-18s%-16s%-12s%-11s\n",
        "Train",
        "Line",
        "Destination",
        "Departure",
        "Track",
        "Delay");
    System.out.println("-".repeat(75));
    sortedTrainDepartures().stream()
        .map(this::trainDepartureInfo)
        .forEach(info -> {
          System.out.printf("%-12s%-11s%-18s%-16s%-12s%-11s\n",
              info.get("trainNumber"),
              info.get("line"),
              info.get("destination"),
              info.get("departureTime"),
              info.get("track"),
              info.get("delay"));
        });
    System.out.println("-".repeat(75));
  }

  private void updateDeparted() {
    trainDepartureRegistry.removeTrainDeparturesBeforeTime(currentTime);
  }

  private boolean setCurrentTime(LocalTime time) {
    boolean success = false;
    try {
      Validation.validateNotNull(time, "Time");
      Validation.validateTimeAfter(currentTime, time);
      currentTime = time;
      updateDeparted();
      success = true;
    } catch (IllegalArgumentException e) {
      System.out.println("\n" + "Time not set, reason: " + e.getMessage());
    }
    return success;
  }


  private LocalTime timeFromString(String timeString) {
    LocalTime time = null;
    try {
      time = LocalTime.parse(timeString);
    } catch (DateTimeParseException e) {
      System.out.println("\nTime must be in the format HH:mm");
    }
    return time;
  }


  private void runCommand(String command) {
    String [] commands = command.split(" ");
    String primaryCommand = commands[0];
    switch (primaryCommand) {
      case "go":
        goCommand(commands);
        break;
      case "quit":
        quitCommand(commands);
        break;
      case "train":
        trainCommand(commands);
        break;
      case "time":
        timeCommand(commands);
        break;
      default:
        System.out.println("\nUnknown command");
        break;
    }
  }

  private void goCommand(String [] commands) {
    selectedMenu = commands[1];
  }

  private void quitCommand(String [] commands) {
    exit = true;
  }

  private void trainCommand(String [] command) {
    String nextCommandWord = command[1];
    switch (nextCommandWord) {
      case "add":
        trainAddCommand(command);
        break;
      case "edit":
        editTrainCommand(command);
        break;
      case "search":
        trainSearchCommand(command);
        break;
      case "informationBoard":
        informationBoardCommand(command);
        break;
      default:
        System.out.println("\nUnknown command");
        break;
    }
  }

  private void timeCommand(String [] command) {
    String nextCommandWord = command[1];
    if (nextCommandWord.equals("prompt")) {
      promptSetCurrentTime();
      waitForUser();
    } else if (command.length == 4) {
      String timeString = command[2];
      LocalTime time = timeFromString(timeString);
      setCurrentTime(time);
    } else {
      System.out.println("\nUnknown command");
    }
  }


  private void trainAddCommand(String [] command) {
    String nextCommandWord = command[2];
    if (nextCommandWord.equals("prompt")) {
      promptAddDeparture();
      waitForUser();
    } else if (command.length == 7) {
      int trainNumber = Integer.parseInt(command[3]);
      String line = command[4];
      String destination = command[5];
      String timeString = command[6];
      LocalTime departureTime = timeFromString(timeString);
      addDeparture(trainNumber, line, destination, departureTime);
    } else {
      System.out.println("\nUnknown command");
    }
  }

  private void trainSearchCommand(String [] command) {
    String searchType = command[2];
    switch (searchType) {
      case "trainNumber":
        searchByTrainNumberCommand(command);
        break;
      case "destination":
        searchByDestinationCommand(command);
        break;
      default:
        System.out.println("\nUnknown command");
        break;
    }
  }

  private void editTrainCommand(String [] command) {
    String nextCommandWord = command[2];
    switch (nextCommandWord) {
      case "addDelay":
        addDelayCommand(command);
        break;
      case "setTrack":
        setTrackCommand(command);
        break;
      default:
        System.out.println("\nUnknown command");
        break;
    }
  }

  private void informationBoardCommand(String [] command) {
    System.out.println("\n");
    printInformationBoard();
    waitForUser();
  }

  private void searchByTrainNumberCommand(String [] command) {
    String nextCommandWord = command[3];
    if (nextCommandWord.equals("prompt")) {
      promptSearchByTrainNumber();
      waitForUser();
    } else if (command.length == 5) {
      int trainNumber = Integer.parseInt(command[4]);
      searchByTrainNumber(trainNumber);
    } else {
      System.out.println("\nUnknown command");
    }
  }

  private void searchByDestinationCommand(String [] command) {
    String nextCommandWord = command[3];
    if (nextCommandWord.equals("prompt")) {
      promptSearchByDestination();
      waitForUser();
    } else if (command.length == 5) {
      String destination = command[4];
      searchByDestination(destination);
    } else {
      System.out.println("\nUnknown command");
    }
  }

  private void addDelayCommand(String [] command) {
    String nextCommandWord = command[3];
    if (nextCommandWord.equals("prompt")) {
      promptAddDelay();
      waitForUser();
    } else if (command.length == 5) {
      int trainNumber = Integer.parseInt(command[3]);
      String delayString = command[4];
      int delay = Integer.parseInt(delayString);
      addDelay(trainNumber, delay);
    } else {
      System.out.println("\nUnknown command");
    }
  }

  private void setTrackCommand(String [] command) {
    String nextCommandWord = command[3];
    if (nextCommandWord.equals("prompt")) {
      promptSetTrack();
      waitForUser();
    } else if (command.length == 5) {
      int trainNumber = Integer.parseInt(command[3]);
      int track = Integer.parseInt(command[4]);
      setTrack(trainNumber, track);
    } else {
      System.out.println("\nUnknown command");
    }
  }


  private void promptAddDeparture() {
    int trainNumber = promptTrainNumber();
    String line = promptLine();
    String destination = promptDestination();
    LocalTime departureTime = promptTime();
    boolean success = addDeparture(trainNumber, line, destination, departureTime);
    if (success) {
      System.out.println("\nTrain departure added successfully:");
      searchByTrainNumber(trainNumber);
    }
  }

  private void promptSetTrack() {
    int trainNumber = promptTrainNumber();
    int track = promptTrack();
    boolean success = setTrack(trainNumber, track);
    if (success) {
      System.out.println("\nUpdated train info:");
      searchByTrainNumber(trainNumber);
    }
  }

  private void promptAddDelay() {
    int trainNumber = promptTrainNumber();
    int delay = promptDelay();
    boolean success = addDelay(trainNumber, delay);
    if (success) {
      System.out.println("\nUpdated train info:");
      searchByTrainNumber(trainNumber);
    }
  }

  private void promptSearchByTrainNumber() {
    int trainNumber = promptTrainNumber();
    System.out.println("\nTrain departure info:");
    searchByTrainNumber(trainNumber);
  }

  private void promptSearchByDestination() {
    String destination = promptDestination();
    System.out.println("\nTrain departures to " + destination + ":");
    searchByDestination(destination);
  }

  private void promptSetCurrentTime() {
    LocalTime time = promptTime();
    boolean success = setCurrentTime(time);
    if (success) {
      System.out.println("\nTime set to " + time);
    }
  }

  private int promptTrainNumber() {
    return UserInput.promptInt("\nEnter train number:");
  }

  private LocalTime promptTime() {
    String timeString = UserInput.promptString("\nEnter time (format HH:mm):");
    return timeFromString(timeString);
  }

  private String promptDestination() {
    return UserInput.promptString("\nEnter destination:");
  }

  private String promptLine() {
    return UserInput.promptString("\nEnter line:");
  }

  private int promptTrack() {
    return UserInput.promptInt("\nEnter track:");
  }

  private int promptDelay() {
    return UserInput.promptInt("\nEnter delay:");
  }
}

