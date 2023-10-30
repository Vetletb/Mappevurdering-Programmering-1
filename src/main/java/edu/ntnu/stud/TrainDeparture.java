package edu.ntnu.stud;

import java.time.LocalTime;

public class TrainDeparture {
  private final String trainNumber;
  private final String line;
  private final String destination;
  private final LocalTime departureTime;
  private LocalTime delay;
  private String track;

  public TrainDeparture(final String trainNumber, final String line, final String destination,
                        final LocalTime departureTime, final LocalTime delay, final String track) {
    this.trainNumber = trainNumber;
    this.line = line;
    this.destination = destination;
    this.departureTime = departureTime;
    this.delay = delay;
    this.track = track;
  }

  @Override
  public String toString() {
    return departureTime + " " + line + " " + trainNumber + " " + destination + " " + delay + " " + track;
  }

  public void addDelay(final int hours, final int minutes) {
      this.delay = this.delay.plusHours(hours);
      this.delay = this.delay.plusMinutes(minutes);
  }

  public void setTrack(String track) {
      this.track = track;
  }

  public String getTrainNumber() {
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

  public String getTrack() {
    return track;
  }
}
