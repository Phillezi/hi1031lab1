package se.kth.hi1031.lab1.ui.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Data Transfer Object representing a user role.
 *
 * <p>The RoleDTO class encapsulates the name of a role and its associated
 * permissions within the application, defining what actions a user with
 * this role is allowed to perform.</p>
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class RoleDTO {
    private final String name;
    private final List<PermissionDTO> permissions;
}
