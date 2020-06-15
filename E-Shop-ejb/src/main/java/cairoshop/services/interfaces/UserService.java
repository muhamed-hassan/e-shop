package cairoshop.services.interfaces;

import java.util.List;

import cairoshop.entities.Customer;
import cairoshop.entities.Product;
import cairoshop.entities.User;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.BaseRepository;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface UserService extends BaseService<User> {

    void update(Customer customer, Product favoriteProduct) throws ModificationException;

    List<Integer> findAll(int custId) throws RetrievalException;

    List<Product> findAll(int custId, int startPosition) throws RetrievalException; 
    
}
