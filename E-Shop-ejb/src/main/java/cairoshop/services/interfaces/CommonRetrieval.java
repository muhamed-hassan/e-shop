package cairoshop.services.interfaces;

import cairoshop.entities.*;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface CommonRetrieval {
    
    List<Category> viewCategories(int startPosition);    
    List<Vendor> getAllVendors();
    List<Category> getAllCategories();
    int getCategoriesCount();
    int getVendorsCount();
    int getProductsCount();
    
}
