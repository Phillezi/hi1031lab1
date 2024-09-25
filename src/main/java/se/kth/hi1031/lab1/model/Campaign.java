package se.kth.hi1031.lab1.model;

import java.sql.Timestamp;

public class Campaign {
    private int id;
    private String name;
    private String description;
    private Timestamp start;
    private Timestamp end;
    private int discountPercent;

    public Campaign(int id, String name, String description, Timestamp start, Timestamp end, int discountPercent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start = start;
        this.end = end;
        this.discountPercent = discountPercent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }
}
