package cairoshop.services;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import java.util.List;

import org.apache.logging.log4j.Level;

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

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
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
        boolean added = true;        
        try {
            
            categoryRepository.add(category);
            
        } catch (InsertionException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::addCategory(Category category)", getClass(), ex);
            added =  false;
        }
        return added;
    }

    @Override
    public boolean editCategory(Category category) {
        boolean edited = true;        
        try {
            
            categoryRepository.update(category);
            
        } catch (ModificationException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::editCategory(Category category)", getClass(), ex);
            edited = false;
        }
        return edited;
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        boolean deleted = true;        
        try {
            
            categoryRepository.remove(categoryId);
            
        } catch (DeletionException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::deleteCategory(Category category)", getClass(), ex);
            deleted = false;
        }
        return deleted;
    }
   
    // =========================================================================
    // ====== manage vendors
    // =========================================================================
    @Override
    public boolean addVendor(Vendor vendor) {
        boolean added = true;        
        try {
            
            vendorRepository.add(vendor);
            
        } catch (InsertionException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::addVendor(Vendor vendor)", getClass(), ex);
            added =  false;
        }        
        return added;
    }

    @Override
    public boolean editVendor(Vendor vendor) {
        boolean edited = true;        
        try {
            
            vendorRepository.update(vendor);
            
        } catch (ModificationException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::editVendor(Vendor vendor)", getClass(), ex);
            edited = false;
        }        
        return edited;
    }

    @Override
    public boolean deleteVendor(int vendorId) {
        boolean deleted = true;        
        try {
            
            vendorRepository.remove(vendorId);
            
        } catch (DeletionException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::deleteVendor(Vendor vendor)", getClass(), ex);
            deleted = false;
        }        
        return deleted;
   }

    // =========================================================================
    // ====== manage users
    // =========================================================================
    @Override
    public boolean changeUserState(User user) {
        boolean updated = true;
        try {
            
            userRepository.update(user);
            
        } catch (ModificationException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::changeUserState(User user)", getClass(), ex);
            updated = false;
        }
        return updated;
    }

    @Override
    public List<User> getCustomers(int startPosition) {
        List<User> users = null;        
        try {
            
            users = userRepository.findAll(new QuerySpecs().addPredicate(new Condition("role", ConditionConnector.EQUAL, 1)), startPosition);
            
        } catch (RetrievalException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::getCustomers(int startPosition)", getClass(), ex);
        }
        return users;
    }

    @Override
    public int getCustomersCount() {
        int count = -1;
        try {
            
            count = userRepository.getCount(new QuerySpecs().addPredicate(new Condition("role", ConditionConnector.EQUAL,1)));
            
        } catch (RetrievalException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::getCustomersCount()", getClass(), ex);
        }        
        return count;
    }

    // =========================================================================
    // ====== manage products
    // =========================================================================
    @Override
    public boolean addProduct(Product product) {
        boolean added = true;        
        try {
            
            productRepository.add(product);
            
        } catch (InsertionException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::addProduct(Product product)", getClass(), ex);
            added = false;
        }
        return added;
    }

    @Override
    public boolean editProduct(Product product) {
        boolean edited = true;        
        try {
            
            productRepository.update(product);
            
        } catch (ModificationException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::editProduct(Product product)", getClass(), ex);
            edited = false;
        }
        return edited;
    }

    @Override
    public boolean deleteProduct(int pId) {
        boolean deleted = true;        
        try {
            
            productRepository.remove(pId);
            
        } catch (DeletionException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::deleteProduct(int pId)", getClass(), ex);
            deleted = false;
        }
        return deleted;
    }

    @Override
    public List<Product> getProducts(int startPosition) {
        List<Product> products = null;        
        try {
            
            products = productRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY, startPosition);
            
        } catch (RetrievalException ex) {
            getGlobalLogger().doLogging(Level.ERROR, "Caller::getProducts(int startPosition)", getClass(), ex);
        }
        return products;
    }    

}
