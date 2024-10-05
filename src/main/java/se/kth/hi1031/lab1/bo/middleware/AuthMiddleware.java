package se.kth.hi1031.lab1.bo.middleware;

import se.kth.hi1031.lab1.bo.model.user.Permission;
import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.bo.model.user.User;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.util.Arrays;
import java.util.List;

public class AuthMiddleware {

    public static boolean userHasOneOf(UserDTO user, PermissionDTO... permissions) {
        List<Permission> permissions_ = Arrays.stream(permissions).map(Permission::new).toList();
        return new User(user).getPermissions().stream()
                .anyMatch(permissions_::contains
                );
    }


    public static boolean userHasOneOf(UserDTO user, RoleDTO... roles) {
        List<Role> roles_ = Arrays.stream(roles).map(Role::new).toList();
        return new User(user).getRoles().stream()
                .anyMatch(roles_::contains
                );
    }

    public static boolean userHasOneOf(UserDTO user, Permission... permissions) {
        List<Permission> permissions_ = Arrays.stream(permissions).toList();
        return new User(user).getPermissions().stream()
                .anyMatch(permissions_::contains
                );
    }


    public static boolean userHasOneOf(UserDTO user, Role... roles) {
        List<Role> roles_ = Arrays.stream(roles).toList();
        return new User(user).getRoles().stream()
                .anyMatch(roles_::contains
                );
    }

    public static boolean userHasOneOf(User user, Permission... permissions) {
        List<Permission> permissions_ = Arrays.stream(permissions).toList();
        return user.getPermissions().stream()
                .anyMatch(permissions_::contains
                );
    }


    public static boolean userHasOneOf(User user, Role... roles) {
        List<Role> roles_ = Arrays.stream(roles).toList();
        return user.getRoles().stream()
                .anyMatch(roles_::contains
                );
    }
}
