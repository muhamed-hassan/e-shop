package cairoshop.daos;

import cairoshop.entities.*;
import java.util.*;
import javax.inject.*;
import org.hibernate.*;
import org.hibernate.criterion.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Singleton
public class CategoryDAOImpl implements CategoryDAO
{

    @Override
    public boolean insert(Category category)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try
        {
            session.getTransaction().begin();

            session.persist(category);

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
    public boolean update(Category category)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try
        {
            session.getTransaction().begin();

            session.merge(category);

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
    public boolean delete(Category category)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean result = false;

        try
        {
            session.getTransaction().begin();

            int affectedRows = session.createQuery("UPDATE Category c SET c.notDeleted=:flag WHERE c.Id=:catID")
                    .setParameter("flag", false)
                    .setParameter("catID", category.getId())
                    .executeUpdate();
            result = affectedRows == 1;

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

        return result;

    }

    @Override
    public List<Category> getAll(Integer startPosition)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Category> categories = null;

        try
        {
            categories = session
                    .createCriteria(Category.class)
                    .add(Restrictions.eq("notDeleted", true))
                    .setMaxResults(5)
                    .setFirstResult(startPosition)
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

        return categories;
    }

    @Override
    public Integer getCount()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;

        try
        {
            count = (Long) session
                    .getNamedQuery("Category.count")
                    .setParameter("flag", true)
                    .uniqueResult();
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

        return ((count == null) ? null : (Integer) count.intValue());
    }

    @Override
    public List<Category> getAll()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Category> categories = null;

        try
        {
            categories = (List<Category>) session
                    .getNamedQuery("Category.findAll")
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

        return categories;
    }

}
