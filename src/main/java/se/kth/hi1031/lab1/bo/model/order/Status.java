package se.kth.hi1031.lab1.bo.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.ui.dto.order.StatusDTO;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Status {
    private String status;
    private Timestamp timestamp;

    public StatusDTO toDTO() {
        return new StatusDTO(status, timestamp);
    }
}
