package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.hi1031.lab1.bo.service.user.UserService;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.io.IOException;

public class LoginController extends HttpServlet {
    public static void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    public static void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDTO user = UserService.login(new UserDTO(null, password, email, password, null, null));
        HttpSession session = req.getSession();
        if (user != null) {
            session.setAttribute("user", user);
            resp.sendRedirect("/logged_in");
            return;
        }

        resp.sendRedirect("/error");
    }
}
