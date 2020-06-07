package cairoshop.services.interfaces;

import java.util.List;

import cairoshop.entities.Customer;
import cairoshop.entities.Product;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface CustomerService extends CommonRetrieval {
    
    List<Product> getProductsIn(Object productClassification, int startPosition);
    int getProductsCount(Object productClassification);
    List<Product> sortProducts(String orderBy, String orderDirection, int startPosition);
    List<Product> getProductsByName(String pName, int startPosition);
    boolean addProductToFavoriteList(Product product, Customer customer);
    List<Product> getMyFavoriteList(int custId, int startPosition);
    int getFavoriteProductsCount(int custId);
    List<Integer> getLikedProducts(int custId);
    
}
