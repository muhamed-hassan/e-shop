package cairoshop.web.models.customer;

import cairoshop.web.models.common.navigation.CustomerNavigation;
import cairoshop.entities.*;
import cairoshop.service.*;
import cairoshop.utils.*;
import cairoshop.web.models.common.CommonBean;
import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.bean.*;
import javax.faces.event.*;
import cairoshop.web.models.common.pagination.PaginationControlsWithEvent;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
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
        categories = customerService.viewCategories(getPaginator().getCursor() + 5);
        getPaginator().setCursor(getPaginator().getCursor() + 5);
        getPaginator().setChunkSize(categories.size());
    }

    @Override
    public void previous() {
        categories = customerService.viewCategories(getPaginator().getCursor() - 5);
        getPaginator().setCursor(getPaginator().getCursor() - 5);
        getPaginator().setChunkSize(categories.size());
    }

    @Override
    public void first() {
        getPaginator().setCursor(0);
        categories = customerService.viewCategories(getPaginator().getCursor());
        getPaginator().setChunkSize(categories.size());
    }

    @Override
    public void last() {
        Integer dataSize = customerService.getCategoriesCount();
        Integer chunkSize = getPaginator().getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            getPaginator().setCursor(dataSize - chunkSize);
        } else {
            getPaginator().setCursor(dataSize - (dataSize % chunkSize));
        }

        categories = customerService.viewCategories(getPaginator().getCursor());
        getPaginator().setChunkSize(categories.size());
    }

    @Override
    public void resetPaginator(ActionEvent event) {
        Integer count = customerService.getCategoriesCount();

        getPaginator().setDataSize(count);
        getPaginator().setCursor(0);
        categories = customerService.viewCategories(getPaginator().getCursor());
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
