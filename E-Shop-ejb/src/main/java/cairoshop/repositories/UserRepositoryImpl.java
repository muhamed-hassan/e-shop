package cairoshop.repositories;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.hibernate.Session;

import cairoshop.entities.Customer;
import cairoshop.entities.Product;
import cairoshop.entities.User;
import cairoshop.helpers.msgs.RepositoryMessage;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.UserRepository;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public void update(Customer customer, Product favoriteProduct) throws ModificationException {        
        Session session = getHibernateConfigurator().getSessionFactory().openSession();

        try {

            session.getTransaction().begin();
            session.refresh(customer);
            customer.getFavoriteProducts().add(favoriteProduct);
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
    public List<Integer> findAll(int custId) throws RetrievalException {        
        EntityManager entityManager = null;
        List<Integer> likedProductsIds = null;
        
        try (Session session = getHibernateConfigurator().getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Integer> criteriaQuery = criteriaBuilder.createQuery(Integer.class);
            Root<Customer> customer = criteriaQuery.from(Customer.class);
            criteriaQuery.where(criteriaBuilder.equal( customer.get("id"), custId ));            
            Join<Customer, Product> likedProducts = customer.join("favoriteProducts");            
            likedProductsIds = entityManager.createQuery(criteriaQuery.multiselect(likedProducts.get("id"))).getResultList();
            
        } catch (Exception ex) {
            getGlobalLogger().doLogging(Level.ERROR, RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, getClass(), ex);
            throw new RetrievalException(RepositoryMessage.AN_ERROR_OCCURED_DURING_ENTITY_RETRIEVAL, ex);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return likedProductsIds;
    }
    
    @Override
    public List<Product> findAll(int custId, int startPosition) throws RetrievalException {            
        EntityManager entityManager = null;
        List<Product> result = null;
        
        try (Session session = getHibernateConfigurator().getSessionFactory().openSession()) {

            entityManager = session.getEntityManagerFactory().createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
            Root<Customer> customer = criteriaQuery.from(Customer.class);
            criteriaQuery.where(criteriaBuilder.equal( customer.get("id"), custId ));            
            Join<Customer, Product> likedProducts = customer.join("favoriteProducts");            
            result = entityManager.createQuery( criteriaQuery.select(likedProducts) )
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
        return result;
    }
    
}
