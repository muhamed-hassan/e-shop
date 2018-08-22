package cairoshop.services.interfaces;

import cairoshop.entities.Category;
import cairoshop.entities.Vendor;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface CommonRetrieval {
    
    List<Category> getCategories();
    List<Category> getCategories(int startPosition);
    int getCategoriesCount();
    
    List<Vendor> getVendors();
    List<Vendor> getVendors(int startPosition);
    int getVendorsCount();    
    
    int getProductsCount();
    
}
