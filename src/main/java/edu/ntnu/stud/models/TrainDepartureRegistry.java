package edu.ntnu.stud.models;

import edu.ntnu.stud.utils.Validation;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a train departure registry. It contains a HashMap of train departures
 * and methods to manage them.
 *
 * @author 10065
 * @version 1.0
 * @since 0.2
 */
public class TrainDepartureRegistry {

  private final HashMap<Integer, TrainDeparture> trainDepartures = new HashMap<>();

  /**
   * Gets a train departure from train number.
   *
   * @param trainNumber the train number of the train departure
   * @return the train departure with the train number
   * @throws IllegalArgumentException if train number is not in registry
   */
  private TrainDeparture getTrainDeparture(int trainNumber) {
    validateTrainNumberExistence(trainNumber);
    return trainDepartures.get(trainNumber);
  }

  /**
   * Creates and adds a new train departure, utilizes {@link #addTrainDeparture(TrainDeparture)}.
   *
   * @param trainNumber   the train number
   * @param line          the line
   * @param destination   the destination
   * @param departureTime the departure time
   * @throws IllegalArgumentException if train number is zero or less
   * @throws IllegalArgumentException if line is blank
   * @throws IllegalArgumentException if destination is blank
   * @throws IllegalArgumentException if departure time is null
   */
  public void newTrainDeparture(int trainNumber, String line, String destination,
                                LocalTime departureTime) {
    var trainDeparture = new TrainDeparture(trainNumber, line, destination, departureTime);
    addTrainDeparture(trainDeparture);
  }

  /**
   * Adds a train departure to registry.
   *
   * @param trainDeparture the train departure
   * @throws IllegalArgumentException if train number already exists
   */
  private void addTrainDeparture(TrainDeparture trainDeparture) {
    if (containsTrainDeparture(trainDeparture)) {
      throw new IllegalArgumentException("Train number already exists");
    }
    trainDepartures.put(trainDeparture.getTrainNumber(), trainDeparture);
  }

  /**
   * Adds delay to train departure from train number.
   *
   * @param trainNumber the train number of the train departure
   * @param delay       the delay
   * @throws IllegalArgumentException if train number is not in registry
   * @throws IllegalArgumentException if delay is zero or less
   */
  public void addDelay(int trainNumber, int delay) {
    validateTrainNumberExistence(trainNumber);
    trainDepartures.get(trainNumber).addDelay(delay);
  }

  /**
   * Sets track to train departure from train number.
   *
   * @param trainNumber the train number of the train departure
   * @param track       the track
   * @throws IllegalArgumentException if train number is not in registry
   * @throws IllegalArgumentException if track is zero or less, unless -1
   */
  public void setTrack(int trainNumber, int track) {
    validateTrainNumberExistence(trainNumber);
    trainDepartures.get(trainNumber).setTrack(track);
  }


  /**
   * Checks if registry contains train departure, utilizes {@link #containsTrainNumber(int)}.
   *
   * @param trainDeparture the train departure to check
   * @return true if registry contains train departure
   */
  public boolean containsTrainDeparture(TrainDeparture trainDeparture) {
    return containsTrainNumber(trainDeparture.getTrainNumber());
  }

  /**
   * Checks if registry contains train departure with train number.
   *
   * @param trainNumber the train number to check
   * @return true if registry contains train departure with train number
   */
  public boolean containsTrainNumber(int trainNumber) {
    return trainDepartures.containsKey(trainNumber);
  }

  /**
   * Validates train departure presence from train number,
   * utilizes {@link #containsTrainNumber(int)}.
   *
   * @param trainNumber the train number to check
   * @throws IllegalArgumentException if train number is not in registry
   */
  public void validateTrainNumberExistence(int trainNumber) {
    if (!containsTrainNumber(trainNumber)) {
      throw new IllegalArgumentException("Train departure is not in the registry");
    }
  }

  /**
   * Deletes a train departure from train number.
   *
   * @param trainNumber the train number
   * @throws IllegalArgumentException if train number is not in registry
   */
  public void removeTrainDeparture(int trainNumber) {
    validateTrainNumberExistence(trainNumber);
    trainDepartures.remove(trainNumber);
  }

  /**
   * Deletes train departures before time.
   *
   * @param time the time to delete before
   * @throws IllegalArgumentException if time is null
   */
  public void removeTrainDeparturesBeforeTime(LocalTime time) {
    Validation.validateNotNull(time, "Time");
    List<Integer> trainsToRemove = trainDepartures.values().stream()
        .filter(trainDeparture -> trainDeparture.departureTimeWithDelay().isBefore(time))
        .map(TrainDeparture::getTrainNumber)
        .toList();
    trainsToRemove.forEach(this::removeTrainDeparture);
  }


  /**
   * Creates new train departure registry filtered from destination.
   *
   * @param destination the destination to filter from
   * @throws IllegalArgumentException if destination is blank
   */
  public TrainDepartureRegistry trainDeparturesByDestination(String destination) {
    Validation.validateStringNotBlank(destination, "Destination");
    var result = new TrainDepartureRegistry();
    trainDepartures.values().stream()
        .filter(trainDeparture -> trainDeparture.getDestination().equals(destination))
        .forEach(result::addTrainDeparture);
    return result;
  }

  /**
   * Returns a list of train numbers sorted by departure time.
   *
   * @return a list of train numbers sorted by departure time
   */
  public ArrayList<Integer> sortedByDepartureTime() {
    return trainDepartures.entrySet().stream()
        .sorted(Comparator.comparing(entry -> entry.getValue().getDepartureTime()))
        .map(Map.Entry::getKey)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Returns a string representation of the train departure from train number.
   *
   * @param trainNumber the train number
   * @return a string representation of the train departure from train number
   * @throws IllegalArgumentException if train number is not in registry
   */
  public String trainDepartureString(int trainNumber) {
    validateTrainNumberExistence(trainNumber);
    return getTrainDeparture(trainNumber).toString();
  }

  /**
   * Returns a HashMap of information about the train departure from train number.
   *
   * @param trainNumber the train number of the train departure
   * @return a Hashmap of information about the train departure from train number
   * @throws IllegalArgumentException if train number is not in registry
   */
  public HashMap<String, String> getAllFromTrainNumber(int trainNumber) {
    validateTrainNumberExistence(trainNumber);
    return trainDepartures.get(trainNumber).trainInfo();
  }

  /**
   * Returns a string representation of the train departure registry.
   *
   * @return a string representation of the train departure registry
   */
  @Override
  public String toString() {
    return trainDepartures.values().stream()
        .map(TrainDeparture::toString)
        .collect(Collectors.joining("\n"));
  }
}

