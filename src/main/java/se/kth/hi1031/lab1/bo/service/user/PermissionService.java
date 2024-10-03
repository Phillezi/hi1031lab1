package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.bo.model.user.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PermissionService {
    private final List<Permission> permissions = new ArrayList<>();

    public Permission createPermission(String name) {
        Permission permission = new Permission(name);
        permissions.add(permission);
        return permission;
    }

    public List<Permission> getAllPermissions() {
        return new ArrayList<>(permissions);
    }

    public Optional<Permission> getPermissionByName(String name) {
        return permissions.stream().filter(permission -> permission.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<Permission> updatePermission(String currentName, String newName) {
        Optional<Permission> permissionOptional = getPermissionByName(currentName);
        permissionOptional.ifPresent(permission -> {
            permissions.remove(permission);
            Permission updatedPermission = new Permission(newName);
            permissions.add(updatedPermission);
        });
        return permissionOptional;
    }

    public boolean deletePermissionByName(String name) {
        return permissions.removeIf(permission -> permission.getName().equalsIgnoreCase(name));
    }

}
