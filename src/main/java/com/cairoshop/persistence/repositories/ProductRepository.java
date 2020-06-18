package com.cairoshop.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.web.dtos.SavedBriefProductDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public interface ProductRepository extends BaseRepository<SavedBriefProductDTO, Product, Integer> {

    @Override
    <SavedDetailedProductDTO> Optional<SavedDetailedProductDTO> findById(int id, Class<SavedDetailedProductDTO> sdtoClass);

    @Query("UPDATE Product p " +
            "SET p.name = ?1, p.price = ?2, p.quantity = ?3,  p.category.id = ?4, p.vendor.id = ?5 " +
            "WHERE p.id = ?6")
    @Modifying
    int update(String name, double price, int quantity, int categoryId, int vendorId, int id);

}
