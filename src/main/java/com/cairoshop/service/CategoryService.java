package com.cairoshop.service;

import java.util.List;
import java.util.Map;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.SavedCategoryDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface CategoryService extends BaseService<NewCategoryDTO, SavedCategoryDTO, Category> {

    void edit(Map<String, Object> fields);

    List<SavedCategoryDTO> getAll();

}
