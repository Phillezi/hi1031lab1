package se.kth.hi1031.lab1.bo.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.dao.user.RoleDAO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Role {
    private final String name;
    private final List<Permission> permissions;

    public RoleDTO toDTO() {
        return new RoleDTO(
                this.name,
                this.permissions.stream().map(Permission::toDTO).toList()
        );
    }

    public RoleDAO toDAO() {
        return new RoleDAO(
                this.name,
                this.permissions.stream().map(Permission::toDAO).toList()
        );
    }
}
