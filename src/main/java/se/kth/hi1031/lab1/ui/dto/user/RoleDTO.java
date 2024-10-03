package se.kth.hi1031.lab1.ui.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class RoleDTO {
    private final String name;
    private final ArrayList<PermissionDTO> permissions;
}
