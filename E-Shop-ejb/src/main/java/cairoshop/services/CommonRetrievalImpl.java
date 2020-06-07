package cairoshop.services;

import java.util.List;

import org.apache.logging.log4j.Level;

import cairoshop.entities.Category;
import cairoshop.entities.Vendor;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.interfaces.CategoryRepository;
import cairoshop.repositories.interfaces.ProductRepository;
import cairoshop.repositories.interfaces.VendorRepository;
import cairoshop.services.helpers.CommonQuerySpecs;
import cairoshop.services.interfaces.CommonRetrieval;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class CommonRetrievalImpl extends BaseService implements CommonRetrieval {

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
    public List<Category> getCategories() {

        try {

            return categoryRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);

        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getCategories()",
                            getClass(),
                            ex
                    );
            return null;
        }

    }
    
     @Override
    public List<Category> getCategories(int startPosition) {

        try {

            return categoryRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY, startPosition);

        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getCategories(int startPosition)",
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
    public List<Vendor> getVendors() {

        try {

            return vendorRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);

        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getVendors()",
                            getClass(),
                            ex
                    );
            return null;
        }

    }

    @Override
    public List<Vendor> getVendors(int startPosition) {

        try {

            return vendorRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY, startPosition);

        } catch (RetrievalException ex) {
            getGlobalLogger()
                    .doLogging(
                            Level.ERROR,
                            "Caller::getVendors(int startPosition)",
                            getClass(),
                            ex
                    );
            return null;
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
