package com.cairoshop.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.service.CategoryService;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.SavedBriefCategoryDTO;
import com.cairoshop.web.dtos.SavedDetailedCategoryDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class CategoryServiceImpl
                extends BaseProductClassificationServiceImpl<NewCategoryDTO, SavedDetailedCategoryDTO, SavedBriefCategoryDTO, Category>
                implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl() {
        super(Category.class, SavedDetailedCategoryDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(categoryRepository);
    }

}
