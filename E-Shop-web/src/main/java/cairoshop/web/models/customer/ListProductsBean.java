package cairoshop.web.models.customer;

import cairoshop.entities.Category;
import cairoshop.entities.Product;
import cairoshop.entities.Vendor;
import cairoshop.services.interfaces.CustomerService;
import cairoshop.utils.CustomerContent;
import cairoshop.utils.CustomerMessages;
import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.pagination.PaginationControlsForType;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.joining;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

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

    private List<Product> products;
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
            categories = customerService.getCategories();
            vendors = customerService.getVendors();
        }
        
        selectedCategory = new Category();
        selectedVendor = new Vendor();
    }

    // =========================================================================
    // =======> getters and setters
    // =========================================================================
    public List<Product> getProducts() {
        return products;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Vendor> getVendors() {
        return vendors;
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
        
        selectedCategory.setName(
                categories
                .stream()
                .filter(c -> c.getId() == selectedCategory.getId())
                .map(Category::getName)
                .collect(joining()));        

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

        selectedVendor.setName(
                vendors
                .stream()
                .filter(v -> v.getId() == selectedVendor.getId())
                .map(Vendor::getName)
                .collect(joining()));

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
        adjustPaginationControls(getPaginator().getCursor() + 5, selected.equals("vendor") ? selectedVendor : selectedCategory);
    }

    @Override
    public void previous(String selected) {
        adjustPaginationControls(getPaginator().getCursor() - 5, selected.equals("vendor") ? selectedVendor : selectedCategory);
    }

    @Override
    public void first(String selected) {        
        adjustPaginationControls(0, selected.equals("vendor") ? selectedVendor : selectedCategory);
    }

    @Override
    public void last(String selected) {
        int dataSize = customerService
                .getProductsCount(((selected.equals("vendor") ? selectedVendor : selectedCategory)));
        int chunkSize = getPaginator().getChunkSize();
        
        adjustPaginationControls(((dataSize % chunkSize) == 0) ? (dataSize - chunkSize) : (dataSize - (dataSize % chunkSize)), selected.equals("vendor") ? selectedVendor : selectedCategory);
    }

    @Override
    public void resetPaginator(Object object) {
        getPaginator().setDataSize(customerService.getProductsCount(object));
        
        adjustPaginationControls(0, object);
    }
    
    private void adjustPaginationControls(int cursor, Object classifiedBy) {
        products = customerService.getProductsIn(classifiedBy, cursor);
        getPaginator().setCursor(cursor);
        getPaginator().setChunkSize(products == null ? 0 : products.size());
    }
    
}
