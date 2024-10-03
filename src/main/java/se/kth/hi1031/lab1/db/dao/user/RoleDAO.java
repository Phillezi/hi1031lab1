package se.kth.hi1031.lab1.db.dao.user;

import lombok.Data;
import se.kth.hi1031.lab1.bo.model.user.Permission;
import se.kth.hi1031.lab1.bo.model.user.Role;

import java.util.List;

@Data
public class RoleDAO {
    private final String name;
    private final List<PermissionDAO> permissions;

    public Role toRole() {
        List<Permission> permissions = this.permissions.stream().map(PermissionDAO::toPermission).toList();
        return new Role(this.name, permissions);
    }
}
