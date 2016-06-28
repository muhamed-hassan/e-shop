package cairoshop.daos;

import cairoshop.entities.*;
import java.util.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface UserDAO extends Common
{

    boolean update(Integer userID, boolean flag);

    Object find(String email, String password);

    Object insert(Customer customer);

    List<Customer> getAll(Integer startPosition);
}
