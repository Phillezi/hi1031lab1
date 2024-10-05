package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.dao.user.RoleDAO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;

import java.util.List;

public class RoleService {
    public static List<String> getAvailableRoles() {
        try {
            return RoleDAO.getAvailableRoles();
        } catch(DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
    public static RoleDTO getRole(String name) {
        if (name == null || name.isEmpty()) {
            throw new ServiceException("Role name cannot be null or empty");
        }
        try {
            return RoleDAO.getRole(name).toRole().toDTO();
        } catch(DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}