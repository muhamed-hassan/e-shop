package com.cairoshop.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.web.dtos.SavedCategoryDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public interface CategoryRepository extends BaseRepository<SavedCategoryDTO, Category, Integer> {

    @Query("UPDATE Category c SET c.name = ?2 WHERE c.id = ?1")
    @Modifying
    int update(int id, String name);

    List<SavedCategoryDTO> findAllBy();

}
