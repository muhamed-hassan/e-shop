package cairoshop.filters;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@WebFilter(urlPatterns =
{
    "/customer/shop-home.jsf", "/admin/admin-home.jsf", "/login-pg.jsf"
})
public class AuthenticationFilter implements Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String requestPath = httpRequest.getServletPath();

        switch (requestPath)
        {
            case "/login-pg.jsf":
                if (session.getAttribute("currentUser") != null)
                {
                    httpRequest
                            .getRequestDispatcher("/WEB-INF/utils/forbidden.jsf")
                            .forward(request, response);
                }
                break;
            default:
                if (session.getAttribute("currentUser") == null)
                {
                    httpRequest
                            .getRequestDispatcher("/WEB-INF/utils/forbidden-access.jsf")
                            .forward(request, response);
                }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy()
    {

    }

}
