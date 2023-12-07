package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class represents a train departure registry.
 */
public class TrainDepartureRegistry {

  private final HashMap<Integer, TrainDeparture> trainDepartures = new HashMap<>();

  /**
   * Gets a train departure from train number.
   *
   * @param trainNumber the train departures
   */
  public TrainDeparture getTrainDeparture(int trainNumber) {
    validateTrainNumberExistence(trainNumber);
    return trainDepartures.get(trainNumber);
  }

  public String getTrainDepartureString(int trainNumber) {
    return trainDepartures.get(trainNumber).toString();
  }

  /**
   * Creates and adds a new train departure.
   *
   * @param trainNumber   the train number
   * @param line          the line
   * @param destination   the destination
   * @param departureTime the departure time
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
   */
  private void addTrainDeparture(TrainDeparture trainDeparture) {
    if (containsTrainDeparture(trainDeparture)) {
      throw new IllegalArgumentException("Train number already exists");
    }
    trainDepartures.put(trainDeparture.getTrainNumber(), trainDeparture);
  }

  public void addDelay(int trainNumber, LocalTime delay) {
    validateTrainNumberExistence(trainNumber);
    trainDepartures.get(trainNumber).addDelay(delay);
  }

  public void setTrack(int trainNumber, int track) {
    validateTrainNumberExistence(trainNumber);
    trainDepartures.get(trainNumber).setTrack(track);
  }

  /**
   * Creates train departure registry filtered from destination.
   *
   * @param destination the destination
   */
  public TrainDepartureRegistry getTrainDeparturesByDestination(String destination) {
    validateStringNotBlank(destination, "Destination");
    var result = new TrainDepartureRegistry();
    trainDepartures.values().stream()
        .filter(trainDeparture -> trainDeparture.getDestination().equals(destination))
        .forEach(result::addTrainDeparture);
    return result;
  }

  /**
   * Checks if registry contains train departure.
   *
   * @param trainDeparture the train departure
   */
  public boolean containsTrainDeparture(TrainDeparture trainDeparture) {
    return containsTrainNumber(trainDeparture.getTrainNumber());
  }

  /**
   * Checks if registry contains train number.
   *
   * @param trainNumber the train number
   */
  public boolean containsTrainNumber(int trainNumber) {
    return trainDepartures.containsKey(trainNumber);
  }

  /**
   * Validates train number presence.
   *
   * @param trainNumber the train number
   */
  public void validateTrainNumberExistence(int trainNumber) {
    if (!containsTrainNumber(trainNumber)) {
      throw new IllegalArgumentException("Train departure is not in the registry");
    }
  }

  /**
   * Validates train departure presence.
   *
   * @param trainDeparture the train departure
   */
  public void validateTrainDepartureExistence(TrainDeparture trainDeparture) {
    if (!containsTrainDeparture(trainDeparture)) {
      throw new IllegalArgumentException("Train departure is not in the registry");
    }
  }



  /**
   * Deletes a train departure.
   *
   * @param trainNumber the train number
   */
  public void removeTrainDeparture(int trainNumber) {
    validateTrainNumberExistence(trainNumber);
    trainDepartures.remove(trainNumber);
  }

  /**
   * Deletes train departures before time.
   *
   * @param time the time
   */
  public void removeTrainDeparturesBeforeTime(LocalTime time) {
    validateNotNull(time, "Time");
    List<Integer> trainsToRemove = trainDepartures.values().stream()
        .filter(trainDeparture -> trainDeparture.departureTimeWithDelay().isBefore(time))
        .map(TrainDeparture::getTrainNumber)
        .toList();
    trainsToRemove.forEach(this::removeTrainDeparture);
  }

  /**
   * Returns a list of train numbers sorted by departure time.
   *
   * @return a list of train numbers sorted by departure time
   */
  public List<Integer> sortedByDepartureTime() {
    return trainDepartures.entrySet().stream()
        .sorted(Comparator.comparing(entry -> entry.getValue().getDepartureTime()))
        .map(Map.Entry::getKey)
        .toList();
  }

  /**
   * Returns a list of train numbers sorted by departure time.
   *
   * @return a list of train numbers sorted by departure time
   */
  @Override
  public String toString() {
    return trainDepartures.values().stream()
        .map(TrainDeparture::toString)
        .collect(Collectors.joining("\n"));
  }

  public void validateNotNull(Object object, String name) {
    if (object == null) {
      throw new IllegalArgumentException(name + " cannot be null");
    }
  }

  public void validateStringNotBlank(String string, String name) {
    if (string.isBlank()) {
      throw new IllegalArgumentException(name + " cannot be blank");
    }
  }
}

