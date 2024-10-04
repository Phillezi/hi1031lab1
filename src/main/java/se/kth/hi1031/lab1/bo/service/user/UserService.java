package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.db.dao.user.UserDAO;
import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;
import se.kth.hi1031.lab1.bo.model.user.User;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    public static UserDTO createUser(UserDTO user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        List<Role> roles = user.getRoles().stream().map((RoleDTO r) -> new Role(r.getName(), null)).toList();
        UserDAO created = UserDAO.createUser(new User(user.getId(),
                user.getName(),
                user.getEmail(),
                hashedPassword,
                roles,
                new ArrayList<>()));

        return created.toUser().toDTO();
    }

    public static List<UserDTO> getUsers() {
        List<UserDAO> users = UserDAO.getUsers();
        System.out.println(users);
        return users.stream().map(UserDAO::toUser).map(User::toDTO).toList();
    }

    public static UserDTO login(UserDTO user) {
        Optional<UserDAO> userOptional = UserDAO
                .login(new User(null, null, user.getEmail(), user.getPassword(), null, null));
        if (userOptional.isPresent()) {
            return userOptional.get().toUser().toDTO();
        } else {
            return null;
        }
    }

    // ToDo

}
