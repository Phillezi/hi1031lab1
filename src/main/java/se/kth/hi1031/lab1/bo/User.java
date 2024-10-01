package se.kth.hi1031.lab1.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private final int id;
    private final String name;
    private final String email;
    private final String password;
}
