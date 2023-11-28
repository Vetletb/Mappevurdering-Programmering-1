package edu.ntnu.stud;

import java.time.LocalTime;

public class TrainDeparture {
  private final int trainNumber;
  private final String line;
  private final String destination;
  private final LocalTime departureTime;
  private LocalTime delay;
  private int track;

  public TrainDeparture(int trainNumber, String line, String destination,
                        LocalTime departureTime, int track) {
    validateTrainNumber(trainNumber);
    validateLine(line);
    validateDestination(destination);
    validateDepartureTime(departureTime);

    this.trainNumber = trainNumber;
    this.line = line;
    this.destination = destination;
    this.departureTime = departureTime;

    setTrack(track);
    setDelay(LocalTime.of(0, 0));
  }

  public TrainDeparture(int trainNumber, String line, String destination,
                        LocalTime departureTime) {
    this(trainNumber, line, destination, departureTime, -1);
  }

  public int getTrainNumber() {
    return trainNumber;
  }

  public String getLine() {
    return line;
  }

  public String getDestination() {
    return destination;
  }

  public LocalTime getDepartureTime() {
    return departureTime;
  }

  public LocalTime getDelay() {
    return delay;
  }

  public int getTrack() {
    return track;
  }

  private void setDelay(LocalTime delay) {
    if (delay == null) {
      throw new IllegalArgumentException("Delay cannot be null");
    }
    this.delay = delay;
  }

  public void setTrack(int track) {
    if (track < 0) {
      throw new IllegalArgumentException("Track cannot be negative");
    }
    this.track = track;
  }

  @Override
  public String toString() {
    var sb = new StringBuilder();
    sb.append("Departure time: ").append(departureTime).append("\n");
    sb.append("Line: ").append(line).append("\n");
    sb.append("Train number: ").append(trainNumber).append("\n");
    sb.append("Destination: ").append(destination).append("\n");
    if (delay != null) {
      sb.append("Delay: ").append(delay).append("\n");
    }
    if (track != -1) {
      sb.append("Track: ").append(track).append("\n");
    }
    return sb.toString();
  }

  public void addDelay(LocalTime delay) {
    setDelay(this.delay.plusHours(delay.getHour()).plusMinutes(delay.getMinute()));
  }

  private void validateTrainNumber(int trainNumber) {
    if (trainNumber < 0) {
      throw new IllegalArgumentException("Train number cannot be negative");
    }
  }

  private void validateLine(String line) {
    if (line == null) {
      throw new IllegalArgumentException("Line cannot be null");
    }
    if (line.isBlank()) {
      throw new IllegalArgumentException("Line cannot be blank");
    }
  }

  private void validateDestination(String destination) {
    if (destination == null) {
      throw new IllegalArgumentException("Destination cannot be null");
    }
    if (destination.isBlank()) {
      throw new IllegalArgumentException("Destination cannot be blank");
    }
  }

  private void validateDepartureTime(LocalTime departureTime) {
    if (departureTime == null) {
      throw new IllegalArgumentException("Departure time cannot be null");
    }
  }
}
