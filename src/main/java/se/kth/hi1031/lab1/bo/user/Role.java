package se.kth.hi1031.lab1.bo.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Role {
    private final String name;
    private final ArrayList<Permission> permissions;
}
