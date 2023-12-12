package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TrainDepartureRegistryTest {

  @Nested
  @DisplayName("Negative tests for TrainDepartureRegistry, throws exceptions on invalid input")
  public class MethodsThrowsExceptions {
    private TrainDepartureRegistry trainDepartureRegistry;

    @BeforeEach
    void setUp() {
      trainDepartureRegistry = new TrainDepartureRegistry();
      trainDepartureRegistry.newTrainDeparture(1, "line", "destination",
          LocalTime.of(0, 0));
      trainDepartureRegistry.newTrainDeparture(2, "line2", "destination2",
          LocalTime.of(0, 1));
      trainDepartureRegistry.newTrainDeparture(3, "line3", "destination",
          LocalTime.of(1, 0));
    }

    @Test
    @DisplayName("newTrainDeparture throws IllegalArgumentException on train already exists")
    void newTrainDepartureThrowsExceptionOnTrainNumberAlreadyInRegistry() {
      assertThrows(IllegalArgumentException.class, () -> {
        trainDepartureRegistry.newTrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0));
      });
    }

    @Test
    @DisplayName("addDelay throws IllegalArgumentException on train number not in registry")
    void addDelayThrowsExceptionOnTrainNumberNotInRegistry() {
      assertThrows(IllegalArgumentException.class, () -> {
        trainDepartureRegistry.addDelay(4, 1);
      });
    }

    @Test
    @DisplayName("setTrack throws IllegalArgumentException on train number not in registry")
    void setTrackThrowsExceptionOnTrainNumberNotInRegistry() {
      assertThrows(IllegalArgumentException.class, () -> {
        trainDepartureRegistry.setTrack(4, 1);
      });
    }

    @Test
    @DisplayName("validateTrainNumberExistence throws IllegalArgumentException on "
        + "train number not in registry")
    void validateTrainNumberExistenceThrowsExceptionOnTrainNumberNotInRegistry() {
      assertThrows(IllegalArgumentException.class, () -> {
        trainDepartureRegistry.validateTrainNumberExistence(4);
      });
    }

    @Test
    @DisplayName("removeTrainDeparture throws IllegalArgumentException on "
        + "train number not in registry")
    void removeTrainDepartureThrowsExceptionOnTrainNumberNotInRegistry() {
      assertThrows(IllegalArgumentException.class, () -> {
        trainDepartureRegistry.removeTrainDeparture(4);
      });
    }

    @Test
    @DisplayName("removeTrainDeparturesBeforeTime throws IllegalArgumentException on time is null")
    void removeTrainDeparturesBeforeTimeThrowsExceptionOnTimeNull() {
      assertThrows(IllegalArgumentException.class, () -> {
        trainDepartureRegistry.removeTrainDeparturesBeforeTime(null);
      });
    }

    @Test
    @DisplayName("trainDepartureByDestination throws IllegalArgumentException "
        + "on destination is blank")
    void trainDepartureByDestinationThrowsExceptionOnDestinationBlank() {
      assertThrows(IllegalArgumentException.class, () -> {
        new TrainDepartureRegistry().trainDeparturesByDestination(" ");
      });
    }

    @Test
    @DisplayName("trainDepartureString throws IllegalArgumentException "
        + "on train number not in registry")
    void trainDepartureStringThrowsExceptionOnTrainNumberNotInRegistry() {
      assertThrows(IllegalArgumentException.class, () -> {
        trainDepartureRegistry.trainDepartureString(4);
      });
    }

    @Test
    @DisplayName("getAllFromTrainNumber throws IllegalArgumentException "
        + "on train number not in registry")
    void getAllFromTrainNumberThrowsExceptionOnTrainNumberNotInRegistry() {
      assertThrows(IllegalArgumentException.class, () -> {
        trainDepartureRegistry.getAllFromTrainNumber(4);
      });
    }
  }

  @Nested
  @DisplayName("Positive tests for TrainDepartureRegistry"
      + "does not throw exceptions on valid input")
  public class MethodsDoesNotThrowExceptions {
    private TrainDepartureRegistry trainDepartureRegistry;

    @BeforeEach
    void setUp() {
      trainDepartureRegistry = new TrainDepartureRegistry();
      trainDepartureRegistry.newTrainDeparture(1, "line", "destination",
          LocalTime.of(0, 0));
      trainDepartureRegistry.newTrainDeparture(2, "line2", "destination2",
          LocalTime.of(0, 1));
      trainDepartureRegistry.newTrainDeparture(3, "line3", "destination",
          LocalTime.of(1, 0));
    }

    @Test
    @DisplayName("newTrainDeparture does not throw IllegalArgumentException on valid input")
    void newTrainDepartureDoesNotThrowExceptionOnValidInput() {
      assertDoesNotThrow(() -> {
        trainDepartureRegistry.newTrainDeparture(4, "line", "destination",
            LocalTime.of(0, 0));
      });
    }

    @Test
    @DisplayName("addDelay does not throw IllegalArgumentException on valid input")
    void addDelayDoesNotThrowExceptionOnValidInput() {
      assertDoesNotThrow(() -> {
        trainDepartureRegistry.addDelay(1, 1);
      });
    }

    @Test
    @DisplayName("setTrack does not throw IllegalArgumentException on valid input")
    void setTrackDoesNotThrowExceptionOnValidInput() {
      assertDoesNotThrow(() -> {
        trainDepartureRegistry.setTrack(1, 1);
      });
    }

    @Test
    @DisplayName("containsTrainDeparture returns true on train number in registry")
    void containsTrainDepartureReturnsTrueOnTrainNumberInRegistry() {
      var trainDeparture = new TrainDeparture(1, "line", "destination",
          LocalTime.of(0, 0));
      assertTrue(trainDepartureRegistry.containsTrainDeparture(trainDeparture));
    }

    @Test
    @DisplayName("containsTrainDeparture returns false on train number not in registry")
    void containsTrainDepartureReturnsFalseOnTrainNumberNotInRegistry() {
      var trainDeparture = new TrainDeparture(4, "line", "destination",
          LocalTime.of(0, 0));
      assertFalse(trainDepartureRegistry.containsTrainDeparture(trainDeparture));
    }

    @Test
    @DisplayName("containsTrainNumber returns true on train number in registry")
    void containsTrainNumberReturnsTrueOnTrainNumberInRegistry() {
      assertTrue(trainDepartureRegistry.containsTrainNumber(1));
    }

    @Test
    @DisplayName("containsTrainNumber returns false on train number not in registry")
    void containsTrainNumberReturnsFalseOnTrainNumberNotInRegistry() {
      assertFalse(trainDepartureRegistry.containsTrainNumber(4));
    }

    @Test
    @DisplayName("validateTrainNumberExistence does not throw IllegalArgumentException "
        + "on train number in registry")
    void validateTrainNumberExistenceDoesNotThrowExceptionOnTrainNumberInRegistry() {
      assertDoesNotThrow(() -> {
        trainDepartureRegistry.validateTrainNumberExistence(1);
      });
    }

    @Test
    @DisplayName("removeTrainDeparture removes train departure from registry")
    void removeTrainDepartureRemovesTrainDepartureFromRegistry() {
      var expectedRegistry = new TrainDepartureRegistry();
      expectedRegistry.newTrainDeparture(2, "line2", "destination2",
          LocalTime.of(0, 1));
      expectedRegistry.newTrainDeparture(3, "line3", "destination",
          LocalTime.of(1, 0));
      trainDepartureRegistry.removeTrainDeparture(1);
      assertEquals(expectedRegistry.toString(), trainDepartureRegistry.toString());
    }

    @Test
    @DisplayName("removeTrainDeparturesBeforeTime removes all train departures before time")
    void removeTrainDeparturesBeforeTimeRemovesAllTrainDeparturesBeforeTime() {
      var expectedRegistry = new TrainDepartureRegistry();
      expectedRegistry.newTrainDeparture(2, "line2", "destination2",
          LocalTime.of(0, 1));
      expectedRegistry.newTrainDeparture(3, "line3", "destination",
          LocalTime.of(1, 0));
      trainDepartureRegistry.removeTrainDeparturesBeforeTime(LocalTime.of(0, 1));
      assertEquals(expectedRegistry.toString(), trainDepartureRegistry.toString());
    }

    @Test
    @DisplayName("getTrainDepartureByDestination returns a new TrainDepartureRegistry "
        + "filtered by destination")
    void getTrainDepartureByDestinationReturnsNewCorrectlyFilteredTrainDepartureRegistry() {
      var expectedRegistry = new TrainDepartureRegistry();
      expectedRegistry.newTrainDeparture(1, "line", "destination",
          LocalTime.of(0, 0));
      expectedRegistry.newTrainDeparture(3, "line3", "destination",
          LocalTime.of(1, 0));
      assertEquals(expectedRegistry.toString(),
          trainDepartureRegistry.trainDeparturesByDestination("destination").toString());
    }


    @Test
    @DisplayName("sortedByDepartureTime returns correct list of train numbers "
        + "sorted by departure time")
    void sortedByDepartureTimeReturnsCorrectListOfTrainNumbersSortedByDepartureTime() {
      List<Integer> expectedList = new ArrayList<>();
      expectedList.add(1);
      expectedList.add(2);
      expectedList.add(3);
      assertEquals(expectedList, trainDepartureRegistry.sortedByDepartureTime());
    }

    @Test
    @DisplayName("trainDepartureString returns correct string representation of "
        + "the train departure")
    void trainDepartureStringReturnsCorrectStringRepresentationOfTheTrainDeparture() {
      var expectedString = """
          Departure time: 00:00
          Line: line
          Train number: 1
          Destination: destination
          Delay: 0
          Track: -1
          """;
      assertEquals(expectedString, trainDepartureRegistry.trainDepartureString(1));
    }

    @Test
    @DisplayName("getAllFromTrainNumber returns correct HashMap of train departure info")
    void getAllFromTrainNumberReturnsCorrectHashMapOfTrainDepartureInfo() {
      var expectedHashMap = new HashMap<String, String>();
      expectedHashMap.put("departureTime", "00:00");
      expectedHashMap.put("line", "line");
      expectedHashMap.put("trainNumber", "1");
      expectedHashMap.put("destination", "destination");
      expectedHashMap.put("delay", "");
      expectedHashMap.put("track", "");
      assertEquals(expectedHashMap, trainDepartureRegistry.getAllFromTrainNumber(1));
    }

    @Test
    @DisplayName("toString returns correct string representation of the train departure registry")
    void toStringReturnsCorrectStringRepresentationOfTheTrainDepartureRegistry() {
      var expectedString = """
          Departure time: 00:00
          Line: line
          Train number: 1
          Destination: destination
          Delay: 0
          Track: -1

          Departure time: 00:01
          Line: line2
          Train number: 2
          Destination: destination2
          Delay: 0
          Track: -1

          Departure time: 01:00
          Line: line3
          Train number: 3
          Destination: destination
          Delay: 0
          Track: -1
          """;
      assertEquals(expectedString, trainDepartureRegistry.toString());
    }
  }
}
