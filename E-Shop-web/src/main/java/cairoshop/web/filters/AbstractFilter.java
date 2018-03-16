package cairoshop.web.filters;

import javax.servlet.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface AbstractFilter extends Filter {

    @Override
    default void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    default void destroy() { }
    
}
