package cairoshop.repositories;

import cairoshop.configs.HibernateConfigurator;
import cairoshop.helpers.msgs.RepositoryMessage;
import cairoshop.repositories.exceptions.*;
import cairoshop.repositories.specs.CriteriaQuerySpecs;
import com.demo.GlobalLogger;
import java.util.List;
import javax.inject.Inject;
import org.apache.logging.log4j.Level;
import org.hibernate.*;
import org.hibernate.criterion.Projections;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public abstract class BaseRepository<T>
        implements AbstractRepository<T>, PagableRepository<T> {

    @Inject
    private HibernateConfigurator hibernateConfigurator;

    private Class<T> entity;
    private String entityName;

    @Inject
    private GlobalLogger globalLogger;

    public BaseRepository(Class<T> entity) {
        this.entity = entity;
        this.entityName = entity.getSimpleName();
    }

    //**************************************************************************
    protected GlobalLogger getGlobalLogger() {
        return globalLogger;
    }

    protected HibernateConfigurator getHibernateConfigurator() {
        return hibernateConfigurator;
    }

    //**************************************************************************
    @Override
    public void add(T entity)
            throws InsertionException {
        Session session = hibernateConfigurator.getSessionFactory().openSession();

        try {

            session.getTransaction().begin();

            session.save(entity);

            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_ADDING,
                            getClass(),
                            ex
                    );
            throw new InsertionException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_ADDING, ex);
        } finally {
            session.close();
        }

    }

    @Override
    public void update(T entity)
            throws ModificationException {
        Session session = hibernateConfigurator.getSessionFactory().openSession();

        try {

            session.getTransaction().begin();

            session.update(entity);

            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_MODIFICATION,
                            getClass(),
                            ex
                    );
            throw new ModificationException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_MODIFICATION, ex);
        } finally {
            session.close();
        }

    }

    @Override
    public void remove(int id)
            throws DeletionException {
        Session session = hibernateConfigurator.getSessionFactory().openSession();

        try {

            session.getTransaction().begin();

            StringBuilder hql = new StringBuilder()
                    .append("UPDATE " + entityName + " obj ")
                    .append("SET obj." + ("User".equals(entityName) ? "active" : "notDeleted") + "=:flag ")
                    .append("WHERE obj.id=:id");

            session
                    .createQuery(hql.toString())
                    .setParameter("flag", false)
                    .setParameter("id", id)
                    .executeUpdate();

            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_REMOVAL,
                            getClass(),
                            ex
                    );
            throw new DeletionException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_REMOVAL, ex);
        } finally {
            session.close();
        }
    }

    @Override
    public T find(CriteriaQuerySpecs querySpecs)
            throws RetrievalException {
        
        T entity = null;
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            Criteria criteria = session.createCriteria(this.entity);
            entity = (T) querySpecs
                    .build(criteria)
                    .uniqueResult();

        } catch (Exception ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL,
                            getClass(),
                            ex
                    );
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } 

        return entity;
    }

    @Override
    public List<T> findAll(CriteriaQuerySpecs querySpecs)
            throws RetrievalException {
        
        List<T> data = null;
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            Criteria criteria = session.createCriteria(entity);
            data = querySpecs
                    .build(criteria)
                    .list();

        } catch (Exception ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL,
                            getClass(),
                            ex
                    );
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } 

        return data;
    }

    @Override
    public List<T> findAll(CriteriaQuerySpecs querySpecs, int startPosition)
            throws RetrievalException {
        
        List<T> data = null;
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            Criteria criteria = session.createCriteria(entity);
            data = querySpecs
                    .build(criteria)
                    .setFirstResult(startPosition)
                    .setMaxResults(5)
                    .list();

        } catch (Exception ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL,
                            getClass(),
                            ex
                    );
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } 

        return data;
    }

    @Override
    public int getCount(CriteriaQuerySpecs querySpecs) 
            throws RetrievalException {
        
        Integer count = null;
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {
            
            Criteria criteria = session.createCriteria(entity);
            count = ((Long) querySpecs
                        .build(criteria)
                        .setProjection(Projections.rowCount())
                        .uniqueResult())
                    .intValue();
            
        } catch (Exception ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_COMPUTING_ENTITY_COUNT,
                            getClass(),
                            ex
                    );
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_COMPUTING_ENTITY_COUNT, ex);
        } 

        return count;
    }

}
