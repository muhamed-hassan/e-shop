package com.cairoshop.service.impl;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.ProductRepository;
import com.cairoshop.service.ProductService;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.NewProductDTO;
import com.cairoshop.web.dtos.SavedBriefProductDTO;
import com.cairoshop.web.dtos.SavedDetailedProductDTO;
import com.cairoshop.web.dtos.SavedImageStream;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class ProductServiceImpl extends BaseServiceImpl<NewProductDTO, SavedBriefProductDTO, Product> implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductServiceImpl() {
        super(Product.class, SavedBriefProductDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(productRepository);
    }

    @Transactional
    @Override
    public int add(NewProductDTO newProductDTO) {
        Integer id = -1;
        try {
            Product product = new Product();
            product.setName(newProductDTO.getName());
            product.setDescription(newProductDTO.getDescription());
            product.setPrice(newProductDTO.getPrice());
            product.setQuantity(newProductDTO.getQuantity());
            Vendor vendor = new Vendor();
            vendor.setId(newProductDTO.getVendorId());
            product.setVendor(vendor);
            Category category = new Category();
            category.setId(newProductDTO.getCategoryId());
            product.setCategory(category);
            product.setActive(true);
            id = productRepository.save(product).getId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public SavedDetailedProductDTO getInDetailById(int id) {
        return productRepository.findById(id, SavedDetailedProductDTO.class)
                                    .orElseThrow(NoResultException::new);
    }

    @Override
    public byte[] getImage(int id) {
        Optional<SavedImageStream> savedImageStream = productRepository.findImageById(id, SavedImageStream.class);
        if (savedImageStream.isEmpty()) {
            throw new NoResultException();
        }
        return savedImageStream.get().getImage();
    }

    @Transactional
    @Override
    public void edit(SavedDetailedProductDTO savedDetailedProductDTO) {
        int affectedRows = productRepository.update(savedDetailedProductDTO.getId(), savedDetailedProductDTO.getName(),
                                                        savedDetailedProductDTO.getPrice(), savedDetailedProductDTO.getQuantity(),
                                                        savedDetailedProductDTO.getCategoryId(), savedDetailedProductDTO.getVendorId());
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

}
