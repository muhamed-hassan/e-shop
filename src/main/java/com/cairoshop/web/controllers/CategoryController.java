package com.cairoshop.web.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.service.CategoryService;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.SavedBriefCategoryDTO;
import com.cairoshop.web.dtos.SavedDetailedCategoryDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@RestController
@RequestMapping("categories")
@Validated
public class CategoryController extends BaseProductClassificationController<NewCategoryDTO, SavedDetailedCategoryDTO, SavedBriefCategoryDTO, Category> {

    @Autowired
    private CategoryService categoryService;

    @PostConstruct
    public void injectRefs() {
        setService(categoryService);
    }

}
