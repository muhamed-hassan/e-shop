package cairoshop.service;

import cairoshop.entities.*;
import cairoshop.repositories.interfaces.UserRepository;
import cairoshop.repositories.specs.CriteriaQuerySpecs;
import cairoshop.service.interfaces.UserService;
import javax.ejb.*;
import org.hibernate.criterion.Restrictions;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class UserServiceImpl
        implements UserService {

    @EJB
    private UserRepository userRepository;

    @Override
    public User signIn(String email, String password) {

        try {

            return userRepository
                    .find(new CriteriaQuerySpecs()
                        .addCriterion(Restrictions.eq("mail", email))
                        .addCriterion(Restrictions.eq("password", password))
                        .addCriterion(Restrictions.eq("active", true))
                    );
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        
    }

    @Override
    public User signUp(Customer customer) {
        
        try {
            
            userRepository.add(customer);

            return userRepository
                    .find(new CriteriaQuerySpecs()
                        .addCriterion(Restrictions.eq("mail", customer.getMail()))
                        .addCriterion(Restrictions.eq("password", customer.getPassword()))
                        .addCriterion(Restrictions.eq("active", true))
                    );
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
