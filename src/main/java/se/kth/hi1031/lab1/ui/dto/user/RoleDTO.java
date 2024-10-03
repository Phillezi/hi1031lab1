package se.kth.hi1031.lab1.ui.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RoleDTO {
    private final String name;
    private final List<PermissionDTO> permissions;
}
