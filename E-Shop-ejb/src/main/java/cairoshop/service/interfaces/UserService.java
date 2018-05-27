package cairoshop.service.interfaces;

import cairoshop.entities.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface UserService {
    
    User signIn(String email, String password);    
    User signUp(Customer customer);
            
}
