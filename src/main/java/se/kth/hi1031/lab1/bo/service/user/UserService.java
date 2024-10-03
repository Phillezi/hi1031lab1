package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.bo.model.user.Role;
import se.kth.hi1031.lab1.bo.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    private final List<User> users = new ArrayList<>();

    public User createUser(Integer id, String name, String email, String password) {
        User user = new User(id, name, email, password, new ArrayList<>());
        users.add(user);
        return user;
    }
    
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public Optional<User> getUserById(Integer id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public Optional<User> getUserByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public Optional<User> updateUser(Integer id, String name, String email, String password) {
        Optional<User> userOptional = getUserById(id);
        userOptional.ifPresent(user -> {
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
        });
        return userOptional;
    }

    public boolean deleteUserById(Integer id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    public Optional<User> assignRoleToUser(User user, Role role) {
        if (user != null && role != null) {
            user.getRoles().add(role);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public Optional<User> removeRoleFromUser(User user, Role role) {
        if (user != null && role != null) {
            user.getRoles().remove(role);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public List<Role> getUserRoles(User user) {
        if (user != null) {
            return new ArrayList<>(user.getRoles());
        }
        return new ArrayList<>();
    }
}
