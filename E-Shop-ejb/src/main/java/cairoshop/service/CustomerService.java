package cairoshop.service;

import cairoshop.daos.*;
import cairoshop.entities.*;
import cairoshop.helpers.*;
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
public class CustomerService {

    @Inject
    private CategoryDAO categoryDAO;

    @Inject
    private VendorDAO vendorDAO;

    @Inject
    private ProductDAO productDAO;

    // =========================================================================
    // ====== list categories
    // =========================================================================
    public List<Category> viewCategories(Integer startPosition) {
        return categoryDAO.getAll(startPosition);
    }

    public Integer getCategoriesCount() {
        return categoryDAO.getCount();
    }

    // =========================================================================
    // ====== retrieve products based on a certain vendor or a category
    // =========================================================================
    public List<Object[]> viewProductsIn(Object object, Integer startPosition) {
        return productDAO.getAll(object, startPosition);
    }

    public Integer getProductsCount(Object object) {
        return productDAO.getCount(object);
    }

    // =========================================================================
    // ====== sort products by name (asc || desc)
    // =========================================================================
    public List<Object[]> sortProducts(SortCriteria sortCriteria, SortDirection sortDirection, Integer startPosition) {
        return productDAO.getAll(sortCriteria, sortDirection, startPosition);
    }

    // =========================================================================
    // ====== find products with similar name
    // =========================================================================
    public List<Object[]> findProductByName(String pName) {
        return productDAO.getAll(pName);
    }

    // =========================================================================
    // ====== load product by id
    // =========================================================================
    public Product getProductDetails(Integer pID) {
        return productDAO.get(pID);
    }

    // =========================================================================
    // ====== like product
    // =========================================================================
    public boolean addProductToFavoriteList(Integer pID, Integer cID) {
        return productDAO.update(pID, cID);
    }

    // =========================================================================
    // ====== view my favorite products
    // =========================================================================
    public List<Object[]> viewMyFavoriteList(Customer customer, Integer startPosition) {
        return productDAO.getAll(customer, startPosition);
    }

    public Integer getFavoriteProductsCount(Integer custId) {
        return productDAO.getFavoriteCount(custId);
    }

    // =========================================================================
    // ====== helpers
    // =========================================================================
    public List<Object[]> viewProducts(Integer startPosition) {
        return productDAO.getAll(startPosition);
    }

    public Integer getProductsCount() {
        return productDAO.getCount();
    }

    public List<Vendor> getAllVendors() {
        return vendorDAO.getAll();
    }

    public List<Category> getAllCategories() {
        return categoryDAO.getAll();
    }

    public List<Integer> getLikedProducts(Integer custId) {
        return productDAO.getLikedProducts(custId);
    }

}
