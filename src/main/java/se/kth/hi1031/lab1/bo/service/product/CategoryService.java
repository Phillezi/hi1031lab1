package se.kth.hi1031.lab1.bo.service.product;

import se.kth.hi1031.lab1.bo.middleware.AuthMiddleware;
import se.kth.hi1031.lab1.bo.model.product.Category;
import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.bo.service.PermissionException;
import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.db.DAOException;
import se.kth.hi1031.lab1.db.dao.product.CategoryDAO;
import se.kth.hi1031.lab1.ui.dto.product.CategoryDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.util.List;
import java.util.Optional;

public class CategoryService {

    public static List<String> getAvailableCategories() {
        try {
            return CategoryDAO.getAvailableCategories();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static List<CategoryDTO> getCategories() {
        try {
            return CategoryDAO.getCategories().stream().map(CategoryDAO::toCategory).map(Category::toDTO).toList();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static CategoryDTO getCategoryByName(String name) {
        try {
            Optional<CategoryDAO> optionalCategory = CategoryDAO.getCategoryByName(name);
            if (optionalCategory.isPresent()) {
                return optionalCategory.get().toCategory().toDTO();
            } else {
                throw new ServiceException("No category found with name " + name + ".");
            }
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static void createCategory(UserDTO user, CategoryDTO category) {
        if (!AuthMiddleware.userHasOneOf(user, new Role("admin"))) {
            throw new PermissionException("User: " + user.getName() + " doesnt have permission to create categories.");
        }
        try {
            CategoryDAO.createCategory(new Category(category));
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public static void updateCategory(UserDTO user, CategoryDTO category) {
        if (!AuthMiddleware.userHasOneOf(user, new Role("admin"))) {
            throw new PermissionException("User: " + user.getName() + " doesnt have permission to create categories.");
        }
        try {
            CategoryDAO.updateCategory(new Category(category));
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
