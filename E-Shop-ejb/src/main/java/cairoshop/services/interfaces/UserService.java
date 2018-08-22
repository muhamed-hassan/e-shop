package cairoshop.services.interfaces;

import cairoshop.entities.Customer;
import cairoshop.entities.User;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface UserService {
    
    User signIn(String email, String password);    
    User signUp(Customer customer);
            
}
