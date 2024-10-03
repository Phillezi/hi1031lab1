package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.bo.model.user.Permission;
import se.kth.hi1031.lab1.bo.model.user.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoleService {
    private final List<Role> roles = new ArrayList<>();

    public Role createRole(String name) {
        Role role = new Role(name, new ArrayList<>());
        roles.add(role);
        return role;
    }

    public List<Role> getAllRoles() {
        return new ArrayList<>(roles);
    }

    public Optional<Role> getRoleByName(String name) {
        return roles.stream().filter(role -> role.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<Role> updateRoleName(String currentName, String newName) {
        Optional<Role> roleOptional = getRoleByName(currentName);
        roleOptional.ifPresent(role -> {
            roles.remove(role); // Remove the old role
            Role updatedRole = new Role(newName, role.getPermissions());
            roles.add(updatedRole);
        });
        return roleOptional;
    }

    public boolean deleteRoleByName(String name) {
        return roles.removeIf(role -> role.getName().equalsIgnoreCase(name));
    }

    public Optional<Role> addPermissionToRole(Role role, Permission permission) {
        if (role != null && permission != null) {
            role.getPermissions().add(permission);
            return Optional.of(role);
        }
        return Optional.empty();
    }

    public Optional<Role> removePermissionFromRole(Role role, Permission permission) {
        if (role != null && permission != null) {
            role.getPermissions().remove(permission);
            return Optional.of(role);
        }
        return Optional.empty();
    }

    public List<Permission> getPermissionsOfRole(Role role) {
        if (role != null) {
            return new ArrayList<>(role.getPermissions());
        }
        return new ArrayList<>();
    }

}
