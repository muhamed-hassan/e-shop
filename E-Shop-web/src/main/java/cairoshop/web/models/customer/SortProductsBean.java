package cairoshop.web.models.customer;

import cairoshop.entities.Product;
import cairoshop.web.models.common.navigation.CustomerNavigation;
import cairoshop.services.interfaces.CustomerService;
import cairoshop.utils.CustomerContent;
import cairoshop.utils.CustomerMessages;
import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.pagination.PaginationControlsWithSorting;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class SortProductsBean 
        extends CommonBean 
        implements Serializable, CustomerNavigation, PaginationControlsWithSorting {

    @EJB
    private CustomerService customerService;

    private String sortCriteria;
    private String sortDirection;
    private List<Product> products;

    // =========================================================================
    // =======> setters and getters
    // =========================================================================
    public List<Product> getProducts() {
        return products;
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
        adjustPaginationControls(getPaginator().getCursor() + 5);
    }

    @Override
    public void previous() {
        adjustPaginationControls(getPaginator().getCursor() - 5);
    }

    @Override
    public void first() {
        adjustPaginationControls(0);
    }

    @Override
    public void last() {
        int dataSize = customerService.getProductsCount();
        int chunkSize = getPaginator().getChunkSize();
        
        adjustPaginationControls(((dataSize % chunkSize) == 0) ? (dataSize - chunkSize) : (dataSize - (dataSize % chunkSize)));
    }

    @Override
    public void resetPaginator(String criteria, String direction) {
        sortCriteria = criteria;
        sortDirection = direction;

        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();

        sessionMap.put("sortCriteria", criteria);
        sessionMap.put("sortDirection", direction.equals("asc") ? "an ascending" : "a descending");

        getPaginator().setDataSize(customerService.getProductsCount());
        adjustPaginationControls(0);
    }
    
    private void adjustPaginationControls(int cursor) {
        products = customerService.sortProducts(sortCriteria, sortDirection, cursor);
        getPaginator().setCursor(cursor);
        getPaginator().setChunkSize(products.size());
    }

}
