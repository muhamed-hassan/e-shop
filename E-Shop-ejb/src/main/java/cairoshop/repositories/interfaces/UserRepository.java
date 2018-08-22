package cairoshop.repositories.interfaces;

import cairoshop.entities.Customer;
import cairoshop.entities.Product;
import cairoshop.entities.User;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface UserRepository extends AbstractRepository<User>, PagableRepository<User> {

    void update(Customer customer, Product favoriteProduct) throws ModificationException;
    List<Integer> findAll(int custId) throws RetrievalException; 
    List<Product> findAll(int custId, int startPosition) throws RetrievalException; 
    
}
