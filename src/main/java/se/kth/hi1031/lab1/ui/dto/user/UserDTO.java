package se.kth.hi1031.lab1.ui.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object representing a user in the application.
 *
 * <p>The UserDTO class encapsulates the data associated with a user, including 
 * their identification, credentials, roles, and permissions within the application.</p>
 */
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private List<RoleDTO> roles;
    private List<PermissionDTO> permissions;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + id)
                .append("\nname: " + name)
                .append("\nemail: " + email)
                .append("\npassword: " + password)
                .append("\nroles: ");
        for (RoleDTO role : roles) {
            sb.append("\n\t" + role.toString());
        }
        sb.append("\npermissions: ");
        for (PermissionDTO perm : permissions) {
            sb.append("\n\t" + perm.toString());
        }
        return sb.toString();
    }
}
