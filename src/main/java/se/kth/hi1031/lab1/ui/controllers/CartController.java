package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;


public class CartController extends HttpServlet {
    public static void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

    public static void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdStr = req.getParameter("productId");
        String quantityStr = req.getParameter("quantity");

        Integer productId = Integer.valueOf(productIdStr);
        Integer quantity = Integer.valueOf(quantityStr);

        System.out.println(productIdStr);

        HttpSession session = req.getSession(false);
        Map<Integer, Integer> cart = null;
        if (session == null) {
            resp.sendRedirect("/login.jsp");
            return;
        }
        cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart != null) {
            
            Integer existingQuantity = cart.get(productId);
            if (existingQuantity == null) {
                existingQuantity = quantity;
            } else {
                existingQuantity += quantity;
            }
            cart.put(productId, existingQuantity);
            session.setAttribute("cart", cart);
        } else {
            cart = new HashMap<>();
            cart.put(productId, quantity);
            session.setAttribute("cart", cart);
        }
        resp.sendRedirect("/index.jsp");

    }
}