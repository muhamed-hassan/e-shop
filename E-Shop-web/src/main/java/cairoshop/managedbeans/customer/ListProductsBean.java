package cairoshop.managedbeans.customer;

import cairoshop.entities.*;
import cairoshop.managedbeans.common.*;
import cairoshop.service.*;
import java.io.*;
import java.util.*;
import javax.annotation.PostConstruct;
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
public class ListProductsBean implements Serializable {

    @EJB
    private CustomerService customerService;

    @ManagedProperty("#{paginator}")
    private Paginator paginator;

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
            sessionMap.put("result", -2);
            sessionMap.put("content", "/sections/result.xhtml");
            sessionMap.put("msg", "Category (" + selectedCategory.getName() + ") has no products to display");
            return;
        }

        sessionMap.put("content", "/customer/view-products-in.xhtml");
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
            sessionMap.put("result", -2);
            sessionMap.put("content", "/sections/result.xhtml");
            sessionMap.put("msg", "Vendor (" + selectedVendor.getName() + ") has no products to display");
            return;
        }

        sessionMap.put("content", "/customer/view-products-in.xhtml");
        sessionMap.put("selected", "vendor");
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

    public void next(String selected) {
        products = customerService.viewProductsIn(((selected.equals("vendor") ? selectedVendor : selectedCategory)), paginator.getCursor() + 5);
        paginator.setCursor(paginator.getCursor() + 5);
        paginator.setChunkSize(products.size());
    }

    public void previous(String selected) {
        products = customerService.viewProductsIn(((selected.equals("vendor") ? selectedVendor : selectedCategory)), paginator.getCursor() - 5);
        paginator.setCursor(paginator.getCursor() - 5);
        paginator.setChunkSize(products.size());
    }

    public void first(String selected) {
        paginator.setCursor(0);
        products = customerService.viewProductsIn(((selected.equals("vendor") ? selectedVendor : selectedCategory)), paginator.getCursor());
        paginator.setChunkSize(products.size());
    }

    public void last(String selected) {
        Integer dataSize = customerService.getProductsCount(((selected.equals("vendor") ? selectedVendor : selectedCategory)));
        Integer chunkSize = paginator.getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            paginator.setCursor(dataSize - chunkSize);
        } else {
            paginator.setCursor(dataSize - (dataSize % chunkSize));
        }

        products = customerService.viewProductsIn(((selected.equals("vendor") ? selectedVendor : selectedCategory)), paginator.getCursor());
        paginator.setChunkSize(products.size());
    }

    private void resetPaginator(Object object) {
        paginator.setDataSize(customerService.getProductsCount(object));
        paginator.setCursor(0);
        products = customerService.viewProductsIn(object, paginator.getCursor());
        paginator.setChunkSize(products.size());
    }
}
