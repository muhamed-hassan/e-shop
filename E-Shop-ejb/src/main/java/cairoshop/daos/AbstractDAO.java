package cairoshop.daos;

import cairoshop.configs.HibernateUtil;
import com.cairoshop.logger.GlobalLogger;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public abstract class AbstractDAO<T> {

    private Class<T> entity;
    private String entityName;
    
    public AbstractDAO(Class<T> entity) {
        this.entity = entity;
        entityName = entity.getSimpleName();
    }

    //**************************************************************************
    public GlobalLogger getLogger() {
        return GlobalLogger.getInstance();
    }

    //**************************************************************************
    public Integer insert(T instance) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Integer id = null;

        try {

            session.getTransaction().begin();

            id = (Integer) session.save(instance);

            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();

            StringBuilder errMsg = new StringBuilder();
            errMsg.append("### insertion failed for entity -> ");
            errMsg.append(entityName);
            errMsg.append(" ###");

            getLogger()
                    .doLogging(
                            Level.ERROR, 
                            errMsg.toString(), 
                            ex,
                            this.getClass());
            return null;
        } finally {
            session.close();
        }

        return id;
    }

    //**************************************************************************
    public boolean update(T instance) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {

            session.getTransaction().begin();

            session.update(instance);

            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();

            StringBuilder errMsg = new StringBuilder();
            errMsg.append("### update failed for entity -> ");
            errMsg.append(entityName);
            errMsg.append(" ###");

            getLogger()
                    .doLogging(
                            Level.ERROR, 
                            errMsg.toString(), 
                            ex,
                            this.getClass());
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    //**************************************************************************
    public boolean delete(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean result = false;

        try {
            session.getTransaction().begin();

            StringBuilder query = new StringBuilder();
            query.append("UPDATE " + entityName + " obj ");
            query.append("SET obj." + ("User".equals(entityName) ? "active" : "notDeleted") + "=:flag ");
            query.append("WHERE obj.id=:id");

            result = (session
                    .createQuery(query.toString())
                    .setParameter("flag", false)
                    .setParameter("id", id)
                    .executeUpdate()) == 1;

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();

            StringBuilder errMsg = new StringBuilder();
            errMsg.append("### delete failed for entity -> ");
            errMsg.append(entityName);
            errMsg.append(" ###");

            getLogger()
                    .doLogging(
                            Level.ERROR, 
                            errMsg.toString(), 
                            ex,
                            this.getClass());
            return false;
        } finally {
            session.close();
        }

        return result;

    }

    //**************************************************************************
    public Integer getCount() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = 0L;

        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT COUNT(obj) ");
            query.append("FROM " + entityName + " obj ");
            query.append("WHERE obj.notDeleted=:flag");

            count = (Long) session
                    .createQuery(query.toString())
                    .setParameter("flag", true)
                    .uniqueResult();
        } catch (Exception ex) {
            getLogger()
                    .doLogging(
                            Level.ERROR, 
                            "getCount failed" + " | " + entityName + "::getCount()", 
                            ex,
                            this.getClass());
            return -1;
        } finally {
            session.close();
        }

        return (Integer) count.intValue();
    }
    
    //**************************************************************************
    public List<T> getAll(Integer startPosition) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<T> data = null;

        try {
            data = session
                    .createCriteria(entity)
                    .add(Restrictions.eq("notDeleted", true))
                    .setMaxResults(5)
                    .setFirstResult(startPosition)
                    .list();
        } catch (Exception ex) {
            getLogger()
                    .doLogging(
                            Level.ERROR, 
                            "Data retrieval failed" + " | " + entityName + "::getAll(startPosition)", 
                            ex,
                            this.getClass());
            return null;
        } finally {
            session.close();
        }

        return data;
    }

    //**************************************************************************
    public List<T> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<T> data = null;

        try {
            data = session
                    .createCriteria(entity)
                    .add(Restrictions.eq("notDeleted", true))
                    .list();

        } catch (Exception ex) {
            getLogger()
                    .doLogging(
                            Level.ERROR, 
                            "Data retrieval failed" + " | " + entityName + "::getAll()", 
                            ex,
                            this.getClass());
            return null;
        } finally {
            session.close();
        }

        return data;
    }
}
