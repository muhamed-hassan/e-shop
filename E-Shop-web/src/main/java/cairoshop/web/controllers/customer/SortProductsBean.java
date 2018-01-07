package cairoshop.web.controllers.customer;

import cairoshop.web.controllers.common.navigation.CustomerNavigation;
import cairoshop.web.controllers.common.pagination.PaginationControls;
import cairoshop.web.controllers.common.CommonBean;
import cairoshop.helpers.*;
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
public class SortProductsBean 
        extends CommonBean 
        implements Serializable, CustomerNavigation, PaginationControls {

    @EJB
    private CustomerService customerService;

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
    @Override
    public void navigate() {
        if (products == null || products.isEmpty()) {
            getContentChanger().displayNoDataFound(CustomerMessages.NO_PRODUCTS_TO_DISPLAY);
            return;
        }

        getContentChanger().displayContent(CustomerContent.SORT_PRODUCTS);
    }

    // =========================================================================
    // =======> Pagination
    // =========================================================================
    @Override
    public void next() {
        products = customerService.sortProducts(sortCriteria, sortDirection, getPaginator().getCursor() + 5);
        getPaginator().setCursor(getPaginator().getCursor() + 5);
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void previous() {
        products = customerService.sortProducts(sortCriteria, sortDirection, getPaginator().getCursor() - 5);
        getPaginator().setCursor(getPaginator().getCursor() - 5);
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void first() {
        getPaginator().setCursor(0);
        products = customerService.sortProducts(sortCriteria, sortDirection, getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void last() {
        Integer dataSize = customerService.getProductsCount();
        Integer chunkSize = getPaginator().getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            getPaginator().setCursor(dataSize - chunkSize);
        } else {
            getPaginator().setCursor(dataSize - (dataSize % chunkSize));
        }

        products = customerService.sortProducts(sortCriteria, sortDirection, getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
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

        getPaginator().setDataSize(customerService.getProductsCount());
        getPaginator().setCursor(0);
        products = customerService.sortProducts(sortCriteria, sortDirection, getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
    }

}
