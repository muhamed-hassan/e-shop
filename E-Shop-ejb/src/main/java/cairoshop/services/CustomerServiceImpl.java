package cairoshop.services;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import java.util.List;

import org.apache.logging.log4j.Level;

import cairoshop.entities.Category;
import cairoshop.entities.Customer;
import cairoshop.entities.Product;
import cairoshop.entities.Vendor;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.CategoryRepository;
import cairoshop.repositories.interfaces.ProductRepository;
import cairoshop.repositories.interfaces.UserRepository;
import cairoshop.repositories.interfaces.VendorRepository;
import cairoshop.repositories.specs.Condition;
import cairoshop.repositories.specs.ConditionConnector;
import cairoshop.repositories.specs.Join;
import cairoshop.repositories.specs.QuerySpecs;
import cairoshop.services.interfaces.CustomerService;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class CustomerServiceImpl extends CommonRetrievalImpl implements CustomerService {

    @EJB
    private UserRepository userRepository;

    @EJB
    private CategoryRepository categoryRepository;

    @EJB
    private VendorRepository vendorRepository;

    @EJB
    private ProductRepository productRepository;

    @PostConstruct
    public void injectReposRefs() {
        setRepos(categoryRepository, vendorRepository, productRepository);
    }

    @Override
    public List<Product> getProductsIn(Object productClassification, int startPosition) {

        Condition condition = null;
        if (productClassification instanceof Vendor) {
            condition = new Condition("vendor.id", ConditionConnector.EQUAL,((Vendor) productClassification).getId());
        } else if (productClassification instanceof Category) {
            condition = new Condition("category.id", ConditionConnector.EQUAL,((Category) productClassification).getId());
        } 

        try {
            
            return productRepository.findAll(
                    new QuerySpecs()
                        .addPredicate(condition)
                        .addPredicate(new Condition("notDeleted", ConditionConnector.EQUAL,true))
                    , startPosition);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getProductsIn(Object productClassification, int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
        }

    }

    @Override
    public List<Product> sortProducts(String orderBy, String orderDirection, int startPosition) {

        try {
            
            return productRepository.findAll(
                    new QuerySpecs(orderBy, orderDirection)
                        .addPredicate(new Condition("notDeleted", ConditionConnector.EQUAL,true))
                    , startPosition);
               
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::sortProducts(String orderBy, String orderDirection, int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
        }
        
    }

    @Override
    public List<Product> getProductsByName(String pName, int startPosition) {

        try {
            
            return productRepository.findAll(
                    new QuerySpecs()
                        .addPredicate(new Condition("name", ConditionConnector.LIKE, pName))
                        .addPredicate(new Condition("notDeleted", ConditionConnector.EQUAL,true))
                    , startPosition);

        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getProductsByName(String pName)",
                            getClass(),
                            ex
                    );
            return null;
        }
        
    }

    @Override
    public boolean addProductToFavoriteList(Product product, Customer customer) {
        
        try {
            
            userRepository.update(customer, product);
            return true;
            
        } catch (ModificationException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::addProductToFavoriteList(Product product, Customer customer)",
                            getClass(),
                            ex
                    );
            return false;
        }

    }

    @Override
    public List<Product> getMyFavoriteList(int custId, int startPosition) {

        try {
            
            return userRepository.findAll(custId, startPosition);
             
        } catch (RetrievalException ex) { 
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getMyFavoriteList(int custId, int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
        }
        
    }

    @Override
    public int getFavoriteProductsCount(int custId) {
        
        try {
            
            return userRepository.getCount(
                    new QuerySpecs(new Join("favoriteProducts"))
                        .addPredicate(new Condition("id", ConditionConnector.EQUAL,custId)));
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getFavoriteProductsCount(int custId)",
                            getClass(),
                            ex
                    );
            return -1;
        }
    }

    @Override
    public List<Integer> getLikedProducts(int custId) {

        try {                   
            
            return userRepository.findAll(custId);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getLikedProducts(int custId)",
                            getClass(),
                            ex
                    );
            return null;
        }
    }

    @Override
    public int getProductsCount(Object productClassification) {
        
        Condition condition = null;
        if (productClassification instanceof Vendor) {
            condition = new Condition("vendor.id", ConditionConnector.EQUAL,((Vendor) productClassification).getId());
        } else if (productClassification instanceof Category) {
            condition = new Condition("category.id", ConditionConnector.EQUAL,((Category) productClassification).getId());
        } else if (productClassification instanceof String) {
            condition = new Condition("name", ConditionConnector.LIKE,(String) productClassification);
        }

        try {
            
            return productRepository.getCount(
                    new QuerySpecs()
                        .addPredicate(condition)
                        .addPredicate(new Condition("notDeleted", ConditionConnector.EQUAL,true)));
                        
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getProductsCount(Object productClassification)",
                            getClass(),
                            ex
                    );
            return -1;
        }
    }

}
