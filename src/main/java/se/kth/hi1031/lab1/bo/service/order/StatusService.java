package se.kth.hi1031.lab1.bo.service.order;

import se.kth.hi1031.lab1.bo.model.order.Status;
import se.kth.hi1031.lab1.bo.service.PermissionException;
import se.kth.hi1031.lab1.db.dao.order.OrderDAO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;
import se.kth.hi1031.lab1.db.dao.order.*;

import java.sql.Timestamp;
import java.util.Optional;

/**
 * Static service class for managing order statuses within the application.
 * This class provides static methods to update the status of orders based on user permissions.
 */
public class StatusService {

    /**
     * Sets the status of an order.
     *
     * <p>The method verifies whether the user has the necessary permissions to update the order status.
     * If the status is 'packed', the user must have the 'pack_orders' permission. For all other statuses,
     * the user must have the 'update_orders' permission.</p>
     *
     * @param orderId The ID of the order to update.
     * @param status  The new status to set.
     * @param user    The user attempting to set the status.
     * @throws PermissionException if the user lacks the necessary permissions to update the order status.
     */
    public static void setOrderStatus(int orderId, String status, UserDTO user) {
        // Check if the user has the required permissions
        if ("packed".equals(status) && !hasPermission(user, "pack_orders")) {
            throw new PermissionException("User " + user.getName() + " does not have permission to pack orders.");
        } else if (!"packed".equals(status) && !hasPermission(user, "update_orders")) {
            throw new PermissionException("User " + user.getName() + " does not have permission to update orders.");
        }

        Optional<OrderDAO> optionalOrder = OrderDAO.getOrderById(orderId);
        if (optionalOrder.isPresent()) {
            StatusDAO.createStatus(orderId, new Status(status, new Timestamp(System.currentTimeMillis())));
        } else {
            throw new IllegalArgumentException("Order with ID " + orderId + " not found.");
        }
    }

    /**
     * Check if the user has the required permission.
     *
     * @param user       The user to check.
     * @param permission The required permission.
     * @return True if the user has the permission, false otherwise.
     */
    private static boolean hasPermission(UserDTO user, String permission) {
        return user.getPermissions().stream()
                .anyMatch(perm -> perm.getName().equals(permission));
    }
}
