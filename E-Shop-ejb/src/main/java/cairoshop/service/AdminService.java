package cairoshop.service;

import cairoshop.daos.*;
import cairoshop.entities.*;
import java.util.*;
import javax.ejb.*;
import javax.inject.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class AdminService {

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private VendorDAO vendorDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private ProductDAO productDAO;

    // =========================================================================
    // ====== manage categories
    // =========================================================================
    public boolean addCategory(Category category) {
        return categoryDAO.insert(category);
    }

    public boolean editCategory(Category category) {
        return categoryDAO.update(category);
    }

    public boolean deleteCategory(Category category) {
        return categoryDAO.delete(category);
    }

    public List<Category> viewCategories(Integer startPosition) {
        return categoryDAO.getAll(startPosition);
    }

    public Integer getCategoriesCount() {
        return categoryDAO.getCount();
    }

    public List<Category> getAllCategories() {
        return categoryDAO.getAll();
    }

    // =========================================================================
    // ====== manage vendors
    // =========================================================================
    public boolean addVendor(Vendor vendor) {
        return vendorDAO.insert(vendor);
    }

    public boolean editVendor(Vendor vendor) {
        return vendorDAO.update(vendor);
    }

    public boolean deleteVendor(Vendor vendor) {
        return vendorDAO.delete(vendor);
    }

    public List<Vendor> viewVendors(Integer startPosition) {
        return vendorDAO.getAll(startPosition);
    }

    public Integer getVendorsCount() {
        return vendorDAO.getCount();
    }

    public List<Vendor> getAllVendors() {
        return vendorDAO.getAll();
    }

    // =========================================================================
    // ====== manage users
    // =========================================================================
    public boolean activate(Integer userID) {
        return userDAO.update(userID, true);
    }

    public boolean deactivate(Integer userID) {
        return userDAO.update(userID, false);
    }

    public List<Customer> viewCustomers(Integer startPosition) {
        return userDAO.getAll(startPosition);
    }

    public Integer getCustomersCount() {
        return userDAO.getCount();
    }

    // =========================================================================
    // ====== manage products
    // =========================================================================
    public boolean addProduct(Product product) {
        return productDAO.insert(product);
    }

    public Product getProduct(Integer pID) {
        return productDAO.get(pID);
    }

    public boolean editProduct(Product product) {
        return productDAO.update(product);
    }

    public boolean editProductImg(byte[] imgStream, Integer pID) {
        return productDAO.update(imgStream, pID);
    }

    public boolean deleteProduct(Integer pID) {
        return productDAO.delete(pID);
    }

    public List<Object[]> viewProducts(Integer startPosition) {
        return productDAO.getAll(startPosition);
    }

    public Integer getProductsCount() {
        return productDAO.getCount();
    }

}
