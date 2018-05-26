package cairoshop.repositories;

import cairoshop.entities.*;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.interfaces.UserRepository;
import cairoshop.repositories.specs.CriteriaQuerySpecs;
import java.util.List;
import javax.ejb.Stateless;
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
    public List<User> findAll(CriteriaQuerySpecs querySpecs, int startPosition) {
        Session session = getHibernateConfigurator().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Customer.class);
        
        List<User> data = criteria
                .setFirstResult(startPosition)
                .setMaxResults(5)
                .list();

        session.close();

        return data;
    }

    @Override
    public int getCount(CriteriaQuerySpecs querySpecs) throws Exception {
        Session session = getHibernateConfigurator().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Customer.class);

        Integer count = ((Long) criteria
                .setProjection(Projections.rowCount())
                .uniqueResult()).intValue();

        session.close();

        return count;
    }

    @Override
    public void update(Customer customer, Product favoriteProduct) throws ModificationException {
        Session session = getHibernateConfigurator().getSessionFactory().openSession();

        session.getTransaction().begin();

        session.refresh(customer);
        customer.getFavoriteProducts().add(favoriteProduct);

        session.getTransaction().commit();

        session.close();
    }

}
