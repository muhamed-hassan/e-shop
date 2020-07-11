package com.cairoshop.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.web.dtos.ProductInBriefDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
//@Repository
public interface ProductRepository
            extends BaseRepository<Product, ProductInDetailDTO, ProductInBriefDTO> {

    Optional<byte[]> findImageByProductId(int id);

//    @Query("UPDATE Product p " +
//            "SET p.name = ?2, p.price = ?3, p.quantity = ?4,  p.category.id = ?5, p.vendor.id = ?6 " +
//            "WHERE p.id = ?1")
//    @Modifying
    //int update(int id, String name, double price, int quantity, int categoryId, int vendorId);

//    @Query("UPDATE Product p " +
//            "SET p.image = ?2, p.imageUploaded = true " +
//            "WHERE p.id = ?1")
//    @Modifying
    int update(int id, byte[] image);

    List<ProductInBriefDTO> search(String name, int startPosition, int pageSize, String sortBy, String sortDirection);

    int countAllByCriteria(String name);

}
