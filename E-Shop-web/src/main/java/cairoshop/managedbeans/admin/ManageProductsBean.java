package cairoshop.managedbeans.admin;

import cairoshop.entities.*;
import cairoshop.managedbeans.common.*;
import cairoshop.service.*;
import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.faces.bean.*;
import javax.faces.context.*;
import javax.servlet.http.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
@SessionScoped
public class ManageProductsBean implements Serializable, NavigationRule {

    @EJB
    private AdminService adminService;

    @ManagedProperty("#{paginator}")
    private Paginator paginator;

    private Product product;
    private Part imgData;
    private Boolean removeImg = false;
    private List<Object[]> products;
    private List<Vendor> vendors;
    private List<Category> categories;

    // =========================================================================
    // =======> Add product
    // =========================================================================
    public void initAddProduct() //to avoid NullPointerException
    {
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
                ex.printStackTrace();
            }
        }

        int status = (adminService.addProduct(product) ? 1 : -1);

        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();
        sessionMap.put("result", status);

        String msg = ((status == 1) ? product.getName() + " added successfully." : "Something went wrong - please try again later.");
        sessionMap.put("msg", msg);

        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("content", "/sections/result.xhtml");

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
        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("content", "/admin/manage-products/edit-product-pg.xhtml");
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
                ex.printStackTrace();
            }
        }

        if (removeImg) {
            flag1 = adminService.editProductImg(null, product.getId());
        }

        flag2 = adminService.editProduct(product);

        int status = ((flag1 && flag2) ? 1 : -1);

        Map<String, Object> sessionMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap();
        sessionMap.put("result", status);

        String msg = ((status == 1) ? product.getName() + " edited successfully." : "Something went wrong - please try again later.");
        sessionMap.put("msg", msg);

        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("content", "/sections/result.xhtml");

        removeImg = false; //reset the flag
        product = null;
    }

    // =========================================================================
    // =======> Delete product
    // =========================================================================
    public void deleteProduct(Integer pID, String pName) {
        int status = (adminService.deleteProduct(pID) ? 1 : -1);

        Map<String, Object> reqMap = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestMap();
        reqMap.put("result", status);

        String msg = ((status == 1) ? pName + " deleted successfully." : "Something went wrong - please try again later.");
        reqMap.put("msg", msg);

        paginator.setDataSize(adminService.getProductsCount());
        products = adminService.viewProducts(paginator.getCursor());
        if (products.isEmpty()) {
            previous();
        }

        paginator.setChunkSize(products.size());
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
    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public void next() {
        products = adminService.viewProducts(paginator.getCursor() + 5);
        paginator.setCursor(paginator.getCursor() + 5);
        paginator.setChunkSize(products.size());
    }

    public void previous() {
        products = adminService.viewProducts(paginator.getCursor() - 5);
        paginator.setCursor(paginator.getCursor() - 5);
        paginator.setChunkSize(products.size());
    }

    public void first() {
        paginator.setCursor(0);
        products = adminService.viewProducts(paginator.getCursor());
        paginator.setChunkSize(products.size());
    }

    public void last() {
        Integer dataSize = adminService.getProductsCount();
        Integer chunkSize = paginator.getChunkSize();

        if ((dataSize % chunkSize) == 0) {
            paginator.setCursor(dataSize - chunkSize);
        } else {
            paginator.setCursor(dataSize - (dataSize % chunkSize));
        }

        products = adminService.viewProducts(paginator.getCursor());
        paginator.setChunkSize(products.size());
    }

    public void resetPaginator() {
        paginator.setDataSize(adminService.getProductsCount());
        paginator.setCursor(0);
        products = adminService.viewProducts(paginator.getCursor());
        paginator.setChunkSize(products.size());
    }

    // =========================================================================
    // =======> Navigation
    // =========================================================================
    @Override
    public void navigate(String destination) {
        String goTo = "/admin/";

        if (destination.equals("addProduct")) {
            goTo += "manage-products/add-new-product.xhtml";
        } else {
            if (products == null || products.isEmpty()) {
                Map<String, Object> sessionMap = FacesContext
                        .getCurrentInstance()
                        .getExternalContext()
                        .getSessionMap();
                sessionMap.put("result", -2);
                sessionMap.put("msg", "No products to display");
                sessionMap.put("content", "/sections/result.xhtml");

                return;
            }

            if (destination.equals("editProduct")) {
                goTo += "manage-products/edit-product-master.xhtml";
            } else if (destination.equals("deleteProduct")) {
                goTo += "manage-products/delete-product.xhtml";
            }
        }

        FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("content", goTo);
    }

}
