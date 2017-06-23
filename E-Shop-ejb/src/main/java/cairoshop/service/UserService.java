package cairoshop.service;

import cairoshop.daos.UserDAO;
import cairoshop.entities.Customer;
import javax.ejb.Stateless;
import javax.inject.Inject;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
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
