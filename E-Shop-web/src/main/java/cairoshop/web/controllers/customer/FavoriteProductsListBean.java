package cairoshop.web.controllers.customer;

import cairoshop.web.controllers.common.pagination.PaginationControls;
import cairoshop.web.controllers.common.CommonBean;
import cairoshop.entities.*;
import cairoshop.service.*;
import cairoshop.utils.CustomerContent;
import cairoshop.utils.CustomerMessages;
import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.bean.*;
import javax.faces.context.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class FavoriteProductsListBean 
        extends CommonBean 
        implements Serializable, PaginationControls {

    @EJB
    private CustomerService customerService;

    private List<Object[]> products;

    private Customer c;

    // =========================================================================
    // =======> getters and setters
    // =========================================================================
    public List<Object[]> getProducts() {
        return products;
    }

    public void setProducts(List<Object[]> products) {
        this.products = products;
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    public void navigate() {
        if (products == null || products.isEmpty()) {
            getContentChanger().displayNoDataFound(CustomerMessages.YOU_HAVE_NO_PRODUCTS_IN_YOUR_FAVORITE_LIST);
            
            return;
        }

        getContentChanger().displayContent(CustomerContent.FAVORITE_PRODUCTS);
    }

    // =========================================================================
    // =======> Pagination
    // =========================================================================
    @Override
    public void next() {
        products = customerService.viewMyFavoriteList(c, getPaginator().getCursor() + 5);
        getPaginator().setCursor(getPaginator().getCursor() + 5);
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void previous() {
        products = customerService.viewMyFavoriteList(c, getPaginator().getCursor() - 5);
        getPaginator().setCursor(getPaginator().getCursor() - 5);
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void first() {
        getPaginator().setCursor(0);
        products = customerService.viewMyFavoriteList(c, getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void last() {
        Integer dataSize = customerService.getFavoriteProductsCount(c.getId());
        Integer chunkSize = getPaginator().getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            getPaginator().setCursor(dataSize - chunkSize);
        } else {
            getPaginator().setCursor(dataSize - (dataSize % chunkSize));
        }

        products = customerService.viewMyFavoriteList(c, getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
    }

    public void resetPaginator() {
        c = (Customer) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("currentUser");

        getPaginator().setDataSize(customerService.getFavoriteProductsCount(c.getId()));
        getPaginator().setCursor(0);
        products = customerService.viewMyFavoriteList(c, getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
    }

}
