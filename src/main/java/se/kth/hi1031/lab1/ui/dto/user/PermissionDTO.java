package se.kth.hi1031.lab1.ui.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Data Transfer Object representing a user permission.
 *
 * <p>The PermissionDTO class encapsulates the name of a permission 
 * associated with a user role within the application.</p>
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PermissionDTO {
    private final String name;
}
