package cairoshop.web.models.customer;

import cairoshop.entities.*;
import cairoshop.service.*;
import cairoshop.utils.CustomerContent;
import cairoshop.utils.CustomerMessages;
import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.pagination.PaginationControlsForType;
import java.io.*;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.faces.bean.*;
import javax.faces.context.*;
import javax.faces.event.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ListProductsBean 
        extends CommonBean
        implements Serializable, PaginationControlsForType {

    @EJB
    private CustomerService customerService;

    private List<Object[]> products;
    private List<Category> categories;
    private List<Vendor> vendors;
    private Category selectedCategory;
    private Vendor selectedVendor;

    // =========================================================================
    // =======> helpers
    // =========================================================================
    @PostConstruct
    public void init() {
        if (categories == null || vendors == null) {
            categories = customerService.getAllCategories();
            vendors = customerService.getAllVendors();

            Map<String, Object> sessionMap = FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap();

            sessionMap.put("categoriesList", categories);
            sessionMap.put("vendorsList", vendors);
        }
        selectedCategory = new Category();
        selectedVendor = new Vendor();
    }

    // =========================================================================
    // =======> getters and setters
    // =========================================================================
    public List<Object[]> getProducts() {
        return products;
    }

    public void setProducts(List<Object[]> products) {
        this.products = products;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public Vendor getSelectedVendor() {
        return selectedVendor;
    }

    public void setSelectedVendor(Vendor selectedVendor) {
        this.selectedVendor = selectedVendor;
    }

    // =========================================================================
    // =======> onSelect 
    // =========================================================================
    public void loadProductsOfCategory(ValueChangeEvent e) {
        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();
        selectedCategory.setId((Integer) e.getNewValue());

        List<Category> categories = (List<Category>) sessionMap.get("categoriesList");
        for (Category c : categories) {
            if (selectedCategory.getId() == c.getId()) {
                selectedCategory.setName(c.getName());
                break;
            }
        }

        resetPaginator(selectedCategory);

        if (products == null || products.isEmpty()) {
            StringBuilder msg = new StringBuilder()
                    .append(CustomerMessages.CATEGORY_OF)
                    .append(selectedCategory.getName())
                    .append(CustomerMessages.HAS_NO_PRODUCTS_TO_DISPLAY);
                    
            getContentChanger().displayNoDataFound(msg.toString());
            return;
        }

        sessionMap.put("content", CustomerContent.VIEW_PRODUCTS_IN);
        sessionMap.put("selected", "category");
    }

    public void loadProductsOfVendor(ValueChangeEvent e) {
        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();
        selectedVendor.setId((Integer) e.getNewValue());

        List<Vendor> vendors = (List<Vendor>) sessionMap.get("vendorsList");
        for (Vendor v : vendors) {
            if (selectedVendor.getId() == v.getId()) {
                selectedVendor.setName(v.getName());
                break;
            }
        }

        resetPaginator(selectedVendor);

        if (products == null || products.isEmpty()) {
            StringBuilder msg = new StringBuilder()
                    .append(CustomerMessages.VENDOR_OF)
                    .append(selectedVendor.getName())
                    .append(CustomerMessages.HAS_NO_PRODUCTS_TO_DISPLAY);
                    
            getContentChanger().displayNoDataFound(msg.toString());
            return;
        }

        sessionMap.put("content", CustomerContent.VIEW_PRODUCTS_IN);
        sessionMap.put("selected", "vendor");
    }

    // =========================================================================
    // =======> Pagination
    // =========================================================================
    @Override
    public void next(String selected) {
        products = customerService
                .viewProductsIn(
                        ((selected.equals("vendor") ? selectedVendor : selectedCategory)), 
                            getPaginator().getCursor() + 5);
        getPaginator().setCursor(getPaginator().getCursor() + 5);
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void previous(String selected) {
        products = customerService
                .viewProductsIn(
                        ((selected.equals("vendor") ? selectedVendor : selectedCategory)), 
                            getPaginator().getCursor() - 5);
        getPaginator().setCursor(getPaginator().getCursor() - 5);
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void first(String selected) {
        getPaginator().setCursor(0);
        products = customerService
                .viewProductsIn(
                        ((selected.equals("vendor") ? selectedVendor : selectedCategory)), 
                            getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void last(String selected) {
        Integer dataSize = customerService
                .getProductsCount(((selected.equals("vendor") ? selectedVendor : selectedCategory)));
        Integer chunkSize = getPaginator().getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            getPaginator().setCursor(dataSize - chunkSize);
        } else {
            getPaginator().setCursor(dataSize - (dataSize % chunkSize));
        }

        products = customerService.viewProductsIn(
                ((selected.equals("vendor") ? selectedVendor : selectedCategory)), 
                    getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void resetPaginator(Object object) {
        getPaginator().setDataSize(customerService.getProductsCount(object));
        getPaginator().setCursor(0);
        products = customerService.viewProductsIn(object, getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
    }
}
