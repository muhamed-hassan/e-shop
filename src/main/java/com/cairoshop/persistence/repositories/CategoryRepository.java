package com.cairoshop.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.web.dtos.CategoryInBriefDTO;
import com.cairoshop.web.dtos.CategoryInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public interface CategoryRepository
            extends BaseProductClassificationRepository<Category, CategoryInDetailDTO, CategoryInBriefDTO> {

    @Query("SELECT COUNT(p.id) "
        + "FROM Product p "
        + "WHERE p.active = true AND p.category.id = ?1")
    long countOfAssociationsWithProduct(int categoryId);

    @Query("SELECT new com.cairoshop.web.dtos.CategoryInDetailDTO(c.name) "
        + "FROM Category c "
        + "WHERE c.id = ?1 AND c.active = ?2")
    Optional<CategoryInDetailDTO> findByIdAndActive(int id, boolean active);

}
