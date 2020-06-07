package cairoshop.repositories;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.hibernate.Session;

import com.cairoshop.GlobalLogger;

import cairoshop.configs.HibernateConfigurator;
import cairoshop.helpers.msgs.RepositoryMessage;
import cairoshop.repositories.exceptions.DeletionException;
import cairoshop.repositories.exceptions.InsertionException;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.AbstractRepository;
import cairoshop.repositories.interfaces.PagableRepository;
import cairoshop.repositories.specs.QuerySpecs;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public abstract class BaseRepository<T> implements AbstractRepository<T>, PagableRepository<T> {

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
    public void add(T entity) throws InsertionException {        
        Session session = hibernateConfigurator.getSessionFactory().openSession();
        try {

            session.getTransaction().begin();
            session.save(entity);
            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_ADDING, getClass(), ex);
            throw new InsertionException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_ADDING, ex);
        } finally {
            session.close();
        }
    }

    @Override
    public void update(T entity) throws ModificationException {        
        Session session = hibernateConfigurator.getSessionFactory().openSession();

        try {

            session.getTransaction().begin();
            session.update(entity);
            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_MODIFICATION, getClass(), ex);
            throw new ModificationException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_MODIFICATION, ex);
        } finally {
            session.close();
        }
    }

    @Override
    public void remove(int id) throws DeletionException {        
        Session session = hibernateConfigurator.getSessionFactory().openSession();

        try {

            session.getTransaction().begin();
            StringBuilder hql = new StringBuilder()
                    .append("UPDATE " + entityName + " obj ")
                    .append("SET obj." + ("User".equals(entityName) ? "active" : "notDeleted") + "=:flag ")
                    .append("WHERE obj.id=:id");
            session.createQuery(hql.toString())
                    .setParameter("flag", false)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_REMOVAL, getClass(), ex);
            throw new DeletionException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_REMOVAL, ex);
        } finally {
            session.close();
        }
    }

    @Override
    public T find(QuerySpecs querySpecs) throws RetrievalException {        
        EntityManager entityManager = null;
        T entity = null;
        
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entity);			
            Root<T> root = criteriaQuery.from(this.entity);           
            entity = (T) entityManager.createQuery(querySpecs.build(criteriaQuery, criteriaBuilder, root)).getSingleResult();

        } catch (Exception ex) {
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, getClass(), ex);
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public List<T> findAll(QuerySpecs querySpecs) throws RetrievalException {        
        EntityManager entityManager = null;
        List<T> data = null;
        
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entity);			
            Root<T> root = criteriaQuery.from(this.entity);                                 
            data = entityManager.createQuery(querySpecs.build(criteriaQuery, criteriaBuilder, root)).getResultList();
                                 
        } catch (Exception ex) {
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, getClass(), ex);
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return data;
    }

    @Override
    public List<T> findAll(QuerySpecs querySpecs, int startPosition) throws RetrievalException {        
        EntityManager entityManager = null;
        List<T> data = null;
        
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entity);            
            Root<T> root = criteriaQuery.from(this.entity);                                 
            data = entityManager.createQuery(querySpecs.build(criteriaQuery, criteriaBuilder, root))
                                    .setFirstResult(startPosition)
                                    .setMaxResults(5)
                                    .getResultList();
            
        } catch (Exception ex) {
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, getClass(), ex);
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return data;
    }

    @Override
    public int getCount(QuerySpecs querySpecs) throws RetrievalException {        
        EntityManager entityManager = null;
        Integer count = null;
        
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);			
            Root<T> root = criteriaQuery.from(entity);            
            if (querySpecs.getJoin() != null) {
                criteriaQuery = criteriaQuery.select(criteriaBuilder.count(root.join(querySpecs.getJoin().getJoinAttribute())));
            } else {
                criteriaQuery = criteriaQuery.select(criteriaBuilder.count(root));
            }            
            count = ((Long) entityManager.createQuery(querySpecs.build(criteriaQuery, criteriaBuilder, root)).getSingleResult()).intValue();
            
        } catch (Exception ex) {
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_COMPUTING_ENTITY_COUNT, getClass(), ex);
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_COMPUTING_ENTITY_COUNT, ex);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return count;
    }

}
