package se.kth.hi1031.lab1.bo.service.order;

import se.kth.hi1031.lab1.bo.model.order.Order;
import se.kth.hi1031.lab1.bo.model.order.Status;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class StatusService {
    public Optional<Order> addStatusToOrder(Order order, String statusDescription) {
        if (order != null) {
            Status newStatus = new Status(statusDescription, new Timestamp(System.currentTimeMillis()));
            order.getStatuses().add(newStatus);
            return Optional.of(order);
        }
        return Optional.empty();
    }

    public List<Status> getStatusHistory(Order order) {
        return order != null ? order.getStatuses() : null;
    }
}

