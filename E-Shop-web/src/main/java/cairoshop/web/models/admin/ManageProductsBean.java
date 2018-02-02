package cairoshop.web.models.admin;

import cairoshop.web.models.common.CommonBean;
import cairoshop.web.models.common.navigation.AdminNavigation;
import cairoshop.entities.*;
import cairoshop.service.*;
import cairoshop.utils.*;
import cairoshop.web.models.common.pagination.PlainPaginationControls;
import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.bean.*;
import javax.servlet.http.*;
import org.apache.logging.log4j.Level;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ManageProductsBean 
        extends CommonBean 
        implements Serializable, AdminNavigation, PlainPaginationControls {

    @EJB
    private AdminService adminService;
    
    private Product product;
    private Part imgData;
    private Boolean removeImg = false;
    private List<Object[]> products;
    private List<Vendor> vendors;
    private List<Category> categories;

    // =========================================================================
    // =======> Add product
    // =========================================================================
    public void initAddProduct() { //to avoid NullPointerException
        product = new Product();
        product.setCategory(new Category());
        product.setVendor(new Vendor());

        vendors = adminService.getAllVendors();
        categories = adminService.getAllCategories();
    }

    public void addProduct() {
        if (imgData != null && imgData.getSize() > 0) {
            try {
                InputStream imgStream = imgData.getInputStream();

                byte[] buffer = new byte[2048]; // 2 KB
                int bytesRead;
                ByteArrayOutputStream imgData = new ByteArrayOutputStream();

                while ((bytesRead = imgStream.read(buffer)) != -1) {
                    imgData.write(buffer, 0, bytesRead);
                }

                product.setImage(imgData.toByteArray());
            } catch (IOException ex) { 
                getLogger()
                        .doLogging(
                                Level.ERROR, 
                                "Failed to read the product's image -> ManageProductsBean::addProduct()", 
                                ex,
                                this.getClass());
            }
        }

        int status = (adminService.addProduct(product) ? 1 : -1);

        String msg = ((status == 1) ? 
                product.getName() + Messages.ADDED_SUCCESSFULLY : 
                Messages.SOMETHING_WENT_WRONG);
        
        getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);

        product = null;
    }

    // =========================================================================
    // =======> Edit product
    // =========================================================================
    public void loadTarget(Integer pID) {
        product = adminService.getProduct(pID);

        if (vendors == null) {
            vendors = adminService.getAllVendors();
        }

        if (categories == null) {
            categories = adminService.getAllCategories();
        }
    }

    public void goToEdit() {
        getContentChanger().displayContent(AdminContent.EDIT_PRODUCT_DETAILS);
    }

    public void editProduct() {
        boolean flag1 = true, flag2 = false;

        if (imgData != null && imgData.getSize() > 0) {
            try {
                InputStream imgStream = imgData.getInputStream();

                byte[] buffer = new byte[2048]; // 2 KB
                int bytesRead;
                ByteArrayOutputStream imgData = new ByteArrayOutputStream();

                while ((bytesRead = imgStream.read(buffer)) != -1) {
                    imgData.write(buffer, 0, bytesRead);
                }

                flag1 = adminService.editProductImg(imgData.toByteArray(), product.getId());
            } catch (IOException ex) {
                getLogger()
                        .doLogging(
                                Level.ERROR, 
                                "Failed to read the product's image -> ManageProductsBean::editProduct()", 
                                ex,
                                this.getClass());
            }
        }

        if (removeImg) {
            flag1 = adminService.editProductImg(null, product.getId());
        }

        flag2 = adminService.editProduct(product);

        int status = ((flag1 && flag2) ? 1 : -1);

        String msg = ((status == 1) ? 
                product.getName() + Messages.EDITED_SUCCESSFULLY : 
                Messages.SOMETHING_WENT_WRONG);
        
        getContentChanger().displayContentWithMsg(msg, status, Scope.SESSION);


        removeImg = false; //reset the flag
        product = null;
    }

    // =========================================================================
    // =======> Delete product
    // =========================================================================
    public void deleteProduct(Integer pID, String pName) {
        int status = (adminService.deleteProduct(pID) ? 1 : -1);

        String msg = ((status == 1) ? 
                pName + Messages.REMOVED_SUCCESSFULLY : 
                Messages.SOMETHING_WENT_WRONG);
        
        getContentChanger().displayContentWithMsg(msg, status, Scope.REQUEST);


        getPaginator().setDataSize(adminService.getProductsCount());
        products = adminService.viewProducts(getPaginator().getCursor());
        if (products.isEmpty()) {
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

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Object[]> getProducts() {
        return products;
    }

    public void setProducts(List<Object[]> products) {
        this.products = products;
    }

    public Boolean getRemoveImg() {
        return removeImg;
    }

    public void setRemoveImg(Boolean removeImg) {
        this.removeImg = removeImg;
    }

    // =========================================================================
    // =======> Pagination
    // =========================================================================
    @Override
    public void next() {
        products = adminService.viewProducts(getPaginator().getCursor() + 5);
        getPaginator().setCursor(getPaginator().getCursor() + 5);
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void previous() {
        products = adminService.viewProducts(getPaginator().getCursor() - 5);
        getPaginator().setCursor(getPaginator().getCursor() - 5);
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void first() {
        getPaginator().setCursor(0);
        products = adminService.viewProducts(getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void last() {
        Integer dataSize = adminService.getProductsCount();
        Integer chunkSize = getPaginator().getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            getPaginator().setCursor(dataSize - chunkSize);
        } else {
            getPaginator().setCursor(dataSize - (dataSize % chunkSize));
        }

        products = adminService.viewProducts(getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
    }

    @Override
    public void resetPaginator() {
        getPaginator().setDataSize(adminService.getProductsCount());
        getPaginator().setCursor(0);
        products = adminService.viewProducts(getPaginator().getCursor());
        getPaginator().setChunkSize(products.size());
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    @Override
    public void navigate(String destination) {
        if (destination.equals(AdminActions.ADD_PRODUCT)) {
            getContentChanger().displayContent(AdminContent.ADD_NEW_PRODUCT);
        } else {
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
