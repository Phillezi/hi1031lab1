package se.kth.hi1031.lab1.bo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Campaign {
    private String name;
    private String description;
    private Timestamp start;
    private Timestamp end;
    private int discountPercent;
}
