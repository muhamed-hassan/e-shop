package cairoshop.web.models.customer;

import cairoshop.dtos.ProductModel;
import cairoshop.entities.*;
import cairoshop.service.interfaces.CustomerService;
import cairoshop.utils.*;
import cairoshop.web.models.common.ContentChanger;
import java.io.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;
import javax.faces.bean.*;
import javax.faces.context.*;
import javax.inject.Inject;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ProductDetails 
        implements Serializable {

    @EJB
    private CustomerService customerService;

    private Product product;
    private List<ProductModel> likedProducts;
    private Customer currentUser;
    private Boolean favorite;
    private Map<String, Object> sessionMap = null;
            
    @Inject
    private ContentChanger contentChanger;

    // =========================================================================
    // =======> helpers
    // =========================================================================
    @PostConstruct
    public void init() {
        favorite = false;
        
        sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();
        
        currentUser = (Customer) sessionMap
                .get("currentUser");
        
        likedProducts = customerService
                .getLikedProducts(currentUser.getId());
    }

    // =========================================================================
    // =======> setters and getters
    // =========================================================================
    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public List<ProductModel> getLikedProducts() {
        return likedProducts;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // =========================================================================
    // =======> load product based in id
    // =========================================================================
    public void getDetails(Integer pId) {
        product = customerService.getProductDetails(pId);

        Integer currentProductId = product.getId();

        favorite = (likedProducts
                .stream()
                .filter(p -> p.getId() == currentProductId)
                .count()) == 1;

        if (product != null) {
            if( sessionMap.size() == 0 ) {
                sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();
            }
            sessionMap.put("content", CustomerContent.PRODUCT_PAGE);
            return;
        }

        contentChanger.displayContentWithMsg(Messages.SOMETHING_WENT_WRONG, -1, Scope.SESSION);
    }

    // =========================================================================
    // =======> like link toggling
    // =========================================================================
    public void addToFavorites() {
        favorite = customerService.addProductToFavoriteList(product, currentUser);

       if( favorite ) {
            ProductModel productModel = new ProductModel();
            productModel.setId(product.getId());
            productModel.setName(product.getName());
            productModel.setPrice(product.getPrice());
            productModel.setQuantity(product.getQuantity());
            
            likedProducts.add(productModel);
        }
        
    }
    
    @PreDestroy
    public void clean() {
        likedProducts = null;
    }

}
