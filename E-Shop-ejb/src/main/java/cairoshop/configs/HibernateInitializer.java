package cairoshop.configs;

import javax.enterprise.context.*;
import javax.enterprise.event.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ApplicationScoped
public class HibernateInitializer {

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        HibernateUtil.getSessionFactory();
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object dest) {
        HibernateUtil.getSessionFactory().close();
    }
}
