package cairoshop.service;

import cairoshop.dtos.ProductModel;
import cairoshop.entities.*;
import cairoshop.repositories.exceptions.*;
import cairoshop.repositories.interfaces.*;
import cairoshop.repositories.specs.*;
import cairoshop.service.helpers.*;
import cairoshop.service.interfaces.AdminService;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.*;
import org.apache.logging.log4j.Level;
import org.hibernate.criterion.Restrictions;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class AdminServiceImpl
        extends CommonRetrievalImpl
        implements AdminService {

    @EJB
    private CategoryRepository categoryRepository;

    @EJB
    private VendorRepository vendorRepository;

    @EJB
    private UserRepository userRepository;

    @EJB
    private ProductRepository productRepository;

    @Inject
    private ProductModelFields productModelFields;

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
    public boolean deleteCategory(Category category) {

        try {
            
            categoryRepository.remove(category.getId());
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
    public boolean deleteVendor(Vendor vendor) {

        try {
            
            vendorRepository.remove(vendor.getId());
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

    @Override
    public List<Vendor> viewVendors(int startPosition) {
        
        try {
            
            return vendorRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY, startPosition);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::viewVendors(int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
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
    public List<User> viewCustomers(int startPosition) {
        
        try {
            
            return userRepository.findAll(null, startPosition);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::viewCustomers(int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
        }
        
    }

    @Override
    public int getCustomersCount() {

        try {
            
            return userRepository.getCount(null);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getCustomersCount",
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
    public Product getProduct(int pId) {
        
        try {
            
            return productRepository
                    .find(new CriteriaQuerySpecs()
                        .addCriterion(Restrictions.eq("id", pId))
                    );
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getProduct(int pId)",
                            getClass(),
                            ex
                    );
            return null;
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
    public List<ProductModel> viewProducts(int startPosition) {

        List<String> criteria = new ArrayList<String>() {
            {
                add("NOT_DELETED=1");
            }
        };
        TableMetadata tableMetadata = new TableMetadata("PRODUCT", productModelFields.getCommonFields());
        NativeQuerySpecs nativeQuerySpecs = new NativeQuerySpecs(tableMetadata)
                .startFrom(startPosition)
                .addCriteria(criteria);

        try {
            
            return productRepository.findAll(nativeQuerySpecs);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::viewProducts(int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
        }

    }

    @Override
    public int getProductsCount() {
        
        try {
            
            return productRepository.getCount(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getProductsCount()",
                            getClass(),
                            ex
                    );
            return -1;
        }

    }

}
