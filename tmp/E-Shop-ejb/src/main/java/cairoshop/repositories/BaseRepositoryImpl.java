package cairoshop.repositories;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.Level;
import org.hibernate.Session;

import com.cairoshop.GlobalLogger;

import cairoshop.configs.HibernateConfigurator;
import cairoshop.helpers.msgs.RepositoryMessage;
import cairoshop.repositories.exceptions.DeletionException;
import cairoshop.repositories.exceptions.InsertionException;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.BaseRepository;
import cairoshop.repositories.specs.QuerySpecs;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class BaseRepositoryImpl<T> implements BaseRepository<T> {

    @Inject
    private HibernateConfigurator hibernateConfigurator;

    private Class<T> entity;
    private String entityName;

    @Inject
    private GlobalLogger globalLogger;

    public BaseRepositoryImpl(Class<T> entity) {
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
    public Integer add(T entity) throws InsertionException {
        Integer id = null;
        Session session = hibernateConfigurator.getSessionFactory().openSession();
        try {

            session.getTransaction().begin();
            id = (Integer) session.save(entity);
            session.getTransaction().commit();

        } catch (Exception ex) {
            session.getTransaction().rollback();
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_ADDING, getClass(), ex);
            throw new InsertionException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_ADDING, ex);
        } finally {
            session.close();
        }
        return id;
    }

    @Override
    public void update(Map<String, Object> fields) throws ModificationException {
        EntityManager entityManager = null;
        Session session = hibernateConfigurator.getSessionFactory().openSession();
        try {
            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaUpdate<T> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(entity);
            Root<T> root = criteriaUpdate.from(entity);
            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                criteriaUpdate.set(root.get(entry.getKey()), entry.getValue());
            }
            criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), fields.get("id")));

            entityManager.getTransaction().begin();
            int affectedRows = entityManager.createQuery(criteriaUpdate).executeUpdate();
            session.getTransaction().commit();

            if (affectedRows == 0) {
                throw new ModificationException("No rows updated");
            }
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
        EntityManager entityManager = null;
        Session session = hibernateConfigurator.getSessionFactory().openSession();
        try {
            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaUpdate<T> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(entity);
            Root<T> root = criteriaUpdate.from(entity);

            criteriaUpdate.set(root.get("active"), false)
                            .where(criteriaBuilder.equal(root.get("id"), id));
            entityManager.getTransaction().begin();
            int affectedRows = entityManager.createQuery(criteriaUpdate).executeUpdate();
            entityManager.getTransaction().commit();
            if (affectedRows == 0) {
                throw new DeletionException("No rows deleted");
            }
        } catch (Exception ex) {
            session.getTransaction().rollback();
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_REMOVAL, getClass(), ex);
            throw new DeletionException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_REMOVAL, ex);
        } finally {
            session.close();
        }
    }

    @Override
    public Object[] find(QuerySpecs querySpecs) throws RetrievalException {
        EntityManager entityManager = null;
        Object[] record = null;
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<T> root = criteriaQuery.from(entity);
            criteriaQuery = querySpecs.build(criteriaQuery, criteriaBuilder, root);
            record = entityManager.createQuery(criteriaQuery)
                .getSingleResult();

        } catch (Exception ex) {
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, getClass(), ex);
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return record;
    }

    @Override
    public List<Object[]> findAll(QuerySpecs querySpecs) throws RetrievalException {
        EntityManager entityManager = null;
        List<Object[]> records = null;
        
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<T> root = criteriaQuery.from(this.entity);
            criteriaQuery.multiselect(root.get("id"), root.get("name"));
            criteriaQuery = querySpecs.build(criteriaQuery, criteriaBuilder, root);
            records = entityManager.createQuery(criteriaQuery).getResultList();
                                 
        } catch (Exception ex) {
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, getClass(), ex);
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return records;
    }

    @Override
    public List<Object[]> findAll(QuerySpecs querySpecs, int startPosition) throws RetrievalException {
        EntityManager entityManager = null;
        List<Object[]> records = null;
        
        try (Session session = hibernateConfigurator.getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<T> root = criteriaQuery.from(this.entity);
            criteriaQuery.multiselect(root.get("id"), root.get("name"));
            criteriaQuery = querySpecs.build(criteriaQuery, criteriaBuilder, root);
            records = entityManager.createQuery(criteriaQuery)
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
        return records;
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
