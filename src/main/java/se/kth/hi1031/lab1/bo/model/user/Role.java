package se.kth.hi1031.lab1.bo.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.dao.user.RoleDAO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;

import java.util.List;

/**
 * Represents a user role within the system, which is a collection of permissions
 * that define the access control level of a user.
 *
 * <p>A {@code Role} object consists of a unique name and a list of {@link Permission} objects
 * that specify the actions allowed for this role.</p>
 *
 * <p>This class uses Lombok annotations to automatically generate getters, setters, and
 * an all-arguments constructor.</p>
 */
@Getter
@Setter
@AllArgsConstructor
public class Role {
    private final String name;
    private final List<Permission> permissions;

    public Role(RoleDTO role) {
        this.name = role.getName();
        this.permissions = role.getPermissions() != null ? role.getPermissions()
                .stream()
                .map((PermissionDTO p) -> new Permission(p))
                .toList() : null;
    }

    /**
     * Converts this {@code Role} object into a {@link RoleDTO}, which is a Data Transfer Object (DTO)
     * used to transfer role data across different layers of the application.
     *
     * @return a {@code RoleDTO} containing the role's name and associated permissions.
     */
    public RoleDTO toDTO() {
        return new RoleDTO(
                this.name,
                this.permissions != null ? this.permissions.stream().map(Permission::toDTO).toList() : null
        );
    }

    /**
     * Converts this {@code Role} object into a {@link RoleDAO}, which is a Data Access Object (DAO)
     * used for storing role data in a database.
     *
     * @return a {@code RoleDAO} containing the role's name and associated permissions for persistence.
     */
    public RoleDAO toDAO() {
        return new RoleDAO(
                this.name,
                this.permissions != null ? this.permissions.stream().map(Permission::toDAO).toList() : null
        );
    }
}
