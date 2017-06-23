package cairoshop.managedbeans.customer;

import cairoshop.helpers.*;
import cairoshop.managedbeans.common.*;
import cairoshop.service.*;
import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.bean.*;
import javax.faces.context.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class SortProductsBean implements Serializable {

    @EJB
    private CustomerService customerService;

    @ManagedProperty("#{paginator}")
    private Paginator paginator;

    private SortCriteria sortCriteria;
    private SortDirection sortDirection;
    private List<Object[]> products;

    // =========================================================================
    // =======> setters and getters
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
            sessionMap.put("msg", "No products to display");
            sessionMap.put("content", "/sections/result.xhtml");

            return;
        }

        sessionMap.put("content", "/customer/sort-products.xhtml");
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
        products = customerService.sortProducts(sortCriteria, sortDirection, paginator.getCursor() + 5);
        paginator.setCursor(paginator.getCursor() + 5);
        paginator.setChunkSize(products.size());
    }

    public void previous() {
        products = customerService.sortProducts(sortCriteria, sortDirection, paginator.getCursor() - 5);
        paginator.setCursor(paginator.getCursor() - 5);
        paginator.setChunkSize(products.size());
    }

    public void first() {
        paginator.setCursor(0);
        products = customerService.sortProducts(sortCriteria, sortDirection, paginator.getCursor());
        paginator.setChunkSize(products.size());
    }

    public void last() {
        Integer dataSize = customerService.getProductsCount();
        Integer chunkSize = paginator.getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            paginator.setCursor(dataSize - chunkSize);
        } else {
            paginator.setCursor(dataSize - (dataSize % chunkSize));
        }

        products = customerService.sortProducts(sortCriteria, sortDirection, paginator.getCursor());
        paginator.setChunkSize(products.size());
    }

    public void resetPaginator(String criteria, String direction) {
        sortCriteria = ((criteria.equals("name")) ? SortCriteria.NAME : SortCriteria.PRICE);
        sortDirection = ((direction.equals("asc")) ? SortDirection.ASC : SortDirection.DESC);

        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();

        sessionMap.put("sortCriteria", criteria);
        sessionMap.put("sortDirection", direction.equals("asc") ? "an ascending" : "a descending");

        paginator.setDataSize(customerService.getProductsCount());
        paginator.setCursor(0);
        products = customerService.sortProducts(sortCriteria, sortDirection, paginator.getCursor());
        paginator.setChunkSize(products.size());
    }

}
