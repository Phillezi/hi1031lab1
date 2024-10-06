package se.kth.hi1031.lab1.ui.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import se.kth.hi1031.lab1.bo.service.PermissionException;
import se.kth.hi1031.lab1.bo.service.ServiceException;
import se.kth.hi1031.lab1.bo.service.order.StatusService;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

/**
 * UI Controller that handles actions on the warehouse pages.
 * Converts from UI related data formats into DTO classes that are handled by the service classes.
 */
public class WarehouseController extends HttpServlet {

    /**
     * Handles changing status of orders. Such as setting an order to be "packed" or "shipped".
     */
    public static void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        UserDTO user = null;
        if (session != null) {
            user = (UserDTO) session.getAttribute("user");
        }
        if (user != null) {
            String orderIdStr = req.getParameter("orderId");
            String status = req.getParameter("status");

            Integer orderId = null;
            if (orderIdStr != null) {
                orderId = Integer.valueOf(orderIdStr);
            }
            if (status != null && orderId != null) {
                try {
                    StatusService.setOrderStatus(orderId, status, user);
                } catch (PermissionException | ServiceException e) {
                    session.setAttribute("error", e.getMessage());
                }
                resp.sendRedirect(req.getHeader("Referer"));
            }
        }
    }
}