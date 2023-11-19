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
    this.trainNumber = trainNumber;
    this.line = line;
    this.destination = destination;
    this.departureTime = departureTime;
    this.track = track;
  }

  public TrainDeparture(int trainNumber, String line, String destination,
                        LocalTime departureTime) {
    this(trainNumber, line, destination, departureTime, -1);
  }

  @Override
  public String toString() {
    var sb = new StringBuilder();
    sb.append("Departure time: ").append(departureTime.toString()).append("\n");
    sb.append("Line: ").append(line).append("\n");
    sb.append("Train number: ").append(trainNumber).append("\n");
    sb.append("Destination: ").append(destination).append("\n");
    if (delay != null) {
      sb.append("Delay: ").append(delay.toString()).append("\n");
    }
    if (track != -1) {
      sb.append("Track: ").append(track).append("\n");
    }
    return sb.toString();
  }

  public void addDelay(int hours, int minutes) {
    if (hours <= 0 || minutes <= 0) {
      throw new IllegalArgumentException("Delay cannot be 0 or negative");
    }
    this.delay = this.delay.plusHours(hours);
    this.delay = this.delay.plusMinutes(minutes);
  }

  public void setTrack(int track) {
    if (track <= 0) {
      throw new IllegalArgumentException("Track cannot be 0 or negative");
    }
    this.track = track;
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
}
