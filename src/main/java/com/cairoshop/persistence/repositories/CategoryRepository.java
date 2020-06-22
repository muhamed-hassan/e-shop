package com.cairoshop.persistence.repositories;

import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.web.dtos.SavedBriefCategoryDTO;
import com.cairoshop.web.dtos.SavedDetailedCategoryDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public interface CategoryRepository extends BaseProductClassificationRepository<SavedBriefCategoryDTO, Category> {}
