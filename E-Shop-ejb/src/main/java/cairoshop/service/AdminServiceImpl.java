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
            ex.printStackTrace();
            return false;
        }
        
    }

    @Override
    public boolean editCategory(Category category) {
        
        try {
            categoryRepository.update(category);
            return true;
        } catch (ModificationException ex) {
            ex.printStackTrace();
            return false;
        }
        
    }

    @Override
    public boolean deleteCategory(Category category) {

        try {
            categoryRepository.remove(category.getId());
            return true;
        } catch (DeletionException ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean editVendor(Vendor vendor) {

        try {
            vendorRepository.update(vendor);
            return true;
        } catch (ModificationException ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteVendor(Vendor vendor) {

        try {
            vendorRepository.remove(vendor.getId());
            return true;
        } catch (DeletionException ex) {
            ex.printStackTrace();
            return false;
        }

   }

    @Override
    public List<Vendor> viewVendors(int startPosition) {
        
        try {
            return vendorRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY, startPosition);
        } catch (Exception ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public List<User> viewCustomers(int startPosition) {
        
        try {
            return userRepository.findAll(null, startPosition);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        
    }

    @Override
    public int getCustomersCount() {

        try {
            return userRepository.getCount(null);
        } catch (Exception ex) {
            ex.printStackTrace();
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
            ex.printStackTrace();
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean editProduct(Product product) {
        
        try {
            productRepository.update(product);
            return true;
        } catch (ModificationException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProduct(int pId) {
        
        try {
            productRepository.remove(pId);
            return true;
        } catch (DeletionException ex) {
            ex.printStackTrace();
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
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    public int getProductsCount() {
        
        try {
            return productRepository.getCount(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }

    }

}
