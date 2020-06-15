package cairoshop.web.filters;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cairoshop.entities.Admin;
import cairoshop.entities.Customer;
import cairoshop.entities.User;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@WebFilter(urlPatterns = {"/customer/*", "/admin/*", "/login-pg.jsf"}, filterName = "authFilter")
public class AuthenticationFilter implements BaseFilter {

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
                        httpRequest.getRequestDispatcher("/WEB-INF/utils/forbidden-access.jsf")
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
