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
    return trainDepartures.get(trainNumber);
  }

  /**
   * Adds a train departure.
   *
   * @param trainNumber   the train number
   * @param line          the line
   * @param destination   the destination
   * @param departureTime the departure time
   */
  public void newTrainDeparture(int trainNumber, String line, String destination,
                                LocalTime departureTime) {
    var trainDeparture = new TrainDeparture(trainNumber, line, destination, departureTime);;
    addTrainDeparture(trainDeparture);
  }

  /**
   * Adds a train departure.
   *
   * @param trainDeparture the train departure
   */
  private void addTrainDeparture(TrainDeparture trainDeparture) {
    if (trainDepartures.containsKey(trainDeparture.getTrainNumber())) {
      throw new IllegalArgumentException("Train number already exists");
    }
    trainDepartures.put(trainDeparture.getTrainNumber(), trainDeparture);
  }

  /**
   * Creates train departure registry filtered from destination.
   *
   * @param destination the destination
   */
  public TrainDepartureRegistry getTrainDepartureByDestination(String destination) {
    var result = new TrainDepartureRegistry();
    trainDepartures.values().stream()
        .filter(trainDeparture -> trainDeparture.getDestination().equals(destination))
        .forEach(trainDeparture -> result.addTrainDeparture(trainDeparture));
    return result;
  }

  /**
   * Deletes a train departure.
   *
   * @param trainNumber the train number
   */
  public void deleteTrainDeparture(int trainNumber) {
    trainDepartures.remove(trainNumber);
  }

  /**
   * Deletes train departures before time.
   *
   * @param time the time
   */
  public void deleteTrainDeparturesBeforeTime(LocalTime time) {
    List<Integer> trainsToRemove = trainDepartures.values().stream()
        .filter(trainDeparture -> trainDeparture.departureTimeWithDelay().isBefore(time))
        .map(TrainDeparture::getTrainNumber)
        .toList();
    trainsToRemove.forEach(this::deleteTrainDeparture);
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
}
