package cairoshop.web.models.customer;

import cairoshop.entities.Customer;
import cairoshop.entities.Product;
import cairoshop.services.interfaces.CustomerService;
import cairoshop.utils.CustomerContent;
import cairoshop.utils.Messages;
import cairoshop.utils.Scope;
import cairoshop.web.models.common.ContentChanger;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ProductDetails implements Serializable {

    @EJB
    private CustomerService customerService;

    private Product product;
    private List<Integer> likedProducts;
    private Customer currentUser;
    private boolean favorite;

    @Inject
    private ContentChanger contentChanger;

    // =========================================================================
    // =======> helpers
    // =========================================================================
    @PostConstruct
    public void init() {
        Map<String, Object> sessionMap = FacesContext
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
            favorite = likedProducts
                    .stream()
                    .anyMatch(likedProductId -> likedProductId == product.getId());
        }

        if (this.product != null) {
            Map<String, Object> sessionMap = FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
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

        if (favorite) {
            likedProducts.add(product.getId());
        }

    }

}
