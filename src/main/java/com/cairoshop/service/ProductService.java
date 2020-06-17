package com.cairoshop.service;

import java.util.List;
import java.util.Map;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Product;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.NewProductDTO;
import com.cairoshop.web.dtos.SavedBriefProductDTO;
import com.cairoshop.web.dtos.SavedCategoryDTO;
import com.cairoshop.web.dtos.SavedDetailedProductDTO;

public interface ProductService extends BaseService<NewProductDTO, SavedBriefProductDTO, Product> {

    SavedDetailedProductDTO getInDetailById(int id);

    void edit(SavedDetailedProductDTO savedDetailedProductDTO);
}
