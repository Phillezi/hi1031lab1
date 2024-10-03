package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.db.dao.user.UserDAO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;
import se.kth.hi1031.lab1.bo.model.user.User;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    public static UserDTO createUser(UserDTO user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), hashedPassword, user.getRoles(),
                user.getPermissions());
    }

    public static List<UserDTO> getUsers() {
        List<UserDAO> users = UserDAO.getUsers();
        System.out.println(users);
        return users.stream().map(UserDAO::toUser).map(User::toDTO).toList();
    }

    // ToDo

}
