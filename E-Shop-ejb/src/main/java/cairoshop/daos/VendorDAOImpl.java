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
public class VendorDAOImpl implements VendorDAO
{

    @Override
    public boolean insert(Vendor vendor)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try
        {
            session.getTransaction().begin();

            session.save(vendor);

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

        return true;
    }

    @Override
    public boolean update(Vendor vendor)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try
        {
            session.getTransaction().begin();

            session.merge(vendor);

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

        return true;

    }

    @Override
    public boolean delete(Vendor vendor)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        int affectedRows = -1;

        try
        {
            session.getTransaction().begin();

            affectedRows = session.createQuery("UPDATE Vendor v SET v.notDeleted=:flag WHERE v.Id=:vID")
                    .setParameter("flag", false)
                    .setParameter("vID", vendor.getId())
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
    public List<Vendor> getAll(Integer startPosition)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Vendor> vendors = null;

        try
        {
            vendors = (List<Vendor>) session
                    .getNamedQuery("Vendor.findAll")
                    .setParameter("flag", true)
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

        return vendors;
    }

    @Override
    public Integer getCount()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;

        try
        {
            count = (Long) session.getNamedQuery("Vendor.count").setParameter("flag", true).uniqueResult();
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

    @Override
    public List<Vendor> getAll()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Vendor> vendors = null;

        try
        {
            vendors = (List<Vendor>) session
                    .getNamedQuery("Vendor.findAll")
                    .setParameter("flag", true)
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

        return vendors;
    }

}
