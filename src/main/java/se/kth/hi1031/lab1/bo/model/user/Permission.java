package se.kth.hi1031.lab1.bo.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.dao.user.PermissionDAO;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;

/**
 * Represents a user permission within the system, used for control levels for different actions.
 * 
 * <p>The {@code Permission} class implements {@link Comparable} to enable natural ordering 
 * of permissions based on their name. It also overrides {@code equals()} for logical comparison.</p>
 * 
 * <p>This class uses Lombok annotations to automatically generate getters, setters, and an all-arguments constructor.</p>
 */
@Getter
@Setter
@AllArgsConstructor
public class Permission implements Comparable<Permission> {
    private final String name;

    public Permission(PermissionDTO permission) {
        this.name = permission.getName();
    }

    /**
     * Converts this {@code Permission} object into a {@link PermissionDTO}, which is a Data Transfer Object (DTO)
     * used to transfer permission data across different layers of the application.
     *
     * @return a {@code PermissionDTO} containing the permission's name.
     */
    public PermissionDTO toDTO() {
        return new PermissionDTO(name);
    }

    /**
     * Converts this {@code Permission} object into a {@link PermissionDAO}, which is a Data Access Object (DAO)
     * used for storing permission data in a database.
     *
     * @return a {@code PermissionDAO} containing the permission's name for persistence.
     */
    public PermissionDAO toDAO() {
        return new PermissionDAO(name);
    }

    /**
     * Compares this permission with another {@code Permission} object for order, 
     * based on the lexicographical ordering of their names.
     *
     * @param p the other {@code Permission} to be compared.
     * @return a negative integer, zero, or a positive integer as this permission's name is 
     *         less than, equal to, or greater than the specified permission's name.
     */
    @Override
    public int compareTo(Permission p) {
        return name.compareTo(p.getName());
    }

    /**
     * Determines whether this permission is equal to another object. 
     * Two {@code Permission} objects are considered equal if their names are identical.
     *
     * @param o the object to compare with this {@code Permission}.
     * @return {@code true} if the given object is a {@code Permission} with the same name; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Permission) {
            return this.compareTo((Permission)o) == 0;
        }
        return false;
    }
}
