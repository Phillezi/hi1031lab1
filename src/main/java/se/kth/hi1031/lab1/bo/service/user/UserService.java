package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.bo.middleware.AuthMiddleware;
import se.kth.hi1031.lab1.bo.service.PermissionException;
import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.dao.user.UserDAO;
import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;
import se.kth.hi1031.lab1.bo.model.user.User;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Service class for managing user related operations.
 *
 * <p>The {@code UserService} class provides methods for creating users, retrieving user
 * information, and handling user login operations.</p>
 */
public class UserService {

    /**
     * Creates a new user with the provided user data.
     *
     * <p>This method hashes the user's password and creates a new user in the system.
     * It also associates roles with the user based on the provided {@link UserDTO}.</p>
     *
     * @param user The {@link UserDTO} object containing the user's details.
     * @return A {@link UserDTO} representing the created user, including their roles.
     */
    public static UserDTO createUser(UserDTO user) throws ServiceException {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        List<Role> roles = user.getRoles().stream().map((RoleDTO r) -> new Role(r.getName(), null)).toList();
        try {
            UserDAO created = UserDAO.createUser(new User(user.getId(), user.getName(), user.getEmail(), hashedPassword, roles, new ArrayList<>()));

            return created.toUser().toDTO();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of all users in the system.
     *
     * <p>This method fetches all users from the database and converts them into a list
     * of {@link UserDTO} objects.</p>
     *
     * @return A list of {@link UserDTO} representing all users.
     */
    public static List<UserDTO> getUsers(UserDTO user) throws ServiceException {
        if (!AuthMiddleware.userHasOneOf(user, new Role("admin"))) {
            throw new PermissionException("User " + user.getName() + " needs to be admin to create products.");
        }
        try {
            List<UserDAO> users = UserDAO.getUsers();
            return users.stream().map(UserDAO::toUser).map(User::toDTO).toList();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Logs in a user by validating their email and password.
     *
     * <p>This method attempts to authenticate the user using the provided email and
     * password. If successful, it returns a {@link UserDTO} for the logged-in user;
     * otherwise, it returns null.</p>
     *
     * @param user The {@link UserDTO} object containing the user's email and password.
     * @return A {@link UserDTO} for the authenticated user if login is successful,
     * or null if the login fails.
     */
    public static UserDTO login(UserDTO user) throws ServiceException {
        try {
            Optional<UserDAO> userOptional = UserDAO.login(new User(null, null, user.getEmail(), user.getPassword(), null, null));
            if (userOptional.isPresent()) {
                return userOptional.get().toUser().toDTO();
            } else {
                throw new ServiceException("Invalid username or password");
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param user The authenticated {@link UserDTO} requesting the information.
     * @param userId The ID of the user to retrieve.
     * @return A {@link UserDTO} representing the user with the specified ID.
     * @throws ServiceException If the user lacks permission or the user is not found.
     */
    public static UserDTO getUserById(UserDTO user, int userId) {
        if (!user.getId().equals(userId) && !AuthMiddleware.userHasOneOf(user, new Role("admin"))) {
            throw new PermissionException("User " + user.getName() + " needs to be admin to get users other than self.");
        }
        try {
            Optional<UserDAO> userOptional = UserDAO.getUserByid(userId);
            if (userOptional.isPresent()) {
                return userOptional.get().toUser().toDTO();
            } else {
                throw new ServiceException("No user found with id " + userId);
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Updates the specified user information.
     *
     * @param user The authenticated {@link UserDTO} requesting the update.
     * @param userToUpdate The {@link UserDTO} containing updated user data.
     * @throws ServiceException If the user lacks permission or a data access error occurs.
     */
    public static void updateUser(UserDTO user, UserDTO userToUpdate) {
        if (!user.getId().equals(userToUpdate.getId()) && !AuthMiddleware.userHasOneOf(user, new Role("admin"))) {
            throw new PermissionException("User " + user.getName() + " needs to be admin to update users other than self.");
        }
        try {
            User user_ = new User(userToUpdate);
            if (user.getPassword() != null && !user_.getPassword().isEmpty()) {
                user_.setPassword(BCrypt.hashpw(user_.getPassword(), BCrypt.gensalt()));
            }
            UserDAO.updateUser(user_);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Deletes a user by their ID.
     *
     * @param user The authenticated {@link UserDTO} requesting the deletion.
     * @param id The ID of the user to delete.
     * @throws ServiceException If the user lacks permission or a data access error occurs.
     */
    public static void deleteUserById(UserDTO user, int id) {
        if (!user.getId().equals(id) && !AuthMiddleware.userHasOneOf(user, new Role("admin"))) {
            throw new PermissionException("User " + user.getName() + " needs to be admin to delete users other than self.");
        }
        try {
            UserDAO.deleteUserById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
