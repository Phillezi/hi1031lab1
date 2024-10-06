package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * UI Controller to handle session stored errors.
 */
public class ErrorController extends HttpServlet {
    /**
     * Handles requests to clear errors from the sessions.
     * Triggered in the UI when someone clears an error.
     */
    public static void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("error");

        resp.sendRedirect(req.getHeader("Referer"));
    }
}