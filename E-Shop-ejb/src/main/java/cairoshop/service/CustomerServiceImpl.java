package cairoshop.service;

import cairoshop.dtos.ProductModel;
import cairoshop.entities.*;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.*;
import cairoshop.repositories.specs.*;
import cairoshop.service.helpers.ProductModelFields;
import cairoshop.service.interfaces.CustomerService;
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
public class CustomerServiceImpl
        extends CommonRetrievalImpl
        implements CustomerService {

    @EJB
    private UserRepository userRepository;

    @EJB
    private CategoryRepository categoryRepository;

    @EJB
    private VendorRepository vendorRepository;

    @EJB
    private ProductRepository productRepository;

    @Inject
    private ProductModelFields productModelFields;

    @PostConstruct
    public void injectReposRefs() {
        setRepos(categoryRepository, vendorRepository, productRepository);
    }

    @Override
    public List<ProductModel> viewProductsIn(Object productClassification, int startPosition) {
        StringBuilder productClassificationCriterion = new StringBuilder();

        if (productClassification instanceof Vendor) {
            productClassificationCriterion.append("VENDOR=").append(((Vendor) productClassification).getId());
        } else if (productClassification instanceof Category) {
            productClassificationCriterion.append("CATEGORY=").append(((Category) productClassification).getId());
        } 
        
        List<String> criteria = new ArrayList<String>() {
            {
                add("NOT_DELETED=1");
                add("AND");
                add(productClassificationCriterion.toString());
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
                            "Caller::viewProductsIn(Object productClassification, int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
        }

    }

    @Override
    public List<ProductModel> sortProducts(String orderBy, String orderDirection, int startPosition) {
        
        List<String> criteria = new ArrayList<String>() {
            {
                add("NOT_DELETED=1");
            }
        };
        TableMetadata tableMetadata = new TableMetadata("PRODUCT", productModelFields.getCommonFields());
        NativeQuerySpecs nativeQuerySpecs = new NativeQuerySpecs(tableMetadata)
                .startFrom(startPosition)
                .addCriteria(criteria);

        nativeQuerySpecs.setSortCriteria(orderBy, orderDirection);

        try {
            
            return productRepository.findAll(nativeQuerySpecs);
            
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
    public List<ProductModel> findProductByName(String pName) {

        StringBuilder productNameCriterion = new StringBuilder()
                .append("NAME LIKE ")
                .append("'%").append(pName).append("%'");
        List<String> criteria = new ArrayList<String>() {
            {
                add("NOT_DELETED=1");
                add("AND");
                add(productNameCriterion.toString());
            }
        };
        TableMetadata tableMetadata = new TableMetadata("PRODUCT", productModelFields.getCommonFields());
        NativeQuerySpecs nativeQuerySpecs = new NativeQuerySpecs(tableMetadata)
                .addCriteria(criteria);

        try {
            
            return productRepository.findAll(nativeQuerySpecs);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::findProductByName(String pName)",
                            getClass(),
                            ex
                    );
            return null;
        }
        
    }

    @Override
    public Product getProductDetails(int pId) {
       
        try {
            
            return productRepository.find(new CriteriaQuerySpecs().addCriterion(Restrictions.eq("id", pId)));
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getProductDetails(int pId)",
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
    public List<ProductModel> viewMyFavoriteList(int custId, int startPosition) {
        
        TableMetadata productTable = new TableMetadata("PRODUCT", productModelFields.getCommonFields(), "ID");
        TableMetadata customerFavProductTable = new TableMetadata("CUSTOMER_FAV_PRODUCT", null, "PRODUCT");

        List<TableMetadata> tables = new ArrayList<TableMetadata>() {
            {
                add(productTable);
                add(customerFavProductTable);
            }
        };

        StringBuilder criterion = new StringBuilder()
                .append("CUSTOMER_FAV_PRODUCT.CUSTOMER").append("=").append(custId);
        List<String> criteria = new ArrayList<String>() {
            {
                add(criterion.toString());
            }
        };

        NativeQuerySpecs nativeQuerySpecs = new NativeQuerySpecs(tables)
                .addCriteria(criteria)
                .startFrom(startPosition);

        try {
            
            return productRepository.findAll(nativeQuerySpecs);
            
        } catch (RetrievalException ex) { 
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::viewMyFavoriteList(int custId, int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
        }
        
    }

    @Override
    public int getFavoriteProductsCount(int custId) {
        
        StringBuilder havingClause = new StringBuilder()
                .append("CUSTOMER").append("=").append(custId);

        List<String> fields = new ArrayList<String>() {
            {
                add("COUNT(*)");
                add("CUSTOMER");
            }
        };
        List<String> groupBy = new ArrayList<String>() {
            {
                add("CUSTOMER");
            }
        }; 
        List<String> having = new ArrayList<String>() {
            {
                add(havingClause.toString());
            }
        }; 
        TableMetadata tableMetadata = new TableMetadata("CUSTOMER_FAV_PRODUCT", fields);
        NativeQuerySpecs nativeQuerySpecs = new NativeQuerySpecs(tableMetadata)
                .groupBy(groupBy)
                .having(having);
        
        try {
            
            return productRepository.getCount(nativeQuerySpecs);
            
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
    public List<ProductModel> getLikedProducts(int custId) {

        TableMetadata productTable = new TableMetadata("PRODUCT", productModelFields.getCommonFields(), "ID");
        TableMetadata customerFavProductTable = new TableMetadata("CUSTOMER_FAV_PRODUCT", null, "PRODUCT");

        List<TableMetadata> tables = new ArrayList<TableMetadata>() {
            {
                add(productTable);
                add(customerFavProductTable);
            }
        };

        StringBuilder criterion = new StringBuilder()
                .append("CUSTOMER_FAV_PRODUCT.CUSTOMER").append("=").append(custId);
        List<String> criteria = new ArrayList<String>() {
            {
                add(criterion.toString());
            }
        };

        NativeQuerySpecs nativeQuerySpecs = new NativeQuerySpecs(tables)
                .addCriteria(criteria);

        try {
            
            return productRepository.findAll(nativeQuerySpecs);
            
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

        StringBuilder productClassificationCriterion = new StringBuilder();

        if (productClassification instanceof Vendor) {
            productClassificationCriterion.append("VENDOR=").append(((Vendor) productClassification).getId());
        } else if (productClassification instanceof Category) {
            productClassificationCriterion.append("CATEGORY=").append(((Category) productClassification).getId());
        }

        List<String> fields = new ArrayList<String>() {
            {
                add("COUNT(*)");
            }
        };
        List<String> criteria = new ArrayList<String>() {
            {
                add("NOT_DELETED=1");
                add("AND");
                add(productClassificationCriterion.toString());
            }
        };
        TableMetadata tableMetadata = new TableMetadata("PRODUCT", fields);
        NativeQuerySpecs nativeQuerySpecs = new NativeQuerySpecs(tableMetadata)
                .addCriteria(criteria);

        try {
            
            return productRepository.getCount(nativeQuerySpecs);
            
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
