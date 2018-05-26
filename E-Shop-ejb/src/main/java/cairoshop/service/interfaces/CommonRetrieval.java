package cairoshop.service.interfaces;

import cairoshop.entities.*;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface CommonRetrieval {
    
    List<Category> viewCategories(Integer startPosition);
    int getCategoriesCount();
    int getVendorsCount();
    List<Vendor> getAllVendors();
    List<Category> getAllCategories();
    int getProductsCount();
    
}
