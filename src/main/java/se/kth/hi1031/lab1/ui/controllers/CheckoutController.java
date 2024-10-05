package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.hi1031.lab1.bo.service.PermissionException;
import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.bo.service.order.OrderService;
import se.kth.hi1031.lab1.ui.dto.product.ProductDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckoutController extends HttpServlet {
    public static void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String deliveryAddress = req.getParameter("deliveryAddress");
        HttpSession session = req.getSession();
        if (deliveryAddress == null || deliveryAddress.isEmpty()) {
            session.setAttribute("error", "No delivery address provided");
            resp.sendRedirect("/customer/checkout");
            return;
        }

        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart == null) {
            session.setAttribute("error", "Cart is empty");
            resp.sendRedirect("/customer/checkout");
            return;
        }

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            session.setAttribute("error", "Not logged in");
            resp.sendRedirect("/customer/checkout");
            return;
        }

        List<ProductDTO> products = cart.keySet().stream()
                .map(id -> new ProductDTO(id, null, null, 0, cart.get(id), false, null, null, null))
                .toList();
        // TODO: create a better way to make a order
        try {
            OrderService.createOrder(user, user, deliveryAddress, products);
        } catch (PermissionException | ServiceException e) {
            session.setAttribute("error", e.getMessage());
            resp.sendRedirect("/customer/checkout");
            return;
        }
        resp.sendRedirect("/customer/checkout/done");
    }
}