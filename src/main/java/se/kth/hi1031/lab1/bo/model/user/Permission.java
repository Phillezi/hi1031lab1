package se.kth.hi1031.lab1.bo.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import se.kth.hi1031.lab1.db.dao.user.PermissionDAO;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;

@Getter
@Setter
@AllArgsConstructor
public class Permission implements Comparable {
    private final String name;

    public PermissionDTO toDTO() {
        return new PermissionDTO(name);
    }

    public PermissionDAO toDAO() {
        return new PermissionDAO(name);
    }
}
