package cairoshop.repositories;

import cairoshop.configs.HibernateConfigurator;
import cairoshop.repositories.exceptions.*;
import cairoshop.repositories.specs.CriteriaQuerySpecs;
import java.util.List;
import javax.inject.Inject;
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

    public BaseRepository(Class<T> entity) {
        this.entity = entity;
        this.entityName = entity.getSimpleName();
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
            ex.printStackTrace();
            throw new InsertionException("" , ex);
        } finally {
            session.close();
        }
        
    }

    @Override
    public void update(T entity) throws ModificationException {
        Session session = hibernateConfigurator.getSessionFactory().openSession();

        session.getTransaction().begin();

        session.update(entity);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void remove(int id) throws DeletionException {
        Session session = hibernateConfigurator.getSessionFactory().openSession();

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

        session.close();
    }

    @Override
    public T find(CriteriaQuerySpecs querySpecs) throws Exception {
        Session session = hibernateConfigurator.getSessionFactory().openSession();
        
        Criteria criteria = session.createCriteria(entity);
        T entity  = (T) querySpecs
                            .build(criteria)
                            .uniqueResult();
        
        session.close();
        
        return entity;
    }

    @Override
    public List<T> findAll(CriteriaQuerySpecs querySpecs) throws Exception {
        Session session = hibernateConfigurator.getSessionFactory().openSession();
                
        Criteria criteria = session.createCriteria(entity);
        List<T> data  = querySpecs
                            .build(criteria)
                            .list();
        
        session.close();
        
        return data;
    }
    
    

    

    @Override
    public List<T> findAll(CriteriaQuerySpecs querySpecs, int startPosition) {
        Session session = hibernateConfigurator.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(entity);
        List<T> data  = querySpecs
                            .build(criteria)                    
                    .setFirstResult(startPosition)
                    .setMaxResults(5)
                    .list();
        
        session.close();
        
        return data;
    }

    @Override
    public int getCount(CriteriaQuerySpecs querySpecs) throws Exception {
        Session session = hibernateConfigurator.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(entity);
        Integer count  = null;
        
        try { 
            count= ((Long) querySpecs
                            .build(criteria).setProjection(Projections.rowCount())
                    .uniqueResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }        
        
        session.close();
        
        return count;
    }
    
}
