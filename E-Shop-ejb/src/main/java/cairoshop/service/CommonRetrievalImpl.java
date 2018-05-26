package cairoshop.service;

import cairoshop.entities.*;
import cairoshop.repositories.interfaces.*;
import cairoshop.service.helpers.CommonQuerySpecs;
import cairoshop.service.interfaces.CommonRetrieval;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class CommonRetrievalImpl 
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
    public List<Category> viewCategories(Integer startPosition) {
        
        try {
            return categoryRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY, startPosition);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        
    }

    @Override
    public int getCategoriesCount() {
        
        try {
            return categoryRepository.getCount(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        
    }
    
    @Override
    public int getVendorsCount() {
        
        try {
            return vendorRepository.getCount(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        
    }

    @Override
    public List<Vendor> getAllVendors() {
        
        try {
            return vendorRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        
    }

    @Override
    public List<Category> getAllCategories() {
        
        try {
            return categoryRepository.findAll(CommonQuerySpecs.FIND_NOT_DELETED_ITEMS_QUERY);
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
