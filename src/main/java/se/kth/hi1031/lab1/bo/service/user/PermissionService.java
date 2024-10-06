package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.dao.user.PermissionDAO;

import java.util.List;

/**
 * Service class for managing user permissions in the application.
 */
public class PermissionService {

    /**
     * Retrieves a list of available permissions.
     *
     * <p>This method fetches all available permissions
     * and returns them as a list of permission names in String format.</p>
     *
     * @return A {@link List} of available permissions as {@link String}.
     * @throws ServiceException If a {@link DAOException} occurs during the data access operation.
     */
    public static List<String> getAvailablePermissions() {
        try {
            return PermissionDAO.getAvailablePermissions();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
