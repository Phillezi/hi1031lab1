package se.kth.hi1031.lab1.bo.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.dao.user.UserDAO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the system, containing details such as user ID, name, email, 
 * password, associated roles, and permissions.
 * 
 * <p>This class uses Lombok annotations to automatically generate getters, setters, and an all-arguments constructor.</p>
 */
@Getter
@Setter
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private List<Role> roles;
    private List<Permission> permissions;

    public User(UserDTO user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles() != null ? user.getRoles()
                            .stream()
                            .map((RoleDTO r) -> new Role(r))
                            .toList() : null;
        this.permissions = user.getPermissions() != null ? user.getPermissions()
                                .stream()
                                .map((PermissionDTO p) -> new Permission(p))
                                .toList() : null;
    }

    /**
     * Converts this {@code User} object into a {@link UserDTO}, which is a Data Transfer Object (DTO)
     * used to transfer user data across different layers of the application.
     *
     * @return a {@code UserDTO} containing the user's ID, name, email, password, roles, and permissions.
     */
    public UserDTO toDTO() {
        return new UserDTO(
                this.id,
                this.name,
                this.email,
                this.password,
                this.roles != null ? this.roles.stream().map(Role::toDTO).toList() : null,
                this.permissions != null ? this.permissions.stream().map(Permission::toDTO).toList() : null
        );
    }

    /**
     * Converts this {@code User} object into a {@link UserDAO}, which is a Data Access Object (DAO)
     * used for storing user data in a database.
     *
     * @return a {@code UserDAO} containing the user's ID, name, email, password, roles, and permissions for persistence.
     */
    public UserDAO toDAO() {
        return new UserDAO(
                this.id,
                this.name,
                this.email,
                this.password,
                this.roles != null ? this.roles.stream().map(Role::toDAO).toList() : null,
                this.permissions != null ? this.permissions.stream().map(Permission::toDAO).toList() : null
        );
    }
}
