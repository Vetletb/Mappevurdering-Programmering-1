package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TrainDepartureRegistry {

  private final HashMap<Integer, TrainDeparture> trainDepartures = new HashMap<>();

  public TrainDeparture getTrainDeparture(int trainNumber) {
    return trainDepartures.get(trainNumber);
  }

  public void addTrainDeparture(int trainNumber, String line, String destination,
                                LocalTime departureTime) {
    var trainDeparture = new TrainDeparture(trainNumber, line, destination, departureTime);;
    if (trainDepartures.containsKey(trainNumber)) {
      throw new IllegalArgumentException("Train number already exists");
    }
    trainDepartures.put(trainNumber, trainDeparture);
  }

  public TrainDepartureRegistry getTrainDepartureByDestination(String destination) {
    var result = new TrainDepartureRegistry();
    trainDepartures.values().stream()
        .filter(trainDeparture -> trainDeparture.getDestination().equals(destination))
        .forEach(trainDeparture -> result.addTrainDeparture(
            trainDeparture.getTrainNumber(),
            trainDeparture.getLine(),
            trainDeparture.getDestination(),
            trainDeparture.getDepartureTime()));
    return result;
  }

  public void deleteTrainDeparture(int trainNumber) {
    trainDepartures.remove(trainNumber);
  }

  public void deleteTrainDeparturesBeforeTime(LocalTime time) {
    List<Integer> trainsToRemove = trainDepartures.values().stream()
        .filter(trainDeparture -> trainDeparture.departureTimeWithDelay().isBefore(time))
        .map(TrainDeparture::getTrainNumber)
        .toList();
    trainsToRemove.forEach(this::deleteTrainDeparture);
  }

  @Override
  public String toString() {
    return trainDepartures.values().stream()
        .map(TrainDeparture::toString)
        .collect(Collectors.joining("\n"));
  }
}
