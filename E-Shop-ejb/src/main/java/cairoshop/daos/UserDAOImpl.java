package cairoshop.daos;

import cairoshop.entities.*;
import com.cairoshop.logger.GlobalLogger;
import java.util.*;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import org.hibernate.*;
import org.apache.logging.log4j.Level;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
public class UserDAOImpl implements UserDAO {

    private GlobalLogger logger;

    @PostConstruct
    public void init() {
        logger = GlobalLogger.getInstance();
    }

    @Override
    public boolean update(Integer userID, boolean flag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        int affectedRows = -1;

        try {
            session.getTransaction().begin();

            affectedRows = session
                    .createQuery("UPDATE Customer c SET c.active=:flag WHERE c.id=:userID")
                    .setParameter("flag", flag)
                    .setParameter("userID", userID)
                    .executeUpdate();

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            logger.doLogging(Level.ERROR, "User update failed" + " | " + UserDAOImpl.class.getName() + "::update(userID, flag)", ex);
            return false;
        } finally {
            session.close();
        }

        return (affectedRows == 1);
    }

    @Override
    public List<Customer> getAll(Integer startPosition) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Customer> customers = null;

        try {
            customers = (List<Customer>) session
                    .getNamedQuery("Customer.findAll")
                    .setFirstResult(startPosition)
                    .setMaxResults(5)
                    .list();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "User retrieval failed" + " | " + UserDAOImpl.class.getName() + "::getAll(startPosition)", ex);
            return null;
        } finally {
            session.close();
        }

        return customers;
    }

    @Override
    public Object find(String email, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user;

        try {
            user = (User) session.getNamedQuery("User.find")
                    .setParameter("email", email)
                    .setParameter("pwd", password)
                    .setParameter("flag", true)
                    .uniqueResult();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "User retrieval failed" + " | " + UserDAOImpl.class.getName() + "::find(email, password)", ex);
            return null; //exception occured
        } finally {
            session.close();
        }

        if (user == null) {
            return "notFound"; //no such user found            
        }

        return user;
    }

    @Override
    public Object insert(Customer customer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Customer c = null;

        try {
            session.getTransaction().begin();

            session.persist(customer);
            session.flush();

            c = (Customer) session.getNamedQuery("User.find")
                    .setParameter("email", customer.getMail())
                    .setParameter("pwd", customer.getPassword())
                    .setParameter("flag", true)
                    .uniqueResult();

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();

            if (ex instanceof org.hibernate.exception.ConstraintViolationException) {
                return "Duplicated values - please review your entries again.";
            }

            logger.doLogging(Level.ERROR, "User insertion failed" + " | " + UserDAOImpl.class.getName() + "::insert(customer)", ex);
            return null;
        } finally {
            session.close();
        }

        return c;
    }

    @Override
    public Integer getCount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;

        try {
            count = (Long) session.getNamedQuery("Customer.count").uniqueResult();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "User getCount failed" + " | " + UserDAOImpl.class.getName() + "::getCount()", ex);
            return -1;
        } finally {
            session.close();
        }

        return (Integer) count.intValue();
    }
}
