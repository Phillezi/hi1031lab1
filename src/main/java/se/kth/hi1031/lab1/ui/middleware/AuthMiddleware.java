package se.kth.hi1031.lab1.ui.middleware;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import se.kth.hi1031.lab1.ui.dto.user.PermissionDTO;
import se.kth.hi1031.lab1.ui.dto.user.RoleDTO;
import se.kth.hi1031.lab1.ui.dto.user.UserDTO;

import java.io.IOException;
/**
 * Middleware for checking auth on UI layer.
 */
@WebFilter(urlPatterns = {"/components/*", "/admin/*", "/warehouse/*", "/customer/*"})
public class AuthMiddleware implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        UserDTO user = null;
        if (session != null) {
            user = (UserDTO) session.getAttribute("user");
        }
        if (user == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.sendRedirect("/errors/401.jsp");
            return;
        }
        String requestURI = httpRequest.getRequestURI();
        if (requestURI.startsWith("/admin")) {
            if (user.getRoles()
                    .stream()
                    .noneMatch(
                            (RoleDTO r)
                                    ->
                                    "admin".equalsIgnoreCase(r.getName())
                    )
            ) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.sendRedirect("/errors/403.jsp");
            }
        } else if (requestURI.startsWith("/warehouse")) {
            if (user.getRoles()
                    .stream()
                    .noneMatch(
                            (RoleDTO r)
                                    ->
                                    "warehouse".equalsIgnoreCase(r.getName())
                    ) && user.getPermissions()
                    .stream()
                    .noneMatch(
                            (PermissionDTO p)
                                    ->
                                    "view_orders".equalsIgnoreCase(p.getName())
                    )
            ) {
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResponse.sendRedirect("/errors/403.jsp");
            }
        } else if (requestURI.startsWith("/components")) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.sendRedirect("/errors/403.jsp");
        }

        // user is counted as customer despite not having the customer role,
        // they are implicitly customers
        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
