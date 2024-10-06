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

/**
 * Service class responsible for managing product categories.
 * This class interacts with the {@link CategoryDAO} and handles authorization checks
 * through the {@link AuthMiddleware}.
 */
public class CategoryService {

    /**
     * Retrieves a list of available category names.
     *
     * @return a {@link List} of available category names as Strings
     * @throws ServiceException if a {@link DAOException} occurs during the data access operation
     */
    public static List<String> getAvailableCategories() {
        try {
            return CategoryDAO.getAvailableCategories();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Retrieves a list of categories.
     *
     * @return a {@link List} of {@link CategoryDTO} objects representing the available categories
     * @throws ServiceException if a {@link DAOException} occurs during the data access operation
     */
    public static List<CategoryDTO> getCategories() {
        try {
            return CategoryDAO.getCategories().stream().map(CategoryDAO::toCategory).map(Category::toDTO).toList();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Retrieves a category by its name.
     *
     * @param name the name of the category to retrieve
     * @return the {@link CategoryDTO} representing the category with the given name
     * @throws ServiceException if the category is not found or if a {@link DAOException} occurs
     */
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

    /**
     * Creates a new category.
     * Only users with the "admin" role are authorized to create categories.
     *
     * @param user     the user attempting to create the category, represented as a {@link UserDTO}
     * @param category the {@link CategoryDTO} representing the new category
     * @throws PermissionException if the user does not have the required "admin" role
     * @throws ServiceException    if a {@link DAOException} occurs during the category creation
     */
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

    /**
     * Updates an existing category.
     * Only users with the "admin" role are authorized to update categories.
     *
     * @param user     the user attempting to update the category, represented as a {@link UserDTO}
     * @param category the {@link CategoryDTO} representing the category to be updated
     * @throws PermissionException if the user does not have the required "admin" role
     * @throws ServiceException    if a {@link DAOException} occurs during the category update
     */
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
