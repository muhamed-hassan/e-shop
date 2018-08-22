package cairoshop.services;

import cairoshop.entities.Category;
import cairoshop.entities.Product;
import cairoshop.entities.User;
import cairoshop.entities.Vendor;
import cairoshop.repositories.exceptions.DeletionException;
import cairoshop.repositories.exceptions.InsertionException;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.CategoryRepository;
import cairoshop.repositories.interfaces.ProductRepository;
import cairoshop.repositories.interfaces.UserRepository;
import cairoshop.repositories.interfaces.VendorRepository;
import cairoshop.repositories.specs.Condition;
import cairoshop.repositories.specs.ConditionConnector;
import cairoshop.repositories.specs.QuerySpecs;
import cairoshop.services.helpers.CommonQuerySpecs;
import cairoshop.services.interfaces.AdminService;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.logging.log4j.Level;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class AdminServiceImpl extends CommonRetrievalImpl implements AdminService {

    @EJB
    private CategoryRepository categoryRepository;

    @EJB
    private VendorRepository vendorRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private ProductRepository productRepository;

    
    @PostConstruct
    public void injectReposRefs() {
        setRepos(categoryRepository, vendorRepository, productRepository);
    }

    // =========================================================================
    // ====== manage categories
    // =========================================================================
    @Override
    public boolean addCategory(Category category) {
        
        try {
            
            categoryRepository.add(category);
            return true;
            
        } catch (InsertionException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::addCategory(Category category)",
                            getClass(),
                            ex
                    );
            return false;
        }
        
    }

    @Override
    public boolean editCategory(Category category) {
        
        try {
            
            categoryRepository.update(category);
            return true;
            
        } catch (ModificationException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::editCategory(Category category)",
                            getClass(),
                            ex
                    );
            return false;
        }
        
    }

    @Override
    public boolean deleteCategory(int categoryId) {

        try {
            
            categoryRepository.remove(categoryId);
            return true;
            
        } catch (DeletionException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::deleteCategory(Category category)",
                            getClass(),
                            ex
                    );
            return false;
        }
        
    }
    
     

    // =========================================================================
    // ====== manage vendors
    // =========================================================================
    @Override
    public boolean addVendor(Vendor vendor) {

        try {
            
            vendorRepository.add(vendor);
            return true;
            
        } catch (InsertionException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::addVendor(Vendor vendor)",
                            getClass(),
                            ex
                    );
            return false;
        }

    }

    @Override
    public boolean editVendor(Vendor vendor) {

        try {
            
            vendorRepository.update(vendor);
            return true;
            
        } catch (ModificationException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::editVendor(Vendor vendor)",
                            getClass(),
                            ex
                    );
            return false;
        }

    }

    @Override
    public boolean deleteVendor(int vendorId) {

        try {
            
            vendorRepository.remove(vendorId);
            return true;
            
        } catch (DeletionException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::deleteVendor(Vendor vendor)",
                            getClass(),
                            ex
                    );
            return false;
        }

   }

    // =========================================================================
    // ====== manage users
    // =========================================================================
    @Override
    public boolean changeUserState(User user) {
        
        try {
            
            userRepository.update(user);
            return true;
            
        } catch (ModificationException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::changeUserState(User user)",
                            getClass(),
                            ex
                    );
            return false;
        }

    }

    @Override
    public List<User> getCustomers(int startPosition) {
        
        try {
            
            return userRepository.findAll(
                    new QuerySpecs().addPredicate(new Condition("role", ConditionConnector.EQUAL, 1)), startPosition);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getCustomers(int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
        }
        
    }

    @Override
    public int getCustomersCount() {

        try {
            
            return userRepository.getCount(
                    new QuerySpecs().addPredicate(new Condition("role", ConditionConnector.EQUAL,1)));
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getCustomersCount()",
                            getClass(),
                            ex
                    );
            return -1;
        }
    }

    // =========================================================================
    // ====== manage products
    // =========================================================================
    @Override
    public boolean addProduct(Product product) {
        
        try {
            
            productRepository.add(product);
            return true;
            
        } catch (InsertionException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::addProduct(Product product)",
                            getClass(),
                            ex
                    );
            return false;
        }
        
    }

    @Override
    public boolean editProduct(Product product) {
        
        try {
            
            productRepository.update(product);
            return true;
            
        } catch (ModificationException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::editProduct(Product product)",
                            getClass(),
                            ex
                    );
            return false;
        }
        
    }

    @Override
    public boolean deleteProduct(int pId) {
        
        try {
            
            productRepository.remove(pId);
            return true;
            
        } catch (DeletionException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::deleteProduct(int pId)",
                            getClass(),
                            ex
                    );
            return false;
        }
        
    }

    @Override
    public List<Product> getProducts(int startPosition) {

        try {
            
            return productRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY, startPosition);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getProducts(int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
        }

    }    

}
