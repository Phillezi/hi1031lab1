package se.kth.hi1031.lab1.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Cart {
    private final ArrayList<Product> products;
}
