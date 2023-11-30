package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TrainDepartureRegistry {

  private final HashMap<Integer, TrainDeparture> trainDepartures = new HashMap<>();

  public TrainDeparture getTrainDeparture(int trainNumber) {
    return trainDepartures.get(trainNumber);
  }

  public void addTrainDeparture(int trainNumber, String line, String destination,
                                LocalTime departureTime, int track) {
    var trainDeparture = new TrainDeparture(trainNumber, line, destination, departureTime, track);;
    if (trainDepartures.containsKey(trainNumber)) {
      throw new IllegalArgumentException("Train number already exists");
    }
    trainDepartures.put(trainNumber, trainDeparture);
  }
}
