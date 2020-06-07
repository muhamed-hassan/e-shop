package cairoshop.web.models.customer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import java.io.Serializable;
import java.util.List;

import cairoshop.entities.Category;
import cairoshop.web.models.common.navigation.CustomerNavigation;
import cairoshop.services.interfaces.CustomerService;
import cairoshop.utils.CustomerContent;
import cairoshop.utils.CustomerMessages;
import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.pagination.PaginationControlsWithEvent;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ListCategoriesBean 
        extends CommonBean 
        implements Serializable, CustomerNavigation, PaginationControlsWithEvent {

    @EJB
    private CustomerService customerService;

    private List<Category> categories;

    // =========================================================================
    // =======> getters and setters
    // =========================================================================
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
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
        int dataSize = customerService.getCategoriesCount();
        int chunkSize = getPaginator().getChunkSize();
        
        adjustPaginationControls(((dataSize % chunkSize) == 0) ? (dataSize - chunkSize) : (dataSize - (dataSize % chunkSize)));
    }

    @Override
    public void resetPaginator(ActionEvent event) {
        getPaginator().setDataSize(customerService.getCategoriesCount());
        
        adjustPaginationControls(0);
    }

    private void adjustPaginationControls(int cursor) {
        categories = customerService.getCategories(cursor);
        getPaginator().setCursor(cursor);
        getPaginator().setChunkSize(categories.size());
    }
    
    // =========================================================================
    // =======> Navigation
    // =========================================================================
    @Override
    public void navigate() {
        if (categories == null || categories.isEmpty()) {
            getContentChanger().displayNoDataFound(CustomerMessages.NO_CATEGORIES_TO_DISPLAY);
            
            return;
        }
        
        getContentChanger().displayContent(CustomerContent.CATEGORIES_LIST);
    }
    
}
