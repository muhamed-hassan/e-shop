package cairoshop.web.models.customer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import cairoshop.entities.Customer;
import cairoshop.entities.Product;
import cairoshop.messages.Messages;
import cairoshop.pages.CustomerContent;
import cairoshop.utils.Scope;
import cairoshop.web.models.common.CommonBean;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ProductDetails extends CommonBean implements Serializable {

    /*@EJB
    private CustomerService customerService;*/

    private Product product;
    private List<Integer> likedProducts;
    private Customer currentUser;
    private boolean favorite;

    // =========================================================================
    // =======> helpers
    // =========================================================================
    @PostConstruct
    public void init() {
        Map<String, Object> sessionMap = getSessionMap();
        currentUser = (Customer) sessionMap.get("currentUser");
        //likedProducts = customerService.getLikedProducts(currentUser.getId());
    }

    // =========================================================================
    // =======> setters and getters
    // =========================================================================
    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
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
    public void getDetails(Product product) {
        this.product = product;
        if (likedProducts != null && !likedProducts.isEmpty()) {
            favorite = likedProducts.stream()
                                        .anyMatch(likedProductId -> likedProductId == product.getId());
        }

        if (this.product != null) {
            Map<String, Object> sessionMap = getSessionMap();
            sessionMap.put("content", CustomerContent.PRODUCT_PAGE);
            return;
        }
        
        getContentChanger().displayContentWithMsg(Messages.SOMETHING_WENT_WRONG, -1, Scope.SESSION);
    }

    // =========================================================================
    // =======> like link toggling
    // =========================================================================
    public void addToFavorites() {
        //favorite = customerService.addProductToFavoriteList(product, currentUser);
        if (favorite) {
            likedProducts.add(product.getId());
        }
    }

}
