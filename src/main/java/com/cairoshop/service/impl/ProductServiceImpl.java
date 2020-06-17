package com.cairoshop.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.repositories.ProductRepository;
import com.cairoshop.service.ProductService;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.NewProductDTO;
import com.cairoshop.web.dtos.SavedBriefProductDTO;
import com.cairoshop.web.dtos.SavedCategoryDTO;
import com.cairoshop.web.dtos.SavedDetailedProductDTO;

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

    public SavedDetailedProductDTO getInDetailById(int id) {
        return productRepository.findById(id, SavedDetailedProductDTO.class).get();
    }

    @Override
    public void edit(SavedDetailedProductDTO savedDetailedProductDTO) {
        productRepository.update(savedDetailedProductDTO.getName(), savedDetailedProductDTO.getPrice(), savedDetailedProductDTO.getQuantity(),
            savedDetailedProductDTO.getCategoryId(), savedDetailedProductDTO.getVendorId(), savedDetailedProductDTO.getId());
    }
}
