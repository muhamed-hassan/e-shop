package cairoshop.daos;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class HibernateUtil
{

    private static final SessionFactory sessionFactory;

    static
    {
        try
        {
            sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();            
        }
        catch (Throwable ex)
        {            
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
}
