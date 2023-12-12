package edu.ntnu.stud.view;

import edu.ntnu.stud.utils.UserInput;
import edu.ntnu.stud.utils.Validation;
import edu.ntnu.stud.models.TrainDepartureRegistry;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the user interface. It contains methods for running the application.
 * It handles user interaction and processes commands.
 *
 * @author 10065
 * @version 1.0
 * @since 1.0
 */
public class UserInterface {
  private final MenuBuilder menus = new MenuBuilder();
  private final TrainDepartureRegistry trainDepartureRegistry = new TrainDepartureRegistry();

  // default values
  private LocalTime currentTime = LocalTime.of(0, 0);
  private String selectedMenu = MAIN_MENU;
  private boolean exit = false;

  // whether to use menu or command mode
  private static final boolean COMMAND_MODE = false;

  // constants for menu names
  private static final String MAIN_MENU = "mainMenu";
  private static final String TRAIN_MENU = "trainMenu";
  private static final String TRAIN_SEARCH_MENU = "trainSearchMenu";
  private static final String EDIT_TRAIN_MENU = "trainEditMenu";
  // constants for frequently used strings
  private static final String COMMAND_UNKNOWN_MESSAGE = "\nCommand unknown";
  private static final String COMMAND_SUCCESS_MESSAGE = "\nCommand successful";

  /**
   * Initializes the user interface, creates menus and adds menu options.
   */
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
    menus.addMenuOption(TRAIN_SEARCH_MENU, "Search by train number", 1,
        "train search trainNumber prompt");
    menus.addMenuOption(TRAIN_SEARCH_MENU, "Search by destination", 2,
        "train search destination prompt");
    menus.addMenuOption(TRAIN_SEARCH_MENU, "Train Configuration", 3, "go " + TRAIN_MENU);
    menus.addMenuOption(TRAIN_SEARCH_MENU, "Main menu", 4, "go " + MAIN_MENU);

    menus.addMenu(EDIT_TRAIN_MENU);
    menus.addMenuOption(EDIT_TRAIN_MENU, "Add delay", 1, "train edit addDelay prompt");
    menus.addMenuOption(EDIT_TRAIN_MENU, "Set track", 2, "train edit setTrack prompt");
    menus.addMenuOption(EDIT_TRAIN_MENU, "Train Configuration", 3, "go " + TRAIN_MENU);
    menus.addMenuOption(EDIT_TRAIN_MENU, "Main menu", 4, "go " + MAIN_MENU);

    // train departures for testing
    trainDepartureRegistry.newTrainDeparture(10, "A4", "Trondheim", LocalTime.of(6, 15));
    trainDepartureRegistry.newTrainDeparture(4, "A7", "Oslo", LocalTime.of(14, 45));
    trainDepartureRegistry.newTrainDeparture(54, "F6", "Bergen", LocalTime.of(6, 0));
    trainDepartureRegistry.newTrainDeparture(109, "B4", "Kristiansand", LocalTime.of(14, 45));
    trainDepartureRegistry.newTrainDeparture(1, "A2", "Stavanger", LocalTime.of(20, 5));
    trainDepartureRegistry.newTrainDeparture(16, "G8", "Fredrikstad", LocalTime.of(5, 50));
  }

  /**
   * Starts the user interface, runs the application.
   */
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


  /**
   * Displays a menu and prompts the user to chose option.
   *
   * @param menuName the name of the menu to display
   * @return the command to run
   */
  private String goToMenu(String menuName) {
    System.out.println("\n");
    System.out.println("Current time " + currentTime);
    menus.displayMenu(menuName);
    int choice = UserInput.promptInt("Enter one of the options above:");
    return menus.selectOption(menuName, choice);
  }

  /**
   * Waits for user to press enter.
   */
  private void waitForUser() {
    UserInput.promptString("\nPress enter to continue...");
  }


  /**
   * Adds a new train departure to the registry.
   *
   * @param trainNumber   the train number
   * @param line          the line
   * @param destination   the destination
   * @param departureTime the departure time
   * @return true if train departure was added successfully, false otherwise
   */
  private boolean addDeparture(int trainNumber, String line, String destination,
                               LocalTime departureTime) {
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

  /**
   * Sets the track of a train departure from train number.
   *
   * @param trainNumber the train number of the train departure
   * @param track       the track to set
   * @return true if track was set successfully, false otherwise
   */
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

  /**
   * Adds delay to a train departure from train number.
   *
   * @param trainNumber the train number of the train departure
   * @param delay       the delay to add
   * @return true if delay was added successfully, false otherwise
   */
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

  /**
   * Returns a sorted list of train departures.
   *
   * @return a sorted list of train departures
   */
  private ArrayList<Integer> sortedTrainDepartures() {
    return trainDepartureRegistry.sortedByDepartureTime();
  }

  /**
   * Returns a HashMap with train departure information from train number.
   *
   * @param trainNumber the train number of the train departure
   * @return a HashMap with train departure information
   */
  private HashMap<String, String> trainDepartureInfo(int trainNumber) {
    return trainDepartureRegistry.getAllFromTrainNumber(trainNumber);
  }

  /**
   * Searches for a train departure from train number and prints the result.
   *
   * @param trainNumber the train number of the train departure
   */
  private void searchByTrainNumber(int trainNumber) {
    try {
      String departure = trainDepartureRegistry.trainDepartureString(trainNumber);
      System.out.println(departure);
    } catch (IllegalArgumentException e) {
      System.out.println("\n" + "Train departure not found, reason: " + e.getMessage());
    }
  }

  /**
   * Searches for train departures from destination and prints the result.
   *
   * @param destination the destination of the train departures
   */
  private void searchByDestination(String destination) {
    TrainDepartureRegistry trainDepartures = trainDepartureRegistry
        .trainDeparturesByDestination(destination);
    System.out.println(trainDepartures);
  }

  /**
   * Prints the train departures in the form of an information board.
   */
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

  /**
   * Removes train departures before current time.
   */
  private void updateDeparted() {
    trainDepartureRegistry.removeTrainDeparturesBeforeTime(currentTime);
  }

  /**
   * Sets the current time.
   *
   * @param time the time to set
   * @return true if time was set successfully, false otherwise
   */
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


  /**
   * Parses a string to LocalTime.
   *
   * @param timeString the string to parse
   * @return the parsed LocalTime
   */
  private LocalTime timeFromString(String timeString) {
    LocalTime time = null;
    try {
      time = LocalTime.parse(timeString);
    } catch (DateTimeParseException e) {
      System.out.println("\nTime must be in the format HH:mm");
    }
    return time;
  }


  /**
   * Starts to process a command.
   *
   * @param command the command to run
   */
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

  /**
   * Selects menu from command.
   *
   * @param commands the command (go "menuName")
   */
  private void goCommand(String [] commands) {
    selectedMenu = commands[1];
  }

  /**
   * Tells the application to quit.
   *
   * @param commands the command (exit)
   */
  private void quitCommand(String [] commands) {
    if (commands.length == 1) {
      exit = true;
    } else {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
    }
  }

  /**
   * Processes commands for train departures.
   *
   * @param command the command to run
   */
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

  /**
   * Prompts the user to set the current time or sets the current time from command.
   *
   * @param command the command (time prompt or time "HH:mm")
   */
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

  /**
   * Prompts the user to add a train departure or adds a train departure from command.
   *
   * @param command the command (train add prompt or
   *                train add "trainNumber" "line" "destination" "HH:mm")
   */
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

  /**
   * Processes commands for train search.
   *
   * @param command the command to run
   */
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

  /**
   * Processes commands for editing train departures.
   *
   * @param command the command to run
   */
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

  /**
   * Displays the information board from command.
   *
   * @param command the command (train informationBoard)
   */
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

  /**
   * Displays the list of train departures from command.
   *
   * @param command the command (train list)
   */
  private void trainListCommand(String [] command) {
    if (command.length == 2) {
      System.out.println("\nTrain departures:\n");
      System.out.println(trainDepartureRegistry);
      waitForUser();
    } else {
      System.out.println(COMMAND_UNKNOWN_MESSAGE);
    }
  }

  /**
   * Prompts the user to search for a train departure by train number or searches by command.
   *
   * @param command the command (train search trainNumber prompt or
   *                train search trainNumber "number")
   */
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

  /**
   * Prompts the user to search for train departures by destination or searches by command.
   *
   * @param command the command (train search destination prompt or
   *                train search destination "destination")
   */
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

  /**
   * Prompts the user to add delay to a train departure or adds delay from command.
   *
   * @param command the command (train edit addDelay prompt or
   *                train edit addDelay "trainNumber" "delay")
   */
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

  /**
   * Prompts the user to set the track of a train departure or sets the track from command.
   *
   * @param command the command (train edit setTrack prompt or
   *                train edit setTrack "trainNumber" "track")
   */
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


  /**
   * Prompts the user to add a train departure.
   */
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

  /**
   * Prompts the user to set the track of a train departure.
   */
  private void promptSetTrack() {
    int trainNumber = promptTrainNumber();
    int track = promptTrack();
    boolean success = setTrack(trainNumber, track);
    if (success) {
      System.out.println("\nUpdated train info:");
      searchByTrainNumber(trainNumber);
    }
  }

  /**
   * Prompts the user to add delay to a train departure.
   */
  private void promptAddDelay() {
    int trainNumber = promptTrainNumber();
    int delay = promptDelay();
    boolean success = addDelay(trainNumber, delay);
    if (success) {
      System.out.println("\nUpdated train info:");
      searchByTrainNumber(trainNumber);
    }
  }

  /**
   * Prompts the user to search for a train departure by train number.
   */
  private void promptSearchByTrainNumber() {
    int trainNumber = promptTrainNumber();
    System.out.println("\nTrain departure info:");
    searchByTrainNumber(trainNumber);
  }

  /**
   * Prompts the user to search for train departures by destination.
   */
  private void promptSearchByDestination() {
    String destination = promptDestination();
    System.out.println("\nTrain departures to " + destination + ":");
    searchByDestination(destination);
  }

  /**
   * Prompts the user to set the current time.
   */
  private void promptSetCurrentTime() {
    LocalTime time = promptTime();
    boolean success = setCurrentTime(time);
    if (success) {
      System.out.println("\nTime set to " + time);
    }
  }

  /**
   * Prompts the user for a train number.
   */
  private int promptTrainNumber() {
    return UserInput.promptInt("\nEnter train number:");
  }

  /**
   * Prompts the user for a time.
   */
  private LocalTime promptTime() {
    String timeString = UserInput.promptString("\nEnter time (format HH:mm):");
    return timeFromString(timeString);
  }

  /**
   * Prompts the user for a destination.
   */
  private String promptDestination() {
    return UserInput.promptString("\nEnter destination:");
  }

  /**
   * Prompts the user for a line.
   */
  private String promptLine() {
    return UserInput.promptString("\nEnter line:");
  }

  /**
   * Prompts the user for a track.
   */
  private int promptTrack() {
    return UserInput.promptInt("\nEnter track:");
  }

  /**
   * Prompts the user for a delay.
   */
  private int promptDelay() {
    return UserInput.promptInt("\nEnter delay to add in minutes:");
  }
}

