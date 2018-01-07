package cairoshop.web.filters;

import cairoshop.entities.Admin;
import cairoshop.entities.Customer;
import cairoshop.entities.User;
import java.io.*;
import javax.faces.application.ResourceHandler;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@WebFilter(urlPatterns
        = {
            "/customer/*", "/admin/*", "/login-pg.jsf"
        }, filterName = "authFilter")
public class AuthenticationFilter extends AbstractFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (!httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            HttpSession session = httpRequest.getSession();
            String requestPath = httpRequest.getServletPath();
            String lastRequestSource = (String) session.getAttribute("lastRequestSource");

            StringBuilder requestOrigin = new StringBuilder()
                    .append(httpRequest.getScheme()).append("://")
                    .append(httpRequest.getServerName()).append(":").append(httpRequest.getServerPort())
                    .append(httpRequest.getContextPath()).append(lastRequestSource);

            switch (requestPath) {
                case "/login-pg.jsf":
                    if (session.getAttribute("currentUser") != null) {
                        httpResponse.sendRedirect(requestOrigin.toString());
                    }
                    break;
                default:
                    if (session.getAttribute("currentUser") == null) {
                        httpRequest
                                .getRequestDispatcher("/WEB-INF/utils/forbidden-access.jsf")
                                .forward(request, response);
                    } else {
                        User currentUser = (User) session.getAttribute("currentUser");

                        if ((requestPath.startsWith("/customer") && currentUser instanceof Admin)
                                || (requestPath.startsWith("/admin") && currentUser instanceof Customer)) {
                            httpResponse.sendRedirect(requestOrigin.toString());
                        }
                    }
            }

        }

        chain.doFilter(request, response);
    }

}
