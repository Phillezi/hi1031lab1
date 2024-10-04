package se.kth.hi1031.lab1.ui.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

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
public class StatusDTO {
    private String status;
    private Timestamp timestamp;
}
