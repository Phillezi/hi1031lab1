package se.kth.hi1031.lab1.bo.middleware;

import se.kth.hi1031.lab1.bo.model.user.Permission;
import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.bo.model.user.User;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.util.Arrays;
import java.util.List;

/**
 * Middleware class responsible for handling authorization checks on the Business Object (BO) layer.
 * This class provides utility methods to check whether a user has specific roles or permissions.
 */
public class AuthMiddleware {

    /**
     * Checks if the given user has at least one of the specified permissions.
     *
     * @param user        the user to check, represented as a {@link UserDTO}
     * @param permissions a variable number of {@link PermissionDTO} objects to check
     * @return true if the user has one or more of the provided permissions, false otherwise
     */
    public static boolean userHasOneOf(UserDTO user, PermissionDTO... permissions) {
        List<Permission> permissions_ = Arrays.stream(permissions).map(Permission::new).toList();
        return new User(user).getPermissions().stream()
                .anyMatch(permissions_::contains
                );
    }


    /**
     * Checks if the given user has at least one of the specified roles.
     *
     * @param user  the user to check, represented as a {@link UserDTO}
     * @param roles a variable number of {@link RoleDTO} objects to check
     * @return true if the user has one or more of the provided roles, false otherwise
     */
    public static boolean userHasOneOf(UserDTO user, RoleDTO... roles) {
        List<Role> roles_ = Arrays.stream(roles).map(Role::new).toList();
        return new User(user).getRoles().stream()
                .anyMatch(roles_::contains
                );
    }

    /**
     * Checks if the given user has at least one of the specified permissions.
     * This version accepts {@link Permission} objects directly.
     *
     * @param user        the user to check, represented as a {@link UserDTO}
     * @param permissions a variable number of {@link Permission} objects to check
     * @return true if the user has one or more of the provided permissions, false otherwise
     */
    public static boolean userHasOneOf(UserDTO user, Permission... permissions) {
        List<Permission> permissions_ = Arrays.stream(permissions).toList();
        return new User(user).getPermissions().stream()
                .anyMatch(permissions_::contains
                );
    }

    /**
     * Checks if the given user has at least one of the specified roles.
     * This version accepts {@link Role} objects directly.
     *
     * @param user  the user to check, represented as a {@link UserDTO}
     * @param roles a variable number of {@link Role} objects to check
     * @return true if the user has one or more of the provided roles, false otherwise
     */
    public static boolean userHasOneOf(UserDTO user, Role... roles) {
        List<Role> roles_ = Arrays.stream(roles).toList();
        return new User(user).getRoles().stream()
                .anyMatch(roles_::contains
                );
    }

    /**
     * Checks if the given user has at least one of the specified permissions.
     * This version works directly with a {@link User} object.
     *
     * @param user        the user to check, represented as a {@link User}
     * @param permissions a variable number of {@link Permission} objects to check
     * @return true if the user has one or more of the provided permissions, false otherwise
     */
    public static boolean userHasOneOf(User user, Permission... permissions) {
        List<Permission> permissions_ = Arrays.stream(permissions).toList();
        return user.getPermissions().stream()
                .anyMatch(permissions_::contains
                );
    }


    /**
     * Checks if the given user has at least one of the specified roles.
     * This version works directly with a {@link User} object.
     *
     * @param user  the user to check, represented as a {@link User}
     * @param roles a variable number of {@link Role} objects to check
     * @return true if the user has one or more of the provided roles, false otherwise
     */
    public static boolean userHasOneOf(User user, Role... roles) {
        List<Role> roles_ = Arrays.stream(roles).toList();
        return user.getRoles().stream()
                .anyMatch(roles_::contains
                );
    }
}
