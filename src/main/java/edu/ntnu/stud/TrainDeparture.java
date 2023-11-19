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
    return departureTime + " " + line + " " + trainNumber + " " + destination + " " + delay + " " + track;
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
