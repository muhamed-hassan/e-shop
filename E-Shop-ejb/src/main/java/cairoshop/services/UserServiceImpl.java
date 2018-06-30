package cairoshop.services;

import cairoshop.entities.*;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.UserRepository;
import cairoshop.repositories.specs.*;
import cairoshop.services.interfaces.UserService;
import javax.ejb.*;
import org.apache.logging.log4j.Level;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class UserServiceImpl
        extends BaseService
        implements UserService {

    @EJB
    private UserRepository userRepository;

    @Override
    public User signIn(String email, String password) {

        try {

            return userRepository
                    .find(new CriteriaQuerySpecs()
                        .addPredicate(new Condition("mail", email))
                            .addPredicate(new Condition("password", password))
                            .addPredicate(new Condition("active", true))
                    );
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::signIn(String email, String password)",
                            getClass(),
                            ex
                    );
            return null;
        }
        
    }

    @Override
    public User signUp(Customer customer) {
        
        try {
            
            userRepository.add(customer);

            return userRepository
                    .find(new CriteriaQuerySpecs()
                            .addPredicate(new Condition("mail", customer.getMail()))
                            .addPredicate(new Condition("password", customer.getPassword()))
                            .addPredicate(new Condition("active", true))
                    );
        } catch (Exception ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::signUp(Customer customer)",
                            getClass(),
                            ex
                    );
            return null;
        }

    }
}
