package cairoshop.service;

import cairoshop.entities.*;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.*;
import cairoshop.service.helpers.CommonQuerySpecs;
import cairoshop.service.interfaces.CommonRetrieval;
import java.util.List;
import org.apache.logging.log4j.Level;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class CommonRetrievalImpl
        extends BaseService
        implements CommonRetrieval {

    private CategoryRepository categoryRepository;

    private VendorRepository vendorRepository;

    private ProductRepository productRepository;

    public void setRepos(
            CategoryRepository categoryRepository,
            VendorRepository vendorRepository,
            ProductRepository productRepository) {

        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
        this.productRepository = productRepository;

    }

    @Override
    public List<Category> viewCategories(int startPosition) {

        try {

            return categoryRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY, startPosition);

        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::viewCategories(int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
        }

    }

    @Override
    public List<Vendor> getAllVendors() {

        try {

            return vendorRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);

        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getAllVendors()",
                            getClass(),
                            ex
                    );
            return null;
        }

    }

    @Override
    public List<Category> getAllCategories() {

        try {

            return categoryRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);

        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getAllCategories()",
                            getClass(),
                            ex
                    );
            return null;
        }

    }

    @Override
    public int getCategoriesCount() {

        try {
            
            return categoryRepository.getCount(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getCategoriesCount()",
                            getClass(),
                            ex
                    );
            return -1;
        }

    }

    @Override
    public int getVendorsCount() {

        try {
            
            return vendorRepository.getCount(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);
            
        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getVendorsCount()",
                            getClass(),
                            ex
                    );
            return -1;
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
