package se.kth.hi1031.lab1.db.dao.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import se.kth.hi1031.lab1.bo.model.user.Role;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class UserDAO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private ArrayList<Role> roles;
}
