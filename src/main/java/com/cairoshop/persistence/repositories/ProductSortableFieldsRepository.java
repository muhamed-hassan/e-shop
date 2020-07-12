package com.cairoshop.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.ProductSortableFields;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public interface ProductSortableFieldsRepository
            extends JpaRepository<ProductSortableFields, String> {

    List<ProductSortableFields> findAll();

}
