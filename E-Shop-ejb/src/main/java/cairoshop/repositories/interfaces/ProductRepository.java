package cairoshop.repositories.interfaces;

import cairoshop.entities.Product;
import cairoshop.repositories.exceptions.RetrievalException;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface ProductRepository extends AbstractRepository<Product>, PagableRepository<Product> {
    
    byte[] getImage(int pId) throws RetrievalException;
    
}
