package com.cairoshop.service;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.web.dtos.NewProductDTO;
import com.cairoshop.web.dtos.SavedBriefProductDTO;
import com.cairoshop.web.dtos.SavedDetailedProductDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface ProductService extends BaseService<NewProductDTO, SavedBriefProductDTO, Product> {

    SavedDetailedProductDTO getInDetailById(int id);

    void edit(SavedDetailedProductDTO savedDetailedProductDTO);

}
