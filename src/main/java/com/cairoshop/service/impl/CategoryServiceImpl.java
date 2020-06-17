package com.cairoshop.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.service.BaseService;
import com.cairoshop.service.CategoryService;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.SavedCategoryDTO;

@Service
public class CategoryServiceImpl extends BaseServiceImpl<NewCategoryDTO, SavedCategoryDTO, Category> implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl() {
        super(Category.class, SavedCategoryDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(categoryRepository);
    }

//    public SavedCategoryDTO getCategoryById(int id) {
//
//        return categoryRepository.findAllById(id);
//    }
//
//    @Transactional
//    @Override
//    public void removeCategoryById(int id) {
//        int affectedRows = categoryRepository.softDeleteById(id);
//    }
//
//    @Override
//    public List<SavedCategoryDTO> getAll() {
//        return categoryRepository.findAllBy();
//    }@Override
//    public List<SavedCategoryDTO> getAll(int startPosition, String sortBy, int sortDirection) {
//
//        Pageable sortedByPriceDesc =
//            PageRequest.of(0, 3, Sort.by("name").descending());
//        return categoryRepository.findAllBy(sortedByPriceDesc);
//    }
}
