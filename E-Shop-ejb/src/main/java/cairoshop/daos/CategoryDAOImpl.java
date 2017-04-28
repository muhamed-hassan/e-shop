package cairoshop.daos;

import cairoshop.entities.*;
import com.cairoshop.logger.GlobalLogger;
import java.util.*;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.apache.logging.log4j.Level;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
public class CategoryDAOImpl implements CategoryDAO {

    private GlobalLogger logger;

    @PostConstruct
    public void init() {
        logger = GlobalLogger.getInstance();
    }

    @Override
    public boolean insert(Category category) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.getTransaction().begin();

            session.persist(category);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            logger.doLogging(Level.ERROR, "Category insertion failed" + " | " + CategoryDAOImpl.class.getName() + "::insert(category)", ex);
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    @Override
    public boolean update(Category category) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            session.getTransaction().begin();

            session.merge(category);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            logger.doLogging(Level.ERROR, "Category update failed" + " | " + CategoryDAOImpl.class.getName() + "::update(category)", ex);
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    @Override
    public boolean delete(Category category) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean result = false;

        try {
            session.getTransaction().begin();

            int affectedRows = session.createQuery("UPDATE Category c SET c.notDeleted=:flag WHERE c.Id=:catID")
                    .setParameter("flag", false)
                    .setParameter("catID", category.getId())
                    .executeUpdate();
            result = affectedRows == 1;

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            logger.doLogging(Level.ERROR, "Category delete failed" + " | " + CategoryDAOImpl.class.getName() + "::delete(category)", ex);
            return false;
        } finally {
            session.close();
        }

        return result;

    }

    @Override
    public List<Category> getAll(Integer startPosition) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Category> categories = null;

        try {
            categories = session
                    .createCriteria(Category.class)
                    .add(Restrictions.eq("notDeleted", true))
                    .setMaxResults(5)
                    .setFirstResult(startPosition)
                    .list();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Category retrieval failed" + " | " + CategoryDAOImpl.class.getName() + "::getAll(startPosition)", ex);
            return null;
        } finally {
            session.close();
        }

        return categories;
    }

    @Override
    public Integer getCount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;

        try {
            count = (Long) session
                    .getNamedQuery("Category.count")
                    .setParameter("flag", true)
                    .uniqueResult();
        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Category getCount failed" + " | " + CategoryDAOImpl.class.getName() + "::getCount()", ex);
            return -1;
        } finally {
            session.close();
        }

        return ((count == null) ? null : (Integer) count.intValue());
    }

    @Override
    public List<Category> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Category> categories = null;

        try {
            categories = (List<Category>) session
                    .getNamedQuery("Category.findAll")
                    .setParameter("flag", true)
                    .list();

        } catch (Exception ex) {
            logger.doLogging(Level.ERROR, "Category retrieval failed" + " | " + CategoryDAOImpl.class.getName() + "::getAll()", ex);
            return null;
        } finally {
            session.close();
        }

        return categories;
    }

}
