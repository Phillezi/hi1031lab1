package se.kth.hi1031.lab1.db.dao.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import se.kth.hi1031.lab1.bo.model.user.Permission;

@AllArgsConstructor
@Data
public class PermissionDAO {
    private final String name;

    public Permission toPermission() {
        return new Permission(name);
    }
}
