package com.cairoshop.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.repositories.ProductRepository;
import com.cairoshop.service.ProductService;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.NewProductDTO;
import com.cairoshop.web.dtos.SavedBriefProductDTO;
import com.cairoshop.web.dtos.SavedDetailedProductDTO;

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

    public SavedDetailedProductDTO getInDetailById(int id) {
        return productRepository.findById(id, SavedDetailedProductDTO.class)
                                    .orElseThrow(NoResultException::new);
    }

    @Override
    public void edit(SavedDetailedProductDTO savedDetailedProductDTO) {
        int affectedRows = productRepository.update(savedDetailedProductDTO.getName(), savedDetailedProductDTO.getPrice(),
                                                        savedDetailedProductDTO.getQuantity(), savedDetailedProductDTO.getCategoryId(),
                                                        savedDetailedProductDTO.getVendorId(), savedDetailedProductDTO.getId());
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

}
