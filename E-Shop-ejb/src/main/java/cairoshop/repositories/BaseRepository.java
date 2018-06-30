package cairoshop.repositories;

import cairoshop.repositories.interfaces.*;
import cairoshop.configs.HibernateConfigurator;
import cairoshop.helpers.msgs.RepositoryMessage;
import cairoshop.repositories.exceptions.*;
import cairoshop.repositories.specs.CriteriaQuerySpecs;
import com.demo.GlobalLogger;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.criteria.Root;
import org.apache.logging.log4j.Level;
import org.hibernate.*;

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
        
        EntityManager entityManager = null;
        T entity = null;
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entity);			
            Root<T> root = criteriaQuery.from(this.entity);			
            
            entity = entityManager
                        .createQuery(criteriaQuery.where(querySpecs.build(criteriaBuilder, root)))
                        .getSingleResult();

        } catch (Exception ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL,
                            getClass(),
                            ex
                    );
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } finally {
            if( entityManager != null ) {
                entityManager.close();
            }
        }

        return entity;
    }

    @Override
    public List<T> findAll(CriteriaQuerySpecs querySpecs)
            throws RetrievalException {
        
        EntityManager entityManager = null;
        List<T> data = null;
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entity);			
            Root<T> root = criteriaQuery.from(this.entity);
                                 
            data = entityManager
                    .createQuery(criteriaQuery.where(querySpecs.build(criteriaBuilder, root)))
                    .getResultList();
                                 
        } catch (Exception ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL,
                            getClass(),
                            ex
                    );
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } finally {
            if( entityManager != null ) {
                entityManager.close();
            }
        }

        return data;
    }

    @Override
    public List<T> findAll(CriteriaQuerySpecs querySpecs, int startPosition)
            throws RetrievalException {
        
        EntityManager entityManager = null;
        List<T> data = null;
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entity);			
            Root<T> root = criteriaQuery.from(this.entity);
                                 
            data = entityManager
                    .createQuery(criteriaQuery.where(querySpecs.build(criteriaBuilder, root)))
                    .setFirstResult(startPosition)
                    .setMaxResults(5)
                    .getResultList();

        } catch (Exception ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL,
                            getClass(),
                            ex
                    );
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } finally {
            if( entityManager != null ) {
                entityManager.close();
            }
        }

        return data;
    }

    @Override
    public int getCount(CriteriaQuerySpecs querySpecs) 
            throws RetrievalException {
        
        EntityManager entityManager = null;
        Integer count = null;
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);			
            Root<T> root = criteriaQuery.from(entity);
            count = entityManager
                        .createQuery(criteriaQuery.select(criteriaBuilder.count(root)).where(querySpecs.build(criteriaBuilder, root)))
                        .getSingleResult()
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
        } finally {
            if( entityManager != null ) {
                entityManager.close();
            }
        }

        return count;
    }

}
