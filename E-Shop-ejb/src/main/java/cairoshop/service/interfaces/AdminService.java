package cairoshop.service.interfaces;

import cairoshop.dtos.ProductModel;
import cairoshop.entities.*;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface AdminService 
        extends CommonRetrieval {
    
    boolean addCategory(Category category);
    boolean editCategory(Category category);
    boolean deleteCategory(Category category);
    boolean addVendor(Vendor vendor);
    boolean editVendor(Vendor vendor);
    boolean deleteVendor(Vendor vendor);
    List<Vendor> viewVendors(int startPosition);
    boolean changeUserState(User user);
    List<User> viewCustomers(int startPosition);
    int getCustomersCount();
    boolean addProduct(Product product);
    boolean editProduct(Product product);
    boolean deleteProduct(int pId);
    List<ProductModel> viewProducts(int startPosition);
    Product getProduct(int pId);
    
}
