package se.kth.hi1031.lab1.bo.service.user;

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
    public static UserDTO createUser(UserDTO user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        List<Role> roles = user.getRoles().stream().map((RoleDTO r) -> new Role(r.getName(), null)).toList();
        UserDAO created = UserDAO.createUser(new User(user.getId(),
                user.getName(),
                user.getEmail(),
                hashedPassword,
                roles,
                new ArrayList<>()));

        return created.toUser().toDTO();
    }

    /**
     * Retrieves a list of all users in the system.
     *
     * <p>This method fetches all users from the database and converts them into a list 
     * of {@link UserDTO} objects.</p>
     *
     * @return A list of {@link UserDTO} representing all users.
     */
    public static List<UserDTO> getUsers() {
        List<UserDAO> users = UserDAO.getUsers();
        System.out.println(users);
        return users.stream().map(UserDAO::toUser).map(User::toDTO).toList();
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
     *         or null if the login fails.
     */
    public static UserDTO login(UserDTO user) {
        Optional<UserDAO> userOptional = UserDAO
                .login(new User(null, null, user.getEmail(), user.getPassword(), null, null));
        if (userOptional.isPresent()) {
            return userOptional.get().toUser().toDTO();
        } else {
            return null;
        }
    }

    // ToDo

}
