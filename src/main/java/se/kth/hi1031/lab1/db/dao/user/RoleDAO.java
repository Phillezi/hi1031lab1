package se.kth.hi1031.lab1.db.dao.user;

import lombok.Data;

import java.util.List;

@Data
public class RoleDAO {
    private final String name;
    private final List<PermissionDAO> permissions;
}
