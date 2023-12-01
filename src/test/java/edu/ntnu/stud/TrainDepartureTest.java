package edu.ntnu.stud;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
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
      void TrainDepartureConstructorThrowsExceptionOnTrainNumberZeroOrLess() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(-1, "line", "destination",
              LocalTime.of(0, 0));
        });
      }

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on line is blank")
      void TrainDepartureConstructorThrowsExceptionOnBlankLine() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "", "destination",
              LocalTime.of(0, 0));
        });
      }

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on destination is blank")
      void TrainDepartureConstructorThrowsExceptionOnBlankDestination() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "line", " ",
              LocalTime.of(0, 0));
        });
      }

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on departure time is null")
      void TrainDepartureConstructorThrowsExceptionOnDepartureTimeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "line", "destination",
              null);
        });
      }

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on track under -1")
      void TrainDepartureConstructorThrowsExceptionOnTrackUnderNegativeOne() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "line", "destination",
              LocalTime.of(0, 0), LocalTime.of(0, 0), -2);
        });
      }

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on track zero")
      void TrainDepartureConstructorThrowsExceptionOnTrackZero() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "line", "destination",
              LocalTime.of(0, 0), LocalTime.of(0, 0), 0);
        });
      }

      @Test
      @DisplayName("Constructor throws IllegalArgumentException on delay is null")
      void TrainDepartureConstructorThrowsExceptionOnDelayNull() {
        assertThrows(IllegalArgumentException.class, () -> {
          var trainDeparture = new TrainDeparture(1, "line", "destination",
              LocalTime.of(0, 0), null, 1);
        });
      }
    }

    @Test
    @DisplayName("addDelay throws IllegalArgumentException on added delay is null")
    void TrainDepartureAddDelayThrowsExceptionOnDelayNull() {
      assertThrows(IllegalArgumentException.class, () -> {
        var trainDeparture = new TrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0));
        trainDeparture.addDelay(null);
      });
    }

    @Test
    @DisplayName("addDelay throws IllegalArgumentException on added delay is zero")
    void TrainDepartureAddDelayThrowsExceptionOnDelayZero() {
      assertThrows(IllegalArgumentException.class, () -> {
        var trainDeparture = new TrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0));
        trainDeparture.addDelay(LocalTime.of(0, 0));
      });
    }
  }

  @Nested
  @DisplayName("Positive tests for TrainDeparture, does not throw exceptions on valid input")
  public class MethodsDoesNotThrowExceptions {

    @Test
    @DisplayName("Constructor does not throw IllegalArgumentException on valid parameters")
    void TrainDepartureConstructorDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> {
        new TrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0), LocalTime.of(0, 0), 1);
      });
    }

    @Test
    @DisplayName("Constructor does not throw IllegalArgumentException on track -1")
    void TrainDepartureConstructorDoesNotThrowExceptionOnTrackNegativeOne() {
      assertDoesNotThrow(() -> {
        new TrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0), LocalTime.of(0, 0), -1);
      });
    }

    @Test
    @DisplayName("addDelay does not throw IllegalArgumentException on valid parameters")
    void TrainDepartureAddDelayDoesNotThrowExceptionOnValidParameters() {
      assertDoesNotThrow(() -> {
        var trainDeparture = new TrainDeparture(1, "line", "destination",
            LocalTime.of(0, 0));
        trainDeparture.addDelay(LocalTime.of(1, 1));
      });
    }

    @Test
    @DisplayName("departureTimeWithDelay returns correct time")
    void TrainDepartureDepartureTimeWithDelayReturnsCorrectTime() {
      var trainDeparture = new TrainDeparture(1, "line", "destination",
          LocalTime.of(0, 0));
      trainDeparture.addDelay(LocalTime.of(1, 1));
      assertEquals(LocalTime.of(1, 1), trainDeparture.departureTimeWithDelay());
    }

    @Test
    @DisplayName("toString returns correct string with track and delay")
    void TrainDepartureToStringReturnsCorrectStringWithAllFieldsSet() {
      var trainDeparture = new TrainDeparture(1, "line", "destination",
          LocalTime.of(0, 0), LocalTime.of(1, 1), 1);
      assertEquals("Departure time: 00:00\nLine: line\nTrain number: 1\nDestination:"
          + " destination\nDelay: 01:01\nTrack: 1\n", trainDeparture.toString());
    }

    @Test
    @DisplayName("toString returns correct string without track and delay")
    void TrainDepartureToStringReturnsCorrectStringWithoutOptionalFields() {
      var trainDeparture = new TrainDeparture(1, "line", "destination",
          LocalTime.of(0, 0));
      assertEquals("Departure time: 00:00\nLine: line\nTrain number: 1\nDestination:"
          + " destination\n", trainDeparture.toString());
    }
  }
}