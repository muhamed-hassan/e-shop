package com.cairoshop.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.configs.Constants;
import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.ProductRepository;
import com.cairoshop.service.ProductService;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.ProductInBriefDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class ProductServiceImpl
                extends BaseServiceImpl<Product, ProductInDetailDTO, ProductInBriefDTO>
                implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl() {
        super(Product.class, ProductInDetailDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(productRepository);
    }

    @Transactional
    @Override
    public int add(ProductInDetailDTO productInDetailDTO) {
        int id = -1;
        try {
            Product product = new Product();
            product.setName(productInDetailDTO.getName());
            product.setDescription(productInDetailDTO.getDescription());
            product.setPrice(productInDetailDTO.getPrice());
            product.setQuantity(productInDetailDTO.getQuantity());
            Vendor vendor = new Vendor();
            vendor.setId(productInDetailDTO.getVendorId());
            product.setVendor(vendor);
            Category category = new Category();
            category.setId(productInDetailDTO.getCategoryId());
            product.setCategory(category);
            product.setActive(true);
            id = productRepository.save(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public byte[] getImage(int id) {
        byte[] imageStream;
        try {
            imageStream = productRepository.findImageByProductId(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        } catch (EmptyResultDataAccessException erde) {
            throw new NoResultException();
        }
        return imageStream;
    }

    @Transactional
    @Override
    public void edit(int id, ProductInDetailDTO productInDetailDTO) throws Exception {
        int affectedRows = productRepository.update(id, productInDetailDTO);
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Transactional
    @Override
    public void edit(int id, byte[] image) {
        int affectedRows = productRepository.update(id, image);
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Override
    public List<String> getSortableFields() {
        return Product.SORTABLE_FIELDS;
    }

    @Override
    public SavedItemsDTO<ProductInBriefDTO> searchByProductName(String name, int startPosition, String sortBy, String sortDirection) {
        List<ProductInBriefDTO> page = productRepository.search(name, startPosition, Constants.MAX_PAGE_SIZE, sortBy, sortDirection);
        int countOfItemMetSearchCriteria = productRepository.countAllByCriteria(name);
        SavedItemsDTO<ProductInBriefDTO> savedBriefProductDTOSavedItemsDTO = new SavedItemsDTO<>();
        savedBriefProductDTOSavedItemsDTO.setItems(page);
        savedBriefProductDTOSavedItemsDTO.setAllSavedItemsCount(countOfItemMetSearchCriteria);
        return savedBriefProductDTOSavedItemsDTO;
    }

}
