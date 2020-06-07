package cairoshop.web.models.customer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import java.io.Serializable;
import java.util.List;

import cairoshop.entities.Product;
import cairoshop.web.models.common.navigation.CustomerNavigation;
import cairoshop.services.interfaces.CustomerService;
import cairoshop.utils.CustomerContent;
import cairoshop.utils.CustomerMessages;
import cairoshop.utils.Messages;
import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.pagination.PlainPaginationControls;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class FindProductBean
        extends CommonBean
        implements Serializable, CustomerNavigation, PlainPaginationControls {

    @EJB
    private CustomerService customerService;

    private String keyword;
    private List<Product> queryResult;

    // =========================================================================
    // =======> Getters and Setters
    // =========================================================================
    public List<Product> getQueryResult() {
        return queryResult;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    @Override
    public void navigate() {
        if (queryResult == null || queryResult.isEmpty()) {
            getContentChanger().displayNoDataFound(CustomerMessages.NO_PRODUCTS_TO_DISPLAY);

            return;
        }

        getContentChanger().displayContent(CustomerContent.PRODUCTS_RESULT);
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
        int dataSize = customerService.getProductsCount(keyword);
        int chunkSize = getPaginator().getChunkSize();
        
        adjustPaginationControls(((dataSize % chunkSize) == 0) ? (dataSize - chunkSize) : (dataSize - (dataSize % chunkSize)));
    }

    @Override
    public void resetPaginator() {
        getPaginator().setDataSize(customerService.getProductsCount(keyword));
        
        adjustPaginationControls(0);

        if (queryResult == null || queryResult.isEmpty()) {
            getContentChanger().displayNoDataFound(Messages.NO_PRODUCTS_TO_DISPLAY);
        }
    }
    
    private void adjustPaginationControls(int cursor) {
        queryResult = customerService.getProductsByName(keyword, cursor);
        getPaginator().setCursor(cursor);
        getPaginator().setChunkSize(queryResult.size());
    }

}
