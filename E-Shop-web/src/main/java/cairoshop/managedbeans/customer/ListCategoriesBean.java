package cairoshop.managedbeans.customer;

import cairoshop.entities.*;
import cairoshop.managedbeans.common.*;
import cairoshop.service.*;
import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.bean.*;
import javax.faces.context.*;
import javax.faces.event.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ListCategoriesBean implements Serializable {

    @EJB
    private CustomerService customerService;

    @ManagedProperty("#{paginator}")
    private Paginator paginator;

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
    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public void next() {
        categories = customerService.viewCategories(paginator.getCursor() + 5);
        paginator.setCursor(paginator.getCursor() + 5);
        paginator.setChunkSize(categories.size());
    }

    public void previous() {
        categories = customerService.viewCategories(paginator.getCursor() - 5);
        paginator.setCursor(paginator.getCursor() - 5);
        paginator.setChunkSize(categories.size());
    }

    public void first() {
        paginator.setCursor(0);
        categories = customerService.viewCategories(paginator.getCursor());
        paginator.setChunkSize(categories.size());
    }

    public void last() {
        Integer dataSize = customerService.getCategoriesCount();
        Integer chunkSize = paginator.getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            paginator.setCursor(dataSize - chunkSize);
        } else {
            paginator.setCursor(dataSize - (dataSize % chunkSize));
        }

        categories = customerService.viewCategories(paginator.getCursor());
        paginator.setChunkSize(categories.size());
    }

    public void resetPaginator(ActionEvent event) {
        Integer count = customerService.getCategoriesCount();

        paginator.setDataSize(count);
        paginator.setCursor(0);
        categories = customerService.viewCategories(paginator.getCursor());
        paginator.setChunkSize(categories.size());

    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    public void navigate() {
        if (categories == null || categories.isEmpty()) {
            Map<String, Object> sessionMap = FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();
            sessionMap.put("result", -2);
            sessionMap.put("msg", "No categories to display");
            sessionMap.put("content", "/sections/result.xhtml");

            return;
        }

        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("content", "/customer/categories-list.xhtml");
    }
}
