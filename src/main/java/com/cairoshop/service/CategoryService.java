package com.cairoshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.SavedCategoryDTO;

public interface CategoryService extends BaseService<NewCategoryDTO, SavedCategoryDTO, Category> {


}
