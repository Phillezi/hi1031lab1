package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import se.kth.hi1031.lab1.bo.service.user.UserService;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterController extends HttpServlet {
    public static void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    public static void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");
        System.out.println("registering user: " + name + " , " + email + " , " + password + " , " + role);

        ArrayList<RoleDTO> roles = new ArrayList<>(1);
        roles.add(new RoleDTO(role, null));

        UserService.createUser(new UserDTO(null, name, email, password, roles, new ArrayList<>()));
        // add session here
        resp.sendRedirect("/");
    }
}
