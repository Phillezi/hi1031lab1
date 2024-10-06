package se.kth.hi1031.lab1.ui.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Data Transfer Object representing the status of an order.
 *
 * <p>The StatusDTO class is used to encapsulate information about the status
 * of an order, including the status message and the timestamp indicating
 * when that status was recorded.</p>
 *
 * <p>This class uses Lombok annotations to automatically generate
 * getters, setters, and an all-arguments constructor.</p>
 */
@Getter
@Setter
@AllArgsConstructor
public class StatusDTO implements Comparable<StatusDTO>{
    private String status;
    private Timestamp timestamp;

    @Override
    public int compareTo(StatusDTO other) {
        int timestampComparison = this.timestamp.compareTo(other.timestamp);
        if (timestampComparison != 0) {
            return timestampComparison;
        }
        return this.status.compareTo(other.status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StatusDTO other = (StatusDTO) obj;

        return Objects.equals(status, other.status) &&
                Objects.equals(timestamp, other.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, timestamp);
    }
}
