package com.cairoshop.persistence.repositories.impl;

import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.web.dtos.CategoryInBriefDTO;
import com.cairoshop.web.dtos.CategoryInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public class CategoryRepositoryImpl 
            extends BaseProductClassificationRepositoryImpl<Category, CategoryInDetailDTO, CategoryInBriefDTO>
            implements CategoryRepository {

    public CategoryRepositoryImpl() {
        super(Category.class, CategoryInDetailDTO.class, CategoryInBriefDTO.class);
    }

}
