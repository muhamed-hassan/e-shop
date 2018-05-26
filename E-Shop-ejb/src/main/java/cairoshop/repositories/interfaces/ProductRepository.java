package cairoshop.repositories.interfaces;

import cairoshop.dtos.ProductModel;
import cairoshop.entities.Product;
import cairoshop.repositories.*;
import cairoshop.repositories.specs.NativeQuerySpecs;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface ProductRepository 
        extends AbstractRepository<Product>, PagableRepository<Product> {
    
    List<ProductModel> findAll(NativeQuerySpecs querySpecs);    
    int getCount(NativeQuerySpecs querySpecs) throws Exception;    
    byte[] getImage(Integer pId) throws Exception;
    
}
