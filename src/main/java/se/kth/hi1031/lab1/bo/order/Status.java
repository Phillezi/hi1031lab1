package se.kth.hi1031.lab1.bo.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Status {
    private String status;
    private Timestamp timestamp;
}
