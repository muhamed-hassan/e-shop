package cairoshop.repositories;

import cairoshop.entities.*;
import cairoshop.helpers.msgs.RepositoryMessage;
import cairoshop.repositories.exceptions.*;
import cairoshop.repositories.interfaces.UserRepository;
import cairoshop.repositories.specs.CriteriaQuerySpecs;
import java.util.List;
import javax.ejb.Stateless;
import org.apache.logging.log4j.Level;
import org.hibernate.*;
import org.hibernate.criterion.Projections;

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
        
        List<User> data = null;
        try (Session session = getHibernateConfigurator().getSessionFactory().openSession()) {

            Criteria criteria = session.createCriteria(Customer.class);
            data = criteria
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
        try (Session session = getHibernateConfigurator().getSessionFactory().openSession()) {

            Criteria criteria = session.createCriteria(Customer.class);

            count = ((Long) criteria
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
