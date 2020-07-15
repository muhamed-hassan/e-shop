package com.cairoshop.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.web.dtos.ProductInBriefDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface ProductRepository
            extends BaseRepository<Product, ProductInDetailDTO, ProductInBriefDTO> {

    Optional<byte[]> findImageByProductId(int id);

    int update(int id, byte[] image);

    List<ProductInBriefDTO> search(String name, int startPosition, int pageSize, String sortBy, String sortDirection);

    int countAllByCriteria(String name);

}
