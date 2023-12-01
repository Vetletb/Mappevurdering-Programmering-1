package edu.ntnu.stud;

import java.time.LocalTime;

/**
 * This class represents a train departure.
 */
public class TrainDeparture {
  private final int trainNumber;
  private final String line;
  private final String destination;
  private final LocalTime departureTime;
  private LocalTime delay;
  private int track;

  /**
   * Creates a new train departure.
   *
   * @param trainNumber   the train number
   * @param line          the line
   * @param destination   the destination
   * @param departureTime the departure time
   * @param track         the track
   */
  public TrainDeparture(int trainNumber, String line, String destination,
                        LocalTime departureTime, LocalTime delay, int track) {
    validatePositiveNumber(trainNumber, "Train number");
    validateStringNotBlank(line, "Line");
    validateStringNotBlank(destination, "Destination");
    validateNotNull(departureTime, "Departure time");

    setTrack(track);
    setDelay(delay);

    this.trainNumber = trainNumber;
    this.line = line;
    this.destination = destination;
    this.departureTime = departureTime;
  }

  public TrainDeparture(int trainNumber, String line, String destination,
                        LocalTime departureTime) {
    this(trainNumber, line, destination, departureTime, LocalTime.of(0, 0), -1);
  }

  /**
   * Gets the train number.
   *
   * @return the train number
   */
  public int getTrainNumber() {
    return trainNumber;
  }

  /**
   * Gets the line.
   *
   * @return the line
   */
  public String getLine() {
    return line;
  }

  /**
   * Gets the destination.
   *
   * @return the destination
   */
  public String getDestination() {
    return destination;
  }

  /**
   * Gets the departure time.
   *
   * @return the departure time
   */
  public LocalTime getDepartureTime() {
    return departureTime;
  }

  /**
   * Gets the delay.
   *
   * @return the delay
   */
  public LocalTime getDelay() {
    return delay;
  }

  /**
   * Gets the track.
   *
   * @return the track
   */
  public int getTrack() {
    return track;
  }

  /**
   * Sets the delay.
   *
   * @param delay the delay
   */
  private void setDelay(LocalTime delay) {
    validateNotNull(delay, "Delay");
    this.delay = delay;
  }

  /**
   * Sets the track.
   *
   * @param track the track
   */
  public void setTrack(int track) {
    validateTrack(track);
    this.track = track;
  }

  /**
   * Returns a string representation of the train departure.
   *
   * @return a string representation of the train departure
   */
  @Override
  public String toString() {
    var sb = new StringBuilder();
    sb.append("Departure time: ").append(departureTime).append("\n");
    sb.append("Line: ").append(line).append("\n");
    sb.append("Train number: ").append(trainNumber).append("\n");
    sb.append("Destination: ").append(destination).append("\n");
    if (delay != null && delay != LocalTime.of(0, 0)) {
      sb.append("Delay: ").append(delay).append("\n");
    }
    if (track != -1) {
      sb.append("Track: ").append(track).append("\n");
    }
    return sb.toString();
  }

  private void validateTrack(int track) {
    if (track <= 0 && track != -1) {
      throw new IllegalArgumentException("Track cannot be zero or less, unless -1");
    }
  }

  private void validateStringNotBlank(String string, String name) {
    if (string.isBlank()) {
      throw new IllegalArgumentException(name + " cannot be blank");
    }
  }

  private void validatePositiveNumber(int number, String name) {
    if (number < 0) {
      throw new IllegalArgumentException(name + " cannot be negative");
    }
  }

  private void validateNotNull(Object object, String name) {
    if (object == null) {
      throw new IllegalArgumentException(name + " cannot be null");
    }
  }

  /**
   * Adds a delay to the train departure.
   *
   * @param delay the delay
   */
  public void addDelay(LocalTime delay) {
    validateNotNull(delay, "Delay");
    if (delay.equals(LocalTime.of(0, 0))) {
      throw new IllegalArgumentException("Added delay cannot be zero");
    }
    setDelay(this.delay.plusHours(delay.getHour()).plusMinutes(delay.getMinute()));
  }

  public LocalTime departureTimeWithDelay() {
    return departureTime.plusHours(delay.getHour()).plusMinutes(delay.getMinute());
  }
}
