package com.cairoshop.service;

import java.util.List;

import com.cairoshop.web.dtos.ProductInBriefDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface ProductService
            extends BaseService<ProductInDetailDTO, ProductInBriefDTO> {

    byte[] getImage(int id);

    void edit(int id, ProductInDetailDTO productInDetailDTO) throws Exception;

    void edit(int id, byte[] image);

    List<String> getSortableFields();

    SavedItemsDTO<ProductInBriefDTO> searchByProductName(String name, int startPosition, String sortBy, String sortDirection);

}
