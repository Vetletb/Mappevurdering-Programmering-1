package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalTime;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TrainDepartureTest {

  @Nested
  @DisplayName("Negative tests for TrainDeparture, throws exceptions on invalid input")
  public class MethodsThrowsExceptions {

    @Nested
    @DisplayName("Negative tests for constructor, throws exceptions on invalid input")
    public class ConstructorThrowsExceptions {

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on train number zero or less")
      void trainDepartureConstructorThrowsExceptionOnTrainNumberZeroOrLess() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(0, "line", "destination",
              LocalTime.of(0, 0));
        });
      }

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on line is blank")
      void trainDepartureConstructorThrowsExceptionOnBlankLine() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "", "destination",
              LocalTime.of(0, 0));
        });
      }

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on destination is blank")
      void trainDepartureConstructorThrowsExceptionOnBlankDestination() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "line", " ",
              LocalTime.of(0, 0));
        });
      }

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on departure time is null")
      void trainDepartureConstructorThrowsExceptionOnDepartureTimeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "line", "destination",
              null);
        });
      }

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on track under -1")
      void trainDepartureConstructorThrowsExceptionOnTrackUnderNegativeOne() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "line", "destination",
              LocalTime.of(0, 0), 0, -2);
        });
      }

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on track zero")
      void trainDepartureConstructorThrowsExceptionOnTrackZero() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "line", "destination",
              LocalTime.of(0, 0), 0, 0);
        });
      }
    }


    @Nested
    @DisplayName("Negative tests for other methods, throws exceptions on invalid input")
    public class OtherMethodsThrowsExceptions {
      private TrainDeparture trainDeparture;

      @BeforeEach
      void setUp() {
        trainDeparture = new TrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0));
      }

      @Test
      @DisplayName("setTrack throws IllegalArgumentException on track under -1")
      void trainDepartureSetTrackThrowsExceptionOnTrackUnderNegativeOne() {
        assertThrows(IllegalArgumentException.class, () -> {
          trainDeparture.setTrack(-2);
        });
      }

      @Test
      @DisplayName("setTrack throws IllegalArgumentException on track zero")
      void trainDepartureSetTrackThrowsExceptionOnTrackZero() {
        assertThrows(IllegalArgumentException.class, () -> {
          trainDeparture.setTrack(0);
        });
      }

      @Test
      @DisplayName("addDelay throws IllegalArgumentException on added delay is zero or less")
      void trainDepartureAddDelayThrowsExceptionOnDelayZeroOrLess() {
        assertThrows(IllegalArgumentException.class, () -> {
          trainDeparture.addDelay(0);
        });
      }
    }
  }

  @Nested
  @DisplayName("Positive tests for TrainDeparture, does not throw exceptions on valid input")
  public class MethodsDoesNotThrowExceptions {
    private TrainDeparture trainDeparture;

    @BeforeEach
    void setUp() {
      trainDeparture = new TrainDeparture(1, "line", "destination",
          LocalTime.of(0, 0));
    }

    @Test
    @DisplayName("Constructor does not throw IllegalArgumentException on valid parameters")
    void trainDepartureConstructorDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> {
        new TrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0), 0, 1);
      });
    }

    @Test
    @DisplayName("Constructor does not throw IllegalArgumentException on track -1")
    void trainDepartureConstructorDoesNotThrowExceptionOnTrackNegativeOne() {
      assertDoesNotThrow(() -> {
        new TrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0), 0, -1);
      });
    }

    @Test
    @DisplayName("getTrainNumber returns correct train number")
    void trainDepartureGetTrainNumberReturnsCorrectTrainNumber() {
      assertEquals(1, trainDeparture.getTrainNumber());
    }

    @Test
    @DisplayName("getLine returns correct line")
    void trainDepartureGetLineReturnsCorrectLine() {
      assertEquals("line", trainDeparture.getLine());
    }

    @Test
    @DisplayName("getDestination returns correct destination")
    void trainDepartureGetDestinationReturnsCorrectDestination() {
      assertEquals("destination", trainDeparture.getDestination());
    }

    @Test
    @DisplayName("getDepartureTime returns correct departure time")
    void trainDepartureGetDepartureTimeReturnsCorrectDepartureTime() {
      assertEquals(LocalTime.of(0, 0), trainDeparture.getDepartureTime());
    }

    @Test
    @DisplayName("getDelay returns correct delay")
    void trainDepartureGetDelayReturnsCorrectDelay() {
      assertEquals(0, trainDeparture.getDelay());
    }

    @Test
    @DisplayName("getTrack returns correct track")
    void trainDepartureGetTrackReturnsCorrectTrack() {
      assertEquals(-1, trainDeparture.getTrack());
    }

    @Test
    @DisplayName("setTrack does not throw IllegalArgumentException on valid parameters")
    void trainDepartureSetTrackDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> {
        trainDeparture.setTrack(1);
      });
    }

    @Test
    @DisplayName("addDelay does not throw IllegalArgumentException on valid parameters")
    void trainDepartureAddDelayDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> {
        trainDeparture.addDelay(1);
      });
    }

    @Test
    @DisplayName("departureTimeWithDelay returns correct time")
    void trainDepartureDepartureTimeWithDelayReturnsCorrectTime() {
      trainDeparture.addDelay(1);
      assertEquals(LocalTime.of(0, 1), trainDeparture.departureTimeWithDelay());
    }

    @Nested
    @DisplayName("trainInfo returns correct HashMap")
    public class TrainInfoReturnsCorrectHashMap {
      private HashMap<String, String> expectedHashMap;

      @BeforeEach
      void setUp() {
        expectedHashMap = new HashMap<String, String>();
        expectedHashMap.put("trainNumber", "1");
        expectedHashMap.put("line", "line");
        expectedHashMap.put("destination", "destination");
        expectedHashMap.put("departureTime", "00:00");
        expectedHashMap.put("delay", "");
        expectedHashMap.put("track", "");
      }

      @Test
      @DisplayName("trainInfo returns correct HashMap without delay and track")
      void trainDepartureTrainInfoReturnsCorrectHashMap() {
        assertEquals(expectedHashMap, trainDeparture.trainInfo());
      }

      @Test
      @DisplayName("trainInfo returns correct HashMap with delay and track")
      void trainDepartureTrainInfoReturnsCorrectHashMapWithDelayAndTrack() {
        trainDeparture.addDelay(1);
        trainDeparture.setTrack(1);
        expectedHashMap.put("delay", "1 min");
        expectedHashMap.put("track", "1");
        assertEquals(expectedHashMap, trainDeparture.trainInfo());
      }
    }

    @Test
    @DisplayName("toString returns correct string")
    void trainDepartureToStringReturnsCorrectString() {
      assertEquals("""
          Departure time: 00:00
          Line: line
          Train number: 1
          Destination: destination
          Delay: 0
          Track: -1
          """, trainDeparture.toString());
    }
  }
}