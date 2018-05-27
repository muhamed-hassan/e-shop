package cairoshop.repositories.interfaces;

import cairoshop.dtos.ProductModel;
import cairoshop.entities.Product;
import cairoshop.repositories.*;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.specs.NativeQuerySpecs;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface ProductRepository 
        extends AbstractRepository<Product>, PagableRepository<Product> {
    
    List<ProductModel> findAll(NativeQuerySpecs querySpecs) throws RetrievalException;    
    int getCount(NativeQuerySpecs querySpecs) throws RetrievalException;    
    byte[] getImage(int pId) throws RetrievalException;
    
}
