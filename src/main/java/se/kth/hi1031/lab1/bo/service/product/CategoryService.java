package se.kth.hi1031.lab1.bo.service.product;

import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.dao.product.CategoryDAO;

import java.util.List;

public class CategoryService {

    public static List<String> getAvailableCategories() {
        try {
            return CategoryDAO.getAvailableCategories();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
