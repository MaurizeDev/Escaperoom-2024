package escaperoom.interfaces;

import java.time.Duration;
import java.time.LocalTime;

/**
 * Not a template, just a reference type. The interface provides methods that are repeatedly used in puzzles.
 */
public interface TimeMeasurement {
    
    /**
     * Default method for determining the start time.
     * @return current time
     */
    default LocalTime startTime() {
        return LocalTime.now();
    }

    /**
     * Default method for calculating the time difference.
     * @param startTime
     * @return time taken
     */
    default long calculateDuration(LocalTime startTime) {
        LocalTime endTime = LocalTime.now();
        return Duration.between(startTime, endTime).getSeconds();
    }
    
    // Although every puzzle has a score calculation, the formula for calculation is different each time -> difficult to solve via interface.
}
