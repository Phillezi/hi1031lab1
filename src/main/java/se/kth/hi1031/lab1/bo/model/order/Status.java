package se.kth.hi1031.lab1.bo.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.dao.order.StatusDAO;
import se.kth.hi1031.lab1.ui.dto.order.StatusDTO;

import java.sql.Timestamp;

/**
 * Represents the status of an order.
 *
 * <p>The status tracks various stages of the order, such as
 * "packed", "shipped", "delivered", etc., along with the timestamp when
 * that status was assigned.</p>
 *
 * <p>This class uses Lombok annotations to automatically generate
 * getters, setters, and an all-arguments constructor.</p>
 */
@Getter
@Setter
@AllArgsConstructor
public class Status {
    private String status;
    private Timestamp timestamp;

    public Status(StatusDTO status) {
        this.status = status.getStatus();
        this.timestamp = status.getTimestamp();
    }

    /**
     * Converts this {@code Status} object into a {@link StatusDTO},
     * which is a Data Transfer Object (DTO) used for transferring status data across
     * different layers of the application.
     *
     * @return a {@code StatusDTO} containing the status data.
     */
    public StatusDTO toDTO() {
        return new StatusDTO(status, timestamp);
    }

    /**
     * Converts this {@code Status} object into a {@link StatusDAO},
     * which is a Data Access Object (DAO) used for storing status data in a database.
     *
     * @return a {@code StatusDAO} containing the status data for persistence.
     */
    public StatusDAO toDAO() {
        return new StatusDAO(status, timestamp);
    }
}
