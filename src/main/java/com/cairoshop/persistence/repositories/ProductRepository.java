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

    <SavedImageStream> Optional<SavedImageStream> findImageById(int id, Class<SavedImageStream> sdtoClass);

    @Query("UPDATE Product p " +
            "SET p.name = ?2, p.price = ?3, p.quantity = ?4,  p.category.id = ?5, p.vendor.id = ?6 " +
            "WHERE p.id = ?1")
    @Modifying
    int update(int id, String name, double price, int quantity, int categoryId, int vendorId);

    @Query("UPDATE Product p " +
            "SET p.image = ?2 " +
            "WHERE p.id = ?1")
    @Modifying
    int update(int id, byte[] image);

}
