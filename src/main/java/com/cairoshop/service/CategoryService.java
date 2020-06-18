package com.cairoshop.service;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.SavedCategoryDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface CategoryService extends BaseClassificationService<NewCategoryDTO, SavedCategoryDTO, Category> {}
