package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * Servlet controller for managing the shopping cart functionality.
 *
 * <p>This controller handles HTTP GET and POST requests related to 
 * the shopping cart, including displaying the cart and adding items 
 * to it.</p>
 */
public class CartController extends HttpServlet {

    /**
     * Handles the HTTP GET request to display the shopping cart.
     *
     * <p>This method forwards the request to the "cart.jsp" page for 
     * rendering the current state of the cart.</p>
     *
     * @param req  The HttpServletRequest object that contains the request 
     *             data.
     * @param resp The HttpServletResponse object that contains the response 
     *             data.
     * @throws ServletException If an error occurs during the request 
     *                          dispatching.
     * @throws IOException      If an input or output error occurs during 
     *                          the forwarding process.
     */
    public static void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

    /**
     * Handles the HTTP POST request to add items to the shopping cart.
     *
     * <p>This method retrieves the product ID and quantity from the request, 
     * updates the cart stored in the session, and redirects to the index page.</p>
     *
     * @param req  The HttpServletRequest object that contains the request 
     *             data.
     * @param resp The HttpServletResponse object that contains the response 
     *             data.
     * @throws ServletException If an error occurs during the request 
     *                          processing.
     * @throws IOException      If an input or output error occurs during 
     *                          the redirection.
     */
    public static void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productIdStr = req.getParameter("productId");
        String quantityStr = req.getParameter("quantity");

        Integer productId = Integer.valueOf(productIdStr);
        Integer quantity = Integer.valueOf(quantityStr);

        System.out.println(productIdStr);

        HttpSession session = req.getSession(false);
        Map<Integer, Integer> cart = null;
        if (session == null || session.getAttribute("user") == null) {
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