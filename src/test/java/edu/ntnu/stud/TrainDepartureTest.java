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
      @DisplayName("Throws IllegalArgumentException on invalid train number")
      void TrainDepartureConstructorThrowsExceptionOnTrainNumberZeroOrLess() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(-1, "line", "destination", LocalTime.of(0, 0));
        });
      }

      @Test
      @DisplayName("Throws IllegalArgumentException on invalid line")
      void TrainDepartureConstructorThrowsExceptionOnBlankLine() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "", "destination", LocalTime.of(0, 0));
        });
      }

      @Test
      @DisplayName("Throws IllegalArgumentException on invalid destination")
      void TrainDepartureConstructorThrowsExceptionOnBlankDestination() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "line", " ", LocalTime.of(0, 0));
        });
      }

      @Test
      @DisplayName("Throws IllegalArgumentException on invalid departure time")
      void TrainDepartureConstructorThrowsExceptionOnDepartureTimeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "line", "destination", null);
        });
      }

      @Test
      @DisplayName("Throws IllegalArgumentException on invalid track")
      void TrainDepartureConstructorThrowsExceptionOnTrackNegativeOneOrLess() {
        assertThrows(IllegalArgumentException.class, () -> {
          new TrainDeparture(1, "line", "destination", LocalTime.of(0, 0), -2);
        });
      }
    }

    @Test
    @DisplayName("Throws IllegalArgumentException on invalid delay")
    void TrainDepartureAddDelayThrowsExceptionOnDelayNull() {
      assertThrows(IllegalArgumentException.class, () -> {
        var trainDeparture = new TrainDeparture(1, "line", "destination", LocalTime.of(0, 0));
        trainDeparture.addDelay(null);
      });
    }

    @Test
    @DisplayName("Throws IllegalArgumentException on invalid delay")
    void TrainDepartureAddDelayThrowsExceptionOnDelayZero() {
      assertThrows(IllegalArgumentException.class, () -> {
        var trainDeparture = new TrainDeparture(1, "line", "destination", LocalTime.of(0, 0));
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
        new TrainDeparture(1, "line", "destination", LocalTime.of(0, 0));
      });
    }
  }

  @Test
  @DisplayName("addDelay does not throw IllegalArgumentException on valid parameters")
  void TrainDepartureAddDelayDoesNotThrowExceptionOnValidParameters() {
    assertDoesNotThrow(() -> {
      var trainDeparture = new TrainDeparture(1, "line", "destination", LocalTime.of(0, 0));
      trainDeparture.addDelay(LocalTime.of(0, 1));
    });
  }

  @Test
  @DisplayName("toString returns correct string")
  void TrainDepartureToStringReturnsCorrectString() {
    var trainDeparture = new TrainDeparture(1, "line", "destination", LocalTime.of(0, 0));
    assertEquals("Departure time: 00:00\nLine: line\nTrain number: 1\nDestination: destination\n", trainDeparture.toString());
  }
}