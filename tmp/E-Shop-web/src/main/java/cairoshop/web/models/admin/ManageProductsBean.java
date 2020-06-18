package cairoshop.web.models.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;

import cairoshop.actions.AdminActions;
import cairoshop.entities.Category;
import cairoshop.entities.Product;
import cairoshop.entities.Vendor;
import cairoshop.messages.AdminMessages;
import cairoshop.messages.Messages;
import cairoshop.pages.AdminContent;
import cairoshop.utils.ImageStreamExtractor;
import cairoshop.utils.Scope;
import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.navigation.AdminNavigation;
import cairoshop.web.models.common.pagination.PlainPaginationControls;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
* LinkedIn    : https://www.linkedin.com/in/muhamed-hassan/                *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ManageProductsBean extends CommonBean implements Serializable, AdminNavigation, PlainPaginationControls {

    /*@EJB
    private AdminService adminService;*/
    
    @Inject
    private ImageStreamExtractor imageStreamExtractor;
    
    private Product product;
    private Part imgData;
    private List<Product> products;
    private List<Vendor> vendors;
    private List<Category> categories;    

    // =========================================================================
    // =======> Add product
    // =========================================================================
    public void initAddProduct() { 
        product = new Product();
        product.setCategory(new Category());
        product.setVendor(new Vendor());
        /*vendors = adminService.getVendors();
        categories = adminService.getCategories();*/
    }

    public void addProduct() throws IOException {        
        if (imgData != null && imgData.getSize() > 0) {
            product.setImage(imageStreamExtractor.extract(imgData.getInputStream()));
        }
        int status = 0;//adminService.addProduct(product) ? 1 : -1;
        String msg = (status == 1) ? product.getName() + Messages.ADDED_SUCCESSFULLY : Messages.SOMETHING_WENT_WRONG;        
        getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);
    }

    // =========================================================================
    // =======> Edit product
    // =========================================================================
    public void loadTarget(Product product) {
        this.product = product;
        if (vendors == null) {
            //vendors = adminService.getVendors();
        }
        if (categories == null) {
            //categories = adminService.getCategories();
        }        
    }

    public void goToEdit() {
        getContentChanger().displayContent(AdminContent.EDIT_PRODUCT_DETAILS);
    }

    public void editProduct() throws IOException {
        if (imgData != null && imgData.getSize() > 0) {            
            product.setImage(imageStreamExtractor.extract(imgData.getInputStream()));
        }
        int status = 0;//adminService.editProduct(product) ? 1 : -1;
        String msg = (status == 1) ? product.getName() + Messages.EDITED_SUCCESSFULLY : Messages.SOMETHING_WENT_WRONG;        
        getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);
    }

    // =========================================================================
    // =======> Delete product
    // =========================================================================
    public void deleteProduct(Product productToBeDeleted) {
        int status = 0;//adminService.deleteProduct(productToBeDeleted.getId()) ? 1 : -1;
        String msg = (status == 1) ? productToBeDeleted.getName() + Messages.REMOVED_SUCCESSFULLY : Messages.SOMETHING_WENT_WRONG;        
        getContentChanger().displayContentWithMsg(msg, status, Scope.REQUEST);
        //getPaginator().setDataSize(adminService.getProductsCount());
        //products = adminService.getProducts(getPaginator().getCursor());
        if (products == null || products.isEmpty()) {
            previous();
        }
        getPaginator().setChunkSize(products.size());
    }

    // =========================================================================
    // =======> getters and setters
    // =========================================================================
    public Part getImgData() {
        return imgData;
    }

    public void setImgData(Part imgData) {
        this.imgData = imgData;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Product> getProducts() {
        return products;
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
        int dataSize = 0;//adminService.getProductsCount();
        int chunkSize = getPaginator().getChunkSize();        
        adjustPaginationControls(((dataSize % chunkSize) == 0) ? (dataSize - chunkSize) : (dataSize - (dataSize % chunkSize)));
    }

    @Override
    public void resetPaginator() {
        //getPaginator().setDataSize(adminService.getProductsCount());
        adjustPaginationControls(0);
    }
    
    private void adjustPaginationControls(int cursor) {
        //products = adminService.getProducts(cursor);
        getPaginator().setCursor(cursor);
        getPaginator().setChunkSize(products.size());
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    @Override
    public void navigate(String destination) {
        switch (destination) {
            case AdminActions.ADD_PRODUCT:
                getContentChanger().displayContent(AdminContent.ADD_NEW_PRODUCT);
                break;
            default:
                if (products == null || products.isEmpty()) {
                    getContentChanger().displayNoDataFound(AdminMessages.NO_PRODUCTS_TO_DISPLAY);
                    return;
                }   
                switch (destination) {
                    case AdminActions.EDIT_PRODUCT:
                        getContentChanger().displayContent(AdminContent.EDIT_PRODUCT_MASTER);
                        break;
                    case AdminActions.DELETE_PRODUCT:
                        getContentChanger().displayContent(AdminContent.DELETE_PRODUCT);
                        break;
                }
        }
    }

}
