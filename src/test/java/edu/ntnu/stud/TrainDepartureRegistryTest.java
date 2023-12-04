package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TrainDepartureRegistryTest {

  @Nested
  @DisplayName("Negative tests for TrainDepartureRegistry, throws exceptions on invalid input")
  public class MethodsThrowsExceptions {

    @Test
    @DisplayName("getTrainDeparture throws IllegalArgumentException on non-existing train number")
    void getTrainDepartureThrowsExceptionOnNonExistingTrainNumber() {
      assertThrows(IllegalArgumentException.class, () -> {
        new TrainDepartureRegistry().getTrainDeparture(1);
      });
    }

    @Test
    @DisplayName("newTrainDeparture throws IllegalArgumentException on train already exists")
    void newTrainDepartureThrowsExceptionOnTrainNumberAlreadyInRegistry() {
      assertThrows(IllegalArgumentException.class, () -> {
        var trainDepartureRegistry = new TrainDepartureRegistry();
        trainDepartureRegistry.newTrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0));
        trainDepartureRegistry.newTrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0));
      });
    }

    @Test
    @DisplayName("deleteTrainDeparturesBeforeTime throws IllegalArgumentException on time is null")
    void deleteTrainDeparturesBeforeTimeThrowsExceptionOnTimeNull() {
      assertThrows(IllegalArgumentException.class, () -> {
        new TrainDepartureRegistry().removeTrainDeparturesBeforeTime(null);
      });
    }
  }

  @Nested
  @DisplayName("Positive tests for TrainDepartureRegistry, "
      + "does not throw exceptions on valid input")
  public class MethodsDoesNotThrowExceptions {

    @Test
    @DisplayName("getTrainDeparture does not throw IllegalArgumentException "
        + "on existing train number")
    void getTrainDepartureDoesNotThrowExceptionOnTrainExistingTrainNumber() {
      assertDoesNotThrow(() -> {
        var trainDepartureRegistry = new TrainDepartureRegistry();
        trainDepartureRegistry.newTrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0));
        trainDepartureRegistry.getTrainDeparture(1);
      });
    }

    @Test
    @DisplayName("newTrainDeparture does not throw IllegalArgumentException on valid input")
    void newTrainDepartureDoesNotThrowExceptionOnValidInput() {
      assertDoesNotThrow(() -> {
        new TrainDepartureRegistry().newTrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0));
      });
    }

    @Test
    @DisplayName("getTrainDepartureByDestination returns a new TrainDepartureRegistry "
        + "filtered by destination")
    void getTrainDepartureByDestinationReturnsNewCorrectlyFilteredTrainDepartureRegistry() {
      var trainDepartureRegistry = new TrainDepartureRegistry();
      trainDepartureRegistry.newTrainDeparture(1, "line", "destination",
          LocalTime.of(0, 0));
      trainDepartureRegistry.newTrainDeparture(2, "line2", "destination2",
          LocalTime.of(0, 0));
      trainDepartureRegistry.newTrainDeparture(3, "line3", "destination",
          LocalTime.of(0, 0));
      var expectedRegistry = new TrainDepartureRegistry();
      expectedRegistry.newTrainDeparture(1, "line", "destination",
          LocalTime.of(0, 0));
      expectedRegistry.newTrainDeparture(3, "line3", "destination",
          LocalTime.of(0, 0));
      assertEquals(expectedRegistry.toString(),
          trainDepartureRegistry.getTrainDeparturesByDestination("destination").toString());
    }

    @Test
    @DisplayName("getTrainDepartureByDestination throws IllegalArgumentException "
        + "on destination is blank")
    void getTrainDepartureByDestinationThrowsExceptionOnDestinationBlank() {
      assertThrows(IllegalArgumentException.class, () -> {
        new TrainDepartureRegistry().getTrainDeparturesByDestination(" ");
      });
    }

    @Test
    @DisplayName("removeTrainDeparturesBeforeTime removes all train departures before time")
    void removeTrainDeparturesBeforeTimeRemovesAllTrainDeparturesBeforeTime() {
      var trainDepartureRegistry = new TrainDepartureRegistry();
      trainDepartureRegistry.newTrainDeparture(1, "line", "destination",
          LocalTime.of(0, 0));
      trainDepartureRegistry.newTrainDeparture(2, "line2", "destination2",
          LocalTime.of(0, 1));
      trainDepartureRegistry.newTrainDeparture(3, "line3", "destination3",
          LocalTime.of(0, 3));
      var expectedRegistry = new TrainDepartureRegistry();
      expectedRegistry.newTrainDeparture(2, "line2", "destination2",
          LocalTime.of(0, 1));
      expectedRegistry.newTrainDeparture(3, "line3", "destination3",
          LocalTime.of(0, 3));
      trainDepartureRegistry.removeTrainDeparturesBeforeTime(LocalTime.of(0, 1));
      assertEquals(expectedRegistry.toString(), trainDepartureRegistry.toString());
    }

    @Test
    @DisplayName("sortedByDepartureTime returns a list of train numbers sorted by departure time")
    void sortedByDepartureTimeReturnsListOfTrainNumbersSortedByDepartureTime() {
      var trainDepartureRegistry = new TrainDepartureRegistry();
      trainDepartureRegistry.newTrainDeparture(1, "line", "destination",
          LocalTime.of(0, 4));
      trainDepartureRegistry.newTrainDeparture(2, "line2", "destination2",
          LocalTime.of(0, 1));
      trainDepartureRegistry.newTrainDeparture(3, "line3", "destination3",
          LocalTime.of(0, 2));
      List<Integer> expectedList = new ArrayList<>();
      expectedList.add(2);
      expectedList.add(3);
      expectedList.add(1);
      assertEquals(expectedList, trainDepartureRegistry.sortedByDepartureTime());
    }

    @Test
    @DisplayName("toString returns correct string representation of the train departure registry")
    void toStringReturnsCorrectStringRepresentationOfTheTrainDepartureRegistry() {
      var trainDepartureRegistry = new TrainDepartureRegistry();
      trainDepartureRegistry.newTrainDeparture(1, "line", "destination",
          LocalTime.of(0, 4));
      trainDepartureRegistry.newTrainDeparture(2, "line2", "destination2",
          LocalTime.of(0, 1));
      trainDepartureRegistry.newTrainDeparture(3, "line3", "destination3",
          LocalTime.of(0, 2));
      var expectedString = "Departure time: 00:04\n"
          + "Line: line\n"
          + "Train number: 1\n"
          + "Destination: destination\n"
          + "\n"
          + "Departure time: 00:01\n"
          + "Line: line2\n"
          + "Train number: 2\n"
          + "Destination: destination2\n"
          + "\n"
          + "Departure time: 00:02\n"
          + "Line: line3\n"
          + "Train number: 3\n"
          + "Destination: destination3\n";
      assertEquals(expectedString, trainDepartureRegistry.toString());
    }
  }
}
