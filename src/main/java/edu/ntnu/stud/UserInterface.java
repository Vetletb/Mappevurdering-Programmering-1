package edu.ntnu.stud;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserInterface {
  private final MenuBuilder menus = new MenuBuilder();
  private final TrainDepartureRegistry trainDepartureRegistry = new TrainDepartureRegistry();

  private LocalTime currentTime = LocalTime.of(0, 0);
  private String selectedMenu = MAIN_MENU;
  private boolean exit = false;

  private static final boolean COMMAND_MODE = false;

  private static final String MAIN_MENU = "mainMenu";
  private static final String TRAIN_MENU = "trainMenu";
  private static final String TRAIN_SEARCH_MENU = "trainSearchMenu";
  private static final String EDIT_TRAIN_MENU = "trainEditMenu";
  private static final String COMMAND_UNKNOWN_MESSAGE = "\nCommand unknown";
  private static final String COMMAND_SUCCESS_MESSAGE = "\nCommand successful";

  public void init() {
    menus.addMenu(MAIN_MENU);
    menus.setPrompt(MAIN_MENU, "Main menu");
    menus.addMenuOption(MAIN_MENU, "Configure train departures", 1, "go " + TRAIN_MENU);
    menus.addMenuOption(MAIN_MENU, "Set time", 2, "time prompt");
    menus.addMenuOption(MAIN_MENU, "View information board", 3, "train informationBoard");
    menus.addMenuOption(MAIN_MENU, "Quit", 4, "exit");

    menus.addMenu(TRAIN_MENU);
    menus.addMenuOption(TRAIN_MENU, "Add departure", 1, "train add prompt");
    menus.addMenuOption(TRAIN_MENU, "Edit departure", 2, "go " + EDIT_TRAIN_MENU);
    menus.addMenuOption(TRAIN_MENU, "Search departure", 3, "go " + TRAIN_SEARCH_MENU);
    menus.addMenuOption(TRAIN_MENU, "List all", 4, "train list");
    menus.addMenuOption(TRAIN_MENU, "Main menu", 5, "go " + MAIN_MENU);

    menus.addMenu(TRAIN_SEARCH_MENU);
    menus.addMenuOption(TRAIN_SEARCH_MENU, "Search by train number", 1, "train search trainNumber prompt");
    menus.addMenuOption(TRAIN_SEARCH_MENU, "Search by destination", 2, "train search destination prompt");
    menus.addMenuOption(TRAIN_SEARCH_MENU, "Train Configuration", 3, "go " + TRAIN_MENU);
    menus.addMenuOption(TRAIN_SEARCH_MENU, "Main menu", 4, "go " + MAIN_MENU);

    menus.addMenu(EDIT_TRAIN_MENU);
    menus.addMenuOption(EDIT_TRAIN_MENU, "Add delay", 1, "train edit addDelay prompt");
    menus.addMenuOption(EDIT_TRAIN_MENU, "Set track", 2, "train edit setTrack prompt");
    menus.addMenuOption(EDIT_TRAIN_MENU, "Train Configuration", 3, "go " + TRAIN_MENU);
    menus.addMenuOption(EDIT_TRAIN_MENU, "Main menu", 4, "go " + MAIN_MENU);


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
      if (COMMAND_MODE) {
        String command = UserInput.promptString("");
        runCommand(command);
      } else {
        String command = goToMenu(selectedMenu);
        runCommand(command);
      }
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

  private void searchByTrainNumber(int trainNumber) {
    try {
      String departure = trainDepartureRegistry.trainDepartureString(trainNumber);
      System.out.println(departure);
    } catch (IllegalArgumentException e) {
      System.out.println("\n" + "Train departure not found, reason: " + e.getMessage());
    }
  }

  private void searchByDestination(String destination) {
    TrainDepartureRegistry trainDepartures = trainDepartureRegistry
        .trainDeparturesByDestination(destination);
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
        .forEach(info -> System.out.printf("%-12s%-11s%-18s%-16s%-12s%-11s\n",
            info.get("trainNumber"),
            info.get("line"),
            info.get("destination"),
            info.get("departureTime"),
            info.get("track"),
            info.get("delay")));
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
      case "exit":
        quitCommand(commands);
        break;
      case "train":
        trainCommand(commands);
        break;
      case "time":
        timeCommand(commands);
        break;
      default:
        System.out.println(COMMAND_UNKNOWN_MESSAGE);
        break;
    }
  }

  private void goCommand(String [] commands) {
    selectedMenu = commands[1];
  }

  private void quitCommand(String [] commands) {
    if (commands.length == 1) {
      exit = true;
    } else {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
    }
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
      case "list":
        trainListCommand(command);
        break;
      default:
        System.out.println(COMMAND_UNKNOWN_MESSAGE);
        break;
    }
  }

  private void timeCommand(String [] command) {
    String nextCommandWord = command[1];
    if (nextCommandWord.equals("prompt")) {
      promptSetCurrentTime();
      waitForUser();
    } else if (command.length == 2) {
      LocalTime time = timeFromString(nextCommandWord);
      boolean success = setCurrentTime(time);
      if (success) {
        System.out.println(COMMAND_SUCCESS_MESSAGE);
      }
    } else {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
    }
  }


  private void trainAddCommand(String [] command) {
    String nextCommandWord = command[2];
    if (nextCommandWord.equals("prompt")) {
      promptAddDeparture();
      waitForUser();
    } else if (command.length == 6) {
      int trainNumber = Integer.parseInt(nextCommandWord);
      String line = command[3];
      String destination = command[4];
      String timeString = command[5];
      LocalTime departureTime = timeFromString(timeString);
      boolean success = addDeparture(trainNumber, line, destination, departureTime);
      if (success) {
        System.out.println(COMMAND_SUCCESS_MESSAGE);
      }
    } else {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
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
        System.out.println(COMMAND_UNKNOWN_MESSAGE);
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
        System.out.println(COMMAND_UNKNOWN_MESSAGE);
        break;
    }
  }

  private void informationBoardCommand(String [] command) {
    if (command.length == 2) {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
      System.out.println("\n");
      printInformationBoard();
      waitForUser();
    } else {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
    }
  }

  private void trainListCommand(String [] command) {
    if (command.length == 2) {
      System.out.println("\nTrain departures:\n");
      System.out.println(trainDepartureRegistry);
      waitForUser();
    } else {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
    }
  }

  private void searchByTrainNumberCommand(String [] command) {
    String nextCommandWord = command[3];
    if (nextCommandWord.equals("prompt")) {
      promptSearchByTrainNumber();
      waitForUser();
    } else if (command.length == 4) {
      int trainNumber = Integer.parseInt(nextCommandWord);
      searchByTrainNumber(trainNumber);
    } else {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
    }
  }

  private void searchByDestinationCommand(String [] command) {
    String nextCommandWord = command[3];
    if (nextCommandWord.equals("prompt")) {
      promptSearchByDestination();
      waitForUser();
    } else if (command.length == 4) {
      searchByDestination(nextCommandWord);
    } else {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
    }
  }

  private void addDelayCommand(String [] command) {
    String nextCommandWord = command[3];
    if (nextCommandWord.equals("prompt")) {
      promptAddDelay();
      waitForUser();
    } else if (command.length == 5) {
      int trainNumber = Integer.parseInt(nextCommandWord);
      String delayString = command[4];
      int delay = Integer.parseInt(delayString);
      boolean success = addDelay(trainNumber, delay);
      if (success) {
        System.out.println(COMMAND_SUCCESS_MESSAGE);
      }
    } else {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
    }
  }

  private void setTrackCommand(String [] command) {
    String nextCommandWord = command[3];
    if (nextCommandWord.equals("prompt")) {
      promptSetTrack();
      waitForUser();
    } else if (command.length == 5) {
      int trainNumber = Integer.parseInt(nextCommandWord);
      int track = Integer.parseInt(command[4]);
      boolean success = setTrack(trainNumber, track);
      if (success) {
        System.out.println(COMMAND_SUCCESS_MESSAGE);
      }
    } else {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
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
    return UserInput.promptInt("\nEnter delay to add in minutes:");
  }
}

