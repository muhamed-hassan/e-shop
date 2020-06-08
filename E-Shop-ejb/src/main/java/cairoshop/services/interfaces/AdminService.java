package cairoshop.services.interfaces;

import java.util.List;

import cairoshop.entities.Category;
import cairoshop.entities.Product;
import cairoshop.entities.User;
import cairoshop.entities.Vendor;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface AdminService extends CommonRetrieval {
    
    boolean addCategory(Category category);
    boolean editCategory(Category category);
    boolean deleteCategory(int categoryId);
    
    boolean addVendor(Vendor vendor);
    boolean editVendor(Vendor vendor);
    boolean deleteVendor(int vendorId);
    
    boolean changeUserState(User user);
    List<User> getCustomers(int startPosition);
    int getCustomersCount();    
    
    boolean addProduct(Product product);
    boolean editProduct(Product product);
    boolean deleteProduct(int pId);
    List<Product> getProducts(int startPosition);    
    
}