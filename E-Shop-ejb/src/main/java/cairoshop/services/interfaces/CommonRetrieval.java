package cairoshop.services.interfaces;

import java.util.List;

import cairoshop.entities.Category;
import cairoshop.entities.Vendor;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
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
