package cairoshop.services;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.Level;

import cairoshop.entities.Customer;
import cairoshop.entities.User;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.UserRepository;
import cairoshop.repositories.specs.Condition;
import cairoshop.repositories.specs.ConditionConnector;
import cairoshop.repositories.specs.QuerySpecs;
import cairoshop.services.interfaces.UserService;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class UserServiceImpl extends BaseService implements UserService {

    @EJB
    private UserRepository userRepository;

    @Override
    public User signIn(String email, String password) {

        try {

            return userRepository
                    .find(
                            new QuerySpecs()
                                .addPredicate(new Condition("mail", ConditionConnector.EQUAL, email))
                                .addPredicate(new Condition("password", ConditionConnector.EQUAL, password))
                                .addPredicate(new Condition("active", ConditionConnector.EQUAL, true))
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
                    .find(new QuerySpecs()
                            .addPredicate(new Condition("mail", ConditionConnector.EQUAL, customer.getMail()))
                            .addPredicate(new Condition("password", ConditionConnector.EQUAL, customer.getPassword()))
                            .addPredicate(new Condition("active", ConditionConnector.EQUAL, true))
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
