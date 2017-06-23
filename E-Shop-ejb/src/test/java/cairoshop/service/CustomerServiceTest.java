package cairoshop.service;

import cairoshop.entities.*;
import cairoshop.helpers.*;
import java.util.*;
import javax.ejb.embeddable.*;
import org.junit.*;
import static org.junit.Assert.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class CustomerServiceTest {

    private EJBContainer container;
    private CustomerService customerService;

    public CustomerServiceTest() {
    }

    @Before
    public void init() throws Exception {
        container = EJBContainer.createEJBContainer();
        customerService = (CustomerService) container.getContext().lookup("java:global/classes/CustomerService");
    }

    @After
    public void clean() {
        container.close();
    }

    /**
     * Test of viewCategories method, of class CustomerService.
     */
    @Test
    public void testViewCategories() {
        Integer startPosition = 0;
        List<Category> result = customerService.viewCategories(startPosition);
        assertNotNull(result);
    }

    /**
     * Test of getCategoriesCount method, of class CustomerService.
     */
    @Test
    public void testGetCategoriesCount() {
        Integer expResult = -1;
        Integer result = customerService.getCategoriesCount();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of viewProductsIn method, of class CustomerService.
     */
    @Test
    public void testViewProductsIn() {
        Integer startPosition = 0;
//        Category c = new Category();
//        c.setId(3);

        Vendor v = new Vendor();
        v.setId(2);

        List result = customerService.viewProductsIn(v, startPosition);
        assertNotNull(result);
    }

    /**
     * Test of getProductsCount method, of class CustomerService.
     */
    @Test
    public void testGetProductsCount_Object() {
//        Category c = new Category();
//        c.setId(3);

        Vendor v = new Vendor();
        v.setId(2);

        Integer expResult = -1;
        Integer result = customerService.getProductsCount(v);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of sortProducts method, of class CustomerService.
     */
    @Test
    public void testSortProducts() {
        SortCriteria sortCriteria = SortCriteria.NAME;
        SortDirection sortDirection = SortDirection.DESC;
        Integer startPosition = 0;

        List result = customerService.sortProducts(sortCriteria, sortDirection, startPosition);
        assertNotNull(result);
    }

    /**
     * Test of findProductByName method, of class CustomerService.
     */
    @Test
    public void testFindProductByName() {
        String pName = "galaxy";
        List result = customerService.findProductByName(pName);
        assertNotNull(result);
    }

    /**
     * Test of getProductDetails method, of class CustomerService.
     */
    @Test
    public void testGetProductDetails() {
        Integer pID = 4;
        Product result = customerService.getProductDetails(pID);
        assertNotNull(result);
    }

    /**
     * Test of addProductToFavoriteList method, of class CustomerService.
     */
    @Test
    public void testAddProductToFavoriteList() {
        Integer pID = 4;
        Integer cID = 3;
        boolean result = customerService.addProductToFavoriteList(pID, cID);
        assertTrue(result);
    }

    /**
     * Test of viewMyFavoriteList method, of class CustomerService.
     */
    @Test
    public void testViewMyFavoriteList() {
        Customer customer = new Customer();
        customer.setId(3);

        Integer startPosition = 0;
        List result = customerService.viewMyFavoriteList(customer, startPosition);
        assertNotNull(result);
    }

    /**
     * Test of getFavoriteProductsCount method, of class CustomerService.
     */
    @Test
    public void testGetFavoriteProductsCount() {
        Integer custId = 3;
        Integer expResult = -1;
        Integer result = customerService.getFavoriteProductsCount(custId);
        assertNotEquals(expResult, result);
    }

    /**
     * Test of viewProducts method, of class CustomerService.
     */
    @Test
    public void testViewProducts() throws Exception {
        Integer startPosition = 0;
        List result = customerService.viewProducts(startPosition);
        assertNotNull(result);
    }

    /**
     * Test of getProductsCount method, of class CustomerService.
     */
    @Test
    public void testGetProductsCount() {
        Integer expResult = -1;
        Integer result = customerService.getProductsCount();
        assertNotEquals(expResult, result);
    }

    /**
     * Test of getAllVendors method, of class CustomerService.
     */
    @Test
    public void testGetAllVendors() {
        List<Vendor> result = customerService.getAllVendors();
        assertNotNull(result);
    }

    /**
     * Test of getAllCategories method, of class CustomerService.
     */
    @Test
    public void testGetAllCategories() throws Exception {
        List<Category> result = customerService.getAllCategories();
        assertNotNull(result);
    }

    /**
     * Test of getLikedProducts method, of class CustomerService.
     */
    @Test
    public void testGetLikedProducts() throws Exception {
        Integer custId = 3;
        List<Integer> result = customerService.getLikedProducts(custId);
        assertNotNull(result);
    }

}
