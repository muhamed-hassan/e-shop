package com.cairoshop.service.impl;

import static com.cairoshop.configs.Constants.MAX_PAGE_SIZE;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.entities.ProductSortableFields;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.persistence.repositories.ProductRepository;
import com.cairoshop.persistence.repositories.ProductSortableFieldsRepository;
import com.cairoshop.persistence.repositories.VendorRepository;
import com.cairoshop.service.ProductService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
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

    private final VendorRepository vendorRepository;

    private final CategoryRepository categoryRepository;

    private final ProductSortableFieldsRepository productSortableFieldsRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                                VendorRepository vendorRepository,
                                CategoryRepository categoryRepository,
                                ProductSortableFieldsRepository productSortableFieldsRepository) {
        super(Product.class, ProductInBriefDTO.class, productRepository);
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
        this.productSortableFieldsRepository = productSortableFieldsRepository;
    }

    @Transactional
    @Override
    public int add(ProductInDetailDTO productInDetailDTO) {
        int id;
        try {
            var product = Product.builder()
                                            .name(productInDetailDTO.getName())
                                            .price(productInDetailDTO.getPrice())
                                            .quantity(productInDetailDTO.getQuantity())
                                            .description(productInDetailDTO.getDescription())
                                            .category(categoryRepository.getOne(productInDetailDTO.getCategoryId()))
                                            .vendor(vendorRepository.getOne(productInDetailDTO.getVendorId()))
                                        .build();
            id = getRepository().save(product).getId();
        } catch (DataIntegrityViolationException dive) {
            throw new DataIntegrityViolatedException();
        }
        return id;
    }

    @Override
    public byte[] getImage(int id) {
        return Optional.ofNullable(((ProductRepository) getRepository()).findImageByProductId(id))
                        .orElseThrow(NoResultException::new);
    }

    @Transactional
    @Override
    public void edit(int id, ProductInDetailDTO productInDetailDTO) {
        try {
            var product = getRepository().getOne(id);
            product.setName(productInDetailDTO.getName());
            product.setPrice(productInDetailDTO.getPrice());
            product.setQuantity(productInDetailDTO.getQuantity());
            product.setCategory(categoryRepository.getOne(productInDetailDTO.getCategoryId()));
            product.setVendor(vendorRepository.getOne(productInDetailDTO.getVendorId()));
            getRepository().saveAndFlush(product);
        } catch (DataIntegrityViolationException dive) {
            throw new DataIntegrityViolatedException();
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getClass() == EntityNotFoundException.class) {
                throw new NoResultException();
            }
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public void edit(int id, byte[] image) {
        try {
            var product = getRepository().getOne(id);
            product.setImage(image);
            product.setImageUploaded(image != null);
            getRepository().save(product);
        } catch (RuntimeException e) {
            if (e.getCause() != null && e.getCause().getClass() == EntityNotFoundException.class) {
                throw new NoResultException();
            }
            throw new RuntimeException(e);
        }
    }

    @Cacheable(value = "productSortableFieldsCache")
    @Override
    public List<String> getSortableFields() {
        var productSortableFields = productSortableFieldsRepository.findAll();
        if (productSortableFields.isEmpty()) {
            throw new NoResultException();
        }
        return productSortableFields.stream()
                                        .map(ProductSortableFields::getName)
                                        .collect(Collectors.toList());
    }

    @Override
    public SavedItemsDTO<ProductInBriefDTO> searchByProductName(String name, int startPosition, String sortBy, String sortDirection) {
        var pageable = PageRequest.of(startPosition, MAX_PAGE_SIZE, sortFrom(sortBy, sortDirection));
        var page = ((ProductRepository) getRepository())
                                            .findAllByActiveAndNameLike(true, "%" + name + "%", pageable, getBriefDtoClass());
        if (page.isEmpty()) {
            throw new NoResultException();
        }
        return new SavedItemsDTO<>(page.getContent(), (int) page.getTotalElements());
    }

}
