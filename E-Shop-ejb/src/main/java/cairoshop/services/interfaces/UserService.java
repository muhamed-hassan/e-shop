package cairoshop.services.interfaces;

import cairoshop.entities.Customer;
import cairoshop.entities.User;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface UserService {
    
    User signIn(String email, String password);    
    User signUp(Customer customer);
            
}
