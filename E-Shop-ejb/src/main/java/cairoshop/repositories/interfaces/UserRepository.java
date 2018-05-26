package cairoshop.repositories.interfaces;

import cairoshop.entities.*;
import cairoshop.repositories.*;
import cairoshop.repositories.exceptions.ModificationException;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface UserRepository
        extends AbstractRepository<User>, PagableRepository<User> {

    void update(Customer customer, Product favoriteProduct) throws ModificationException;
    
}
