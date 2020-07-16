package com.cairoshop.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.web.dtos.ProductInBriefDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public interface ProductRepository
            extends BaseRepository<Product, ProductInDetailDTO, ProductInBriefDTO> {

    @Query("SELECT p.image "
        + "FROM Product p "
        + "WHERE p.id = ?1 AND p.active = true AND p.imageUploaded = true")
    byte[] findImageByProductId(int id);

    @Query("SELECT new com.cairoshop.web.dtos.ProductInDetailDTO(p.price, p.quantity, p.description, p.category.id, p.vendor.id, p.imageUploaded, p.name) "
        + "FROM Product p "
        + "WHERE p.id = ?1 AND p.active = ?2")
    Optional<ProductInDetailDTO> findByIdAndActive(int id, boolean active);

    Page<ProductInBriefDTO> findAllByActiveAndNameLike(boolean active, String name, Pageable pageable, Class<ProductInBriefDTO> briefDtoClass);

}
