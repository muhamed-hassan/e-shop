package cairoshop.web.models.customer;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cairoshop.entities.Customer;
import cairoshop.entities.Product;
import cairoshop.services.interfaces.CustomerService;
import cairoshop.pages.CustomerContent;
import cairoshop.messages.CustomerMessages;
import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.pagination.PlainPaginationControls;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class FavoriteProductsListBean extends CommonBean implements Serializable, PlainPaginationControls {

    @EJB
    private CustomerService customerService;

    private List<Product> products;

    private Customer c;

    // =========================================================================
    // =======> helpers
    // =========================================================================
    @PostConstruct
    public void init() {
        Map<String, Object> sessionMap = getSessionMap();
        c = (Customer) sessionMap.get("currentUser");        
    }
    
    // =========================================================================
    // =======> getters and setters
    // =========================================================================
    public List<Product> getProducts() {
        return products;
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
        int dataSize = customerService.getFavoriteProductsCount(c.getId());
        int chunkSize = getPaginator().getChunkSize();
        adjustPaginationControls(((dataSize % chunkSize) == 0) ? (dataSize - chunkSize) : (dataSize - (dataSize % chunkSize)));
    }

    @Override
    public void resetPaginator() {
        getPaginator().setDataSize(customerService.getFavoriteProductsCount(c.getId()));        
        adjustPaginationControls(0);
    }
    
    private void adjustPaginationControls(int cursor) {
        products = customerService.getMyFavoriteList(c.getId(), cursor);
        getPaginator().setCursor(cursor);
        getPaginator().setChunkSize(products.size());
    }

}
