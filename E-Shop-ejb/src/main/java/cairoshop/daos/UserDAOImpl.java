package cairoshop.daos;

import cairoshop.entities.*;
import java.util.*;
import javax.inject.*;
import org.hibernate.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Singleton
public class UserDAOImpl implements UserDAO
{

    @Override
    public boolean update(Integer userID, boolean flag)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        int affectedRows = -1;

        try
        {
            session.getTransaction().begin();

            affectedRows = session
                    .createQuery("UPDATE Customer c SET c.active=:flag WHERE c.id=:userID")
                    .setParameter("flag", flag)
                    .setParameter("userID", userID)
                    .executeUpdate();

            session.getTransaction().commit();
        }
        catch (Exception ex)
        {
            session.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        }
        finally
        {
            session.close();
        }

        return (affectedRows == 1);
    }

    @Override
    public List<Customer> getAll(Integer startPosition)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Customer> customers = null;

        try
        {
            customers = (List<Customer>) session
                    .getNamedQuery("Customer.findAll")
                    .setFirstResult(startPosition)
                    .setMaxResults(5)
                    .list();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null;
        }
        finally
        {
            session.close();
        }

        return customers;
    }

    @Override
    public Object find(String email, String password)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        User user;

        try
        {
            user = (User) session.getNamedQuery("User.find")
                    .setParameter("email", email)
                    .setParameter("pwd", password)
                    .setParameter("flag", true)
                    .uniqueResult();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return null; //exception occured
        }
        finally
        {
            session.close();
        }

        if (user == null)
        {
            return "notFound"; //no such user found            
        }

        return user;
    }

    @Override
    public Object insert(Customer customer)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Customer c = null;

        try
        {
            session.getTransaction().begin();

            session.persist(customer);
            session.flush();

            c = (Customer) session.getNamedQuery("User.find")
                    .setParameter("email", customer.getMail())
                    .setParameter("pwd", customer.getPassword())
                    .setParameter("flag", true)
                    .uniqueResult();

            session.getTransaction().commit();
        }
        catch (Exception ex)
        {
            session.getTransaction().rollback();

            if (ex instanceof org.hibernate.exception.ConstraintViolationException)
            {
                return "Duplicated values - please review your entries again.";
            }

            ex.printStackTrace();
            return null;
        }
        finally
        {
            session.close();
        }

        return c;
    }

    @Override
    public Integer getCount()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;

        try
        {
            count = (Long) session.getNamedQuery("Customer.count").uniqueResult();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return -1;
        }
        finally
        {
            session.close();
        }

        return (Integer) count.intValue();
    }
}
