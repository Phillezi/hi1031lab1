package se.kth.hi1031.lab1.db.dao.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class StatusDAO {
    private String status;
    private Timestamp timestamp;
}
