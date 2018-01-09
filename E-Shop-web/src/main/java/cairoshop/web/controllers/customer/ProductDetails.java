package cairoshop.web.controllers.customer;

import cairoshop.entities.*;
import cairoshop.service.*;
import cairoshop.utils.CustomerContent;
import cairoshop.utils.Messages;
import cairoshop.utils.Scope;
import cairoshop.web.controllers.common.ContentChanger;
import java.io.*;
import java.util.*;
import javax.annotation.PostConstruct;
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
    private List<Integer> likedProducts;
    private Customer currentUser;
    private Boolean favorite;
    
    @Inject
    private ContentChanger contentChanger;

    // =========================================================================
    // =======> helpers
    // =========================================================================
    @PostConstruct
    public void init() {
        favorite = false;
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

    public List<Integer> getLikedProducts() {
        return likedProducts;
    }

    public void setLikedProducts(List<Integer> likedProducts) {
        this.likedProducts = likedProducts;
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
    public void getDetails(Integer pID) {
        product = customerService.getProductDetails(pID);

        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();

        currentUser = (Customer) sessionMap
                .get("currentUser");

        likedProducts = customerService
                .getLikedProducts(currentUser.getId());

        Integer currentProductId = product.getId();

        for (Integer pId : likedProducts) {
            if (currentProductId == pId) {
                favorite = true;
                break;
            }
        }

        if (product != null) {
            sessionMap.put("content", CustomerContent.PRODUCT_PAGE);
            return;
        }

        contentChanger.displayContentWithMsg(Messages.SOMETHING_WENT_WRONG, -1, Scope.SESSION);
    }

    // =========================================================================
    // =======> like link toggling
    // =========================================================================
    public String addToFavorites() {
        favorite = customerService.addProductToFavoriteList(product.getId(), currentUser.getId());

        likedProducts = customerService
                .getLikedProducts(currentUser.getId());

        return null;
    }

}
