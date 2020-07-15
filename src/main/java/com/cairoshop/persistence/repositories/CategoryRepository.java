package com.cairoshop.persistence.repositories;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.web.dtos.CategoryInBriefDTO;
import com.cairoshop.web.dtos.CategoryInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface CategoryRepository
            extends BaseProductClassificationRepository<Category, CategoryInDetailDTO, CategoryInBriefDTO> {}
