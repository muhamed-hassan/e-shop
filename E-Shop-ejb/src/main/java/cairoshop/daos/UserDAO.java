package cairoshop.daos;

import cairoshop.configs.HibernateUtil;
import cairoshop.entities.*;
import java.util.*;
import javax.annotation.ManagedBean;
import org.apache.logging.log4j.Level;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
public class UserDAO extends AbstractDAO<User> {

    public UserDAO() {
        super(User.class);
    }

    public Customer insert(Customer customer) {
        Integer id = super.insert(customer);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Customer c = null;

        try {

            c = (Customer) session
                    .createCriteria(Customer.class)
                    .add(Restrictions.eq("id", id))
                    .uniqueResult();

        } catch (Exception ex) {
            getLogger().doLogging(Level.ERROR, "### retrival failed for entity -> Customer ###", ex);
            return null;
        } finally {
            session.close();
        }

        return c;
    }

    //**************************************************************************
    @Override
    public Integer getCount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;

        try {
            count = (Long) session.getNamedQuery("Customer.count").uniqueResult();
        } catch (Exception ex) {
            getLogger().doLogging(Level.ERROR, "User getCount failed" + " | " + UserDAO.class.getName() + "::getCount()", ex);
            return -1;
        } finally {
            session.close();
        }

        return (Integer) count.intValue();
    }

    //**************************************************************************
    @Override
    public List<User> getAll(Integer startPosition) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> customers = null;

        try {
            customers = /*(List<Customer>)*/ session
                    .getNamedQuery("Customer.findAll")
                    .setFirstResult(startPosition)
                    .setMaxResults(5)
                    .list();
        } catch (Exception ex) {
            getLogger().doLogging(Level.ERROR, "Customers retrieval failed" + " | " + UserDAO.class.getName() + "::getAll(startPosition)", ex);
            return null;
        } finally {
            session.close();
        }

        return customers;
    }

    //**************************************************************************
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
            getLogger().doLogging(Level.ERROR, "User retrieval failed" + " | " + UserDAO.class.getName() + "::find(email, password)", ex);
            return null; //exception occured
        } finally {
            session.close();
        }

        if (user == null) {
            return "notFound"; //no such user found            
        }

        return user;
    }

}
