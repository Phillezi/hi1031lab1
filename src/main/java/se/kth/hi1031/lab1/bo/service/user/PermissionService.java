package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.dao.user.PermissionDAO;

import java.util.List;

public class PermissionService {
    public static List<String> getAvailablePermissions() {
        try {
            return PermissionDAO.getAvailablePermissions();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
