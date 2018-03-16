package cairoshop.web.filters;

import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@WebFilter(urlPatterns = {"/*"}, filterName = "requestSrcTracker")
public class RequestSrcTrackerFilter implements AbstractFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (!httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {
            HttpSession session = httpRequest.getSession();
            session.setAttribute("lastRequestSource", httpRequest.getRequestURI().replace(httpRequest.getContextPath(), ""));
        }

        chain.doFilter(request, response);
    }

}
