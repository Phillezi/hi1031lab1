package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.db.dao.user.RoleDAO;

import java.util.List;

public class RoleService {
    public static List<String> getAvailableRoles() {
        return RoleDAO.getAvailableRoles();
    }
}