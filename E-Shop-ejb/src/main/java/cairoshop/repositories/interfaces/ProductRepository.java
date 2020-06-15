package cairoshop.repositories.interfaces;

import cairoshop.entities.Product;
import cairoshop.repositories.exceptions.RetrievalException;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface ProductRepository extends BaseRepository<Product> {
    
    byte[] getImage(int pId) throws RetrievalException;
    
}
