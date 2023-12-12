package edu.ntnu.stud.models;

import edu.ntnu.stud.utils.Validation;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * This class represents a train departure. It contains information about
 * the train departure, constructors, getters and relevant setters.
 *
 * @author 10065
 * @version 1.0
 * @since 0.1
 */
public class TrainDeparture {
  private final int trainNumber;
  private final String line;
  private final String destination;
  private final LocalTime departureTime;
  private int delay;
  private int track;

  /**
   * Constructor for train departure, creates a new train departure, after validating the input.
   *
   * @param trainNumber   the train number
   * @param line          the line
   * @param destination   the destination
   * @param departureTime the departure time
   * @param track         the track
   * @throws IllegalArgumentException if train number is zero or less
   * @throws IllegalArgumentException if line is blank
   * @throws IllegalArgumentException if destination is blank
   * @throws IllegalArgumentException if departure time is null
   * @throws IllegalArgumentException if track is zero or less, unless -1
   * @throws IllegalArgumentException if delay is negative
   */
  public TrainDeparture(int trainNumber, String line, String destination,
                        LocalTime departureTime, int delay, int track) {
    Validation.validatePositiveNumber(trainNumber, "Train number");
    Validation.validateStringNotBlank(line, "Line");
    Validation.validateStringNotBlank(destination, "Destination");
    Validation.validateNotNull(departureTime, "Departure time");

    setTrack(track);
    setDelay(delay);

    this.trainNumber = trainNumber;
    this.line = line;
    this.destination = destination;
    this.departureTime = departureTime;
  }

  /**
   * Creates a new train departure. Utilizes the other constructor
   * {@link #TrainDeparture(int, String, String, LocalTime, int, int)},
   * and sets delay to zero and track to -1.
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
  public TrainDeparture(int trainNumber, String line, String destination,
                        LocalTime departureTime) {
    this(trainNumber, line, destination, departureTime, 0, -1);
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
  public int getDelay() {
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
   * Sets the delay after validating parameter.
   *
   * @param delay the delay
   * @throws IllegalArgumentException if delay is negative
   */
  private void setDelay(int delay) {
    Validation.validateZeroOrPositiveNumber(delay, "Delay");
    this.delay = delay;
  }

  /**
   * Sets the track after validating parameter.
   *
   * @param track the track
   * @throws IllegalArgumentException if track is zero or less, unless -1
   */
  public void setTrack(int track) {
    Validation.validatePositiveUnlessNegativeOne(track, "Track");
    this.track = track;
  }


  /**
   * Adds delay to the train departure, utilizes {@link #setDelay(int)}.
   *
   * @param delay the delay
   * @throws IllegalArgumentException if delay is zero or less
   */
  public void addDelay(int delay) {
    Validation.validatePositiveNumber(delay, "Delay");
    setDelay(this.delay + delay);
  }

  /**
   * Gets the departure time with delay.
   *
   * @return the departure time with delay
   */
  public LocalTime departureTimeWithDelay() {
    return departureTime.plusMinutes(delay);
  }

  /**
   * Gets the train info as HashMap.
   *
   * @return the train info
   */
  public HashMap<String, String> trainInfo() {
    HashMap<String, String> trainInfo = new HashMap<>();
    trainInfo.put("departureTime", departureTime.toString());
    trainInfo.put("line", line);
    trainInfo.put("trainNumber", String.valueOf(trainNumber));
    trainInfo.put("destination", destination);
    if (delay > 0) {
      trainInfo.put("delay", delay + " min");
    } else {
      trainInfo.put("delay", "");
    }
    if (track != -1) {
      trainInfo.put("track", String.valueOf(track));
    } else {
      trainInfo.put("track", "");
    }
    return trainInfo;
  }


  /**
   * Returns a string representation of the train departure.
   *
   * @return a string representation of the train departure
   */
  @Override
  public String toString() {
    return  "Departure time: " + departureTime + "\n"
        + "Line: " + line + "\n"
        + "Train number: " + trainNumber + "\n"
        + "Destination: " + destination + "\n"
        + "Delay: " + delay + "\n"
        + "Track: " + track + "\n";
  }
}
