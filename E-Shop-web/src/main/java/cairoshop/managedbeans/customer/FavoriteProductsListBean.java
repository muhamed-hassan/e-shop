package cairoshop.managedbeans.customer;

import cairoshop.entities.*;
import cairoshop.managedbeans.common.*;
import cairoshop.service.*;
import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.bean.*;
import javax.faces.context.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class FavoriteProductsListBean implements Serializable {

    @EJB
    private CustomerService customerService;

    @ManagedProperty("#{paginator}")
    private Paginator paginator;

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
        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();

        if (products == null || products.isEmpty()) {
            sessionMap.put("result", -2);
            sessionMap.put("msg", "You have no products in your favorite list");
            sessionMap.put("content", "/sections/result.xhtml");

            return;
        }

        sessionMap.put("content", "/customer/favorite-products.xhtml");
    }

    // =========================================================================
    // =======> Pagination
    // =========================================================================
    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public void next() {
        products = customerService.viewMyFavoriteList(c, paginator.getCursor() + 5);
        paginator.setCursor(paginator.getCursor() + 5);
        paginator.setChunkSize(products.size());
    }

    public void previous() {
        products = customerService.viewMyFavoriteList(c, paginator.getCursor() - 5);
        paginator.setCursor(paginator.getCursor() - 5);
        paginator.setChunkSize(products.size());
    }

    public void first() {
        paginator.setCursor(0);
        products = customerService.viewMyFavoriteList(c, paginator.getCursor());
        paginator.setChunkSize(products.size());
    }

    public void last() {
        Integer dataSize = customerService.getFavoriteProductsCount(c.getId());
        Integer chunkSize = paginator.getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            paginator.setCursor(dataSize - chunkSize);
        } else {
            paginator.setCursor(dataSize - (dataSize % chunkSize));
        }

        products = customerService.viewMyFavoriteList(c, paginator.getCursor());
        paginator.setChunkSize(products.size());
    }

    public void resetPaginator() {
        c = (Customer) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("currentUser");

        paginator.setDataSize(customerService.getFavoriteProductsCount(c.getId()));
        paginator.setCursor(0);
        products = customerService.viewMyFavoriteList(c, paginator.getCursor());
        paginator.setChunkSize(products.size());
    }

}
