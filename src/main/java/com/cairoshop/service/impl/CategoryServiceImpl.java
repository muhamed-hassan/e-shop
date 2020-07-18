package com.cairoshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.service.CategoryService;
import com.cairoshop.web.dtos.CategoryInBriefDTO;
import com.cairoshop.web.dtos.CategoryInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class CategoryServiceImpl
            extends BaseProductClassificationServiceImpl<Category, CategoryInDetailDTO, CategoryInBriefDTO>
            implements CategoryService {

    @Autowired
    public CategoryServiceImpl(CategoryRepository repository) {
        super(Category.class, CategoryInBriefDTO.class, repository);
    }

}
