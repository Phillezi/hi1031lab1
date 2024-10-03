package se.kth.hi1031.lab1.bo.service.user;

import se.kth.hi1031.lab1.db.dao.user.UserDAO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;
import se.kth.hi1031.lab1.bo.model.user.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserService {

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static UserDTO createUser(UserDTO user){
        String hashedPassword = hashPassword(user.getPassword());
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), hashedPassword, user.getRoles(), user.getPermissions());
    }

    public static List<UserDTO> getUsers() {
        List<UserDAO> users = UserDAO.getUsers();
        return users.stream().map(UserDAO::toUser).map(User::toDTO).toList();
    }

    //ToDo

}
