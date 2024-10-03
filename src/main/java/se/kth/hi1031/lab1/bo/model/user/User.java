package se.kth.hi1031.lab1.bo.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.dao.user.UserDAO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.util.ArrayList;
import java.util.List;

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

    public UserDTO toDTO() {
        return new UserDTO(
                this.id,
                this.name,
                this.email,
                this.password,
                this.roles.stream().map(Role::toDTO).toList(),
                this.permissions.stream().map(Permission::toDTO).toList()
        );
    }

    public UserDAO toDAO() {
        return new UserDAO(
                this.id,
                this.name,
                this.email,
                this.password,
                this.roles.stream().map(Role::toDAO).toList(),
                this.permissions.stream().map(Permission::toDAO).toList()
        );
    }
}
