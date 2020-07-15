package com.cairoshop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.configs.Constants;
import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.entities.ProductSortableFields;
import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.ProductRepository;
import com.cairoshop.persistence.repositories.ProductSortableFieldsRepository;
import com.cairoshop.service.ProductService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.ProductInBriefDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class ProductServiceImpl
            extends BaseServiceImpl<Product, ProductInDetailDTO, ProductInBriefDTO>
            implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSortableFieldsRepository productSortableFieldsRepository;

    public ProductServiceImpl() {
        super(Product.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(productRepository);
    }

    @Transactional
    @Override
    public int add(ProductInDetailDTO productInDetailDTO) {
        int id;
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
        } catch (DataIntegrityViolationException dive) {
            throw new DataIntegrityViolatedException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public byte[] getImage(int id) {
        byte[] imageStream;
        try {
            imageStream = productRepository.findImageByProductId(id)
                                            .orElseThrow(() -> new EmptyResultDataAccessException(1));
        } catch (EmptyResultDataAccessException erde) {
            throw new NoResultException();
        }
        return imageStream;
    }

    @Transactional
    @Override
    public void edit(int id, ProductInDetailDTO productInDetailDTO) throws Exception {
        int affectedRows;
        try {
            affectedRows = productRepository.update(id, productInDetailDTO);
        } catch (DataIntegrityViolationException dive) {
            throw new DataIntegrityViolatedException();
        }
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

    @Cacheable(value = "productSortableFieldsCache")
    @Override
    public List<String> getSortableFields() {
        List<ProductSortableFields> productSortableFields = productSortableFieldsRepository.findAll();
        if (productSortableFields.isEmpty()) {
            throw new NoResultException();
        }
        return productSortableFields.stream()
                                        .map(ProductSortableFields::getName)
                                        .collect(Collectors.toList());
    }

    @Override
    public SavedItemsDTO<ProductInBriefDTO> searchByProductName(String name, int startPosition, String sortBy, String sortDirection) {
        List<ProductInBriefDTO> page = productRepository.search(name, startPosition, Constants.MAX_PAGE_SIZE, sortBy, sortDirection);
        if (page.isEmpty()) {
            throw new NoResultException();
        }
        int countOfAllItemsThatMetSearchCriteria = productRepository.countAllByCriteria(name);
        SavedItemsDTO<ProductInBriefDTO> savedBriefProductDTOSavedItemsDTO = new SavedItemsDTO<>(page, countOfAllItemsThatMetSearchCriteria);
        return savedBriefProductDTOSavedItemsDTO;
    }

}
