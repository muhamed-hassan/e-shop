package cairoshop.service.interfaces;

import cairoshop.dtos.ProductModel;
import cairoshop.entities.*;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface CustomerService 
        extends BaseService, CommonRetrieval {
    
    List<ProductModel> viewProductsIn(Object productClassification, int startPosition);
    int getProductsCount(Object productClassification);
    List<ProductModel> sortProducts(String orderBy, String orderDirection, int startPosition);
    List<ProductModel> findProductByName(String pName);
    Product getProductDetails(int pId);
    boolean addProductToFavoriteList(Product product, Customer customer);
    List<ProductModel> viewMyFavoriteList(int custId, int startPosition);
    int getFavoriteProductsCount(int custId);
    List<ProductModel> getLikedProducts(int custId);
    
}
