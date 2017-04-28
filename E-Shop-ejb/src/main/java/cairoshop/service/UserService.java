package cairoshop.service;

import cairoshop.daos.*;
import cairoshop.entities.*;
import javax.ejb.*;
import javax.inject.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class UserService {

    @Inject
    private UserDAO userDAO;

    public Object signIn(String email, String password) {
        return userDAO.find(email, password);
    }

    public Object signUp(Customer customer) {
        return userDAO.insert(customer);
    }
}
