package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.dao.user.RoleDAO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;

import java.util.List;

/**
 * Service class for managing user roles in the application.
 *
 * <p>The {@code RoleService} class provides methods for retrieving available roles and
 * fetching specific role by name. It interacts with the {@link RoleDAO} to
 * access role data and handles potential exceptions during data access.</p>
 */
public class RoleService {

    /**
     * Retrieves a list of available roles.
     *
     * @return A {@link List} of available roles as {@link String}.
     * @throws ServiceException If a {@link DAOException} occurs during the data access operation.
     */
    public static List<String> getAvailableRoles() {
        try {
            return RoleDAO.getAvailableRoles();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Retrieves a specific role by its name.
     *
     * @param name The name of the role to retrieve.
     * @return A {@link RoleDTO} representing the role with the specified name.
     * @throws ServiceException If the role name is null, empty, or if a {@link DAOException} occurs.
     */
    public static RoleDTO getRole(String name) {
        if (name == null || name.isEmpty()) {
            throw new ServiceException("Role name cannot be null or empty");
        }
        try {
            return RoleDAO.getRole(name).toRole().toDTO();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}