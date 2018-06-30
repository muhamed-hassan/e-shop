package cairoshop.repositories;

import cairoshop.entities.*;
import cairoshop.helpers.msgs.RepositoryMessage;
import cairoshop.repositories.exceptions.*;
import cairoshop.repositories.interfaces.UserRepository;
import cairoshop.repositories.specs.CriteriaQuerySpecs;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import org.apache.logging.log4j.Level;
import org.hibernate.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class UserRepositoryImpl
        extends BaseRepository<User>
        implements UserRepository {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public List<User> findAll(CriteriaQuerySpecs querySpecs, int startPosition)
            throws RetrievalException {
        
        EntityManager entityManager = null;
        List<User> data = null;
        try (Session session = getHibernateConfigurator().getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);			
	    Root<User> root = criteriaQuery.from(User.class);
                                 
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
        try (Session session = getHibernateConfigurator().getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder(); 
            CriteriaQuery<Long> criteriaQuery=criteriaBuilder.createQuery(Long.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            count = entityManager
                        .createQuery(criteriaQuery.select(criteriaBuilder.count(root)))
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

    @Override
    public void update(Customer customer, Product favoriteProduct)
            throws ModificationException {
        Session session = getHibernateConfigurator().getSessionFactory().openSession();

        try {

            session.getTransaction().begin();

            session.refresh(customer);
            customer.getFavoriteProducts().add(favoriteProduct);

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

}
