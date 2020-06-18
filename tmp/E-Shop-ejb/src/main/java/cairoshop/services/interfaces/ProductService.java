package cairoshop.services.interfaces;

import cairoshop.entities.Product;
import cairoshop.repositories.exceptions.RetrievalException;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
* LinkedIn    : https://www.linkedin.com/in/muhamed-hassan/                *
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface ProductService /*extends BaseService<Product>*/ {
    
    byte[] getImage(int pId) throws RetrievalException;
    
}
