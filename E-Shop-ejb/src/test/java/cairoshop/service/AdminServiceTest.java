package cairoshop.service;

import cairoshop.entities.*;
import java.util.*;
import javax.ejb.embeddable.*;
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class AdminServiceTest {

    private EJBContainer container;
    private AdminService adminService;

    public AdminServiceTest() {
    }

    @Before
    public void init() throws Exception {
        container = EJBContainer.createEJBContainer();
        adminService = (AdminService) container.getContext().lookup("java:global/classes/AdminService");
    }

    @After
    public void clean() {
        container.close();
    }

    /**
     * Test of addCategory method, of class AdminService.
     */
    @Test
    public void testAddCategory() {
        Category category = new Category("Accessories");

        boolean result = adminService.addCategory(category);
        assertTrue(result);
    }

    /**
     * Test of editCategory method, of class AdminService.
     */
    @Test
    public void testEditCategory() {
        Category category = new Category();
        category.setId(4);
        category.setName("Access");

        boolean result = adminService.editCategory(category);
        assertTrue(result);
    }

    /**
     * Test of deleteCategory method, of class AdminService.
     */
    @Test
    public void testDeleteCategory() {
        Category category = new Category();
        category.setId(4);

        boolean result = adminService.deleteCategory(category);
        assertTrue(result);
    }

    /**
     * Test of viewCategories method, of class AdminService.
     */
    @Test
    public void testViewCategories() {
        List<Category> result = adminService.viewCategories(0);
        assertNotNull(result);
    }

    /**
     * Test of getCategoriesCount method, of class AdminService.
     */
    @Test
    public void testGetCategoriesCount() {
        Integer expResult = -1;
        Integer result = adminService.getCategoriesCount();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of getAllCategories method, of class AdminService.
     */
    @Test
    public void testGetAllCategories() {
        List<Category> result = adminService.getAllCategories();
        assertNotNull(result);
    }

    /**
     * Test of addVendor method, of class AdminService.
     */
    @Test
    public void testAddVendor() {
        Vendor vendor = new Vendor("Howoarang");

        boolean result = adminService.addVendor(vendor);
        assertTrue(result);
    }

    /**
     * Test of editVendor method, of class AdminService.
     */
    @Test
    public void testEditVendor() {
        Vendor vendor = new Vendor();
        vendor.setId(4);
        vendor.setName("Howoarang");

        boolean result = adminService.editVendor(vendor);
        assertTrue(result);
    }

    /**
     * Test of deleteVendor method, of class AdminService.
     */
    @Test
    public void testDeleteVendor() {
        Vendor vendor = new Vendor();
        vendor.setId(4);

        boolean result = adminService.deleteVendor(vendor);
        assertTrue(result);
    }

    /**
     * Test of viewVendors method, of class AdminService.
     */
    @Test
    public void testViewVendors() {
        List<Vendor> result = adminService.viewVendors(0);
        assertNotNull(result);
    }

    /**
     * Test of getVendorsCount method, of class AdminService.
     */
    @Test
    public void testGetVendorsCount() {
        Integer expResult = -1;
        Integer result = adminService.getVendorsCount();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of getAllVendors method, of class AdminService.
     */
    @Test
    public void testGetAllVendors() {
        List<Vendor> result = adminService.getAllVendors();
        assertNotNull(result);
    }

    /**
     * Test of activate method, of class AdminService.
     */
    @Test
    public void testActivate() {
        User user = new Customer();
        user.setId(5);
        user.setActive(true);
        boolean result = adminService.edit(user);
        assertTrue(result);
    }

    /**
     * Test of deactivate method, of class AdminService.
     */
    @Test
    public void testDeactivate() {
        User user = new Customer();
        user.setId(5);
        user.setActive(false);
        boolean result = adminService.edit(user);
        assertTrue(result);
    }

    /**
     * Test of viewCustomers method, of class AdminService.
     */
    @Test
    public void testViewCustomers() {
        List<User> result = adminService.viewCustomers(0);        
        assertNotNull(result);
    }

    /**
     * Test of getCustomersCount method, of class AdminService.
     */
    @Test
    public void testGetCustomersCount() {
        Integer expResult = -1;
        Integer result = adminService.getCustomersCount();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of addProduct method, of class AdminService.
     */
    @Test
    public void testAddProduct() {
        Product product = new Product();
        boolean result = adminService.addProduct(product);
        assertTrue(result);
    }

    /**
     * Test of getProduct method, of class AdminService.
     */
    @Test
    public void testGetProduct() {
        Integer pID = 5;
        Product result = adminService.getProduct(pID);
        assertNotNull(result);
    }

    /**
     * Test of editProduct method, of class AdminService.
     */
    @Test
    public void testEditProduct() {
        Product product = new Product();
        product.setId(4);
        product.setName("HTC x1");

        boolean result = adminService.editProduct(product);
        assertTrue(result);
    }

    /**
     * Test of editProductImg method, of class AdminService.
     */
    @Test
    public void testEditProductImg() throws Exception {
        byte[] imgStream = null; //delete image
        Integer pID = 5;
        boolean result = adminService.editProductImg(imgStream, pID);
        assertTrue(result);
    }

    /**
     * Test of deleteProduct method, of class AdminService.
     */
    @Test
    public void testDeleteProduct() {
        Integer pID = 4;
        boolean result = adminService.deleteProduct(pID);
        assertTrue(result);
    }

    /**
     * Test of viewProducts method, of class AdminService.
     */
    @Test
    public void testViewProducts() {
        Integer startPosition = 5;
        List result = adminService.viewProducts(startPosition);
        assertNotNull(result);
    }

    /**
     * Test of getProductsCount method, of class AdminService.
     */
    @Test
    public void testGetProductsCount() {
        Integer expResult = -1;
        Integer result = adminService.getProductsCount();
        assertNotEquals(expResult, result);
    }

}
