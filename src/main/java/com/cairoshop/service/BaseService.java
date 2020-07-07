package com.cairoshop.service;

import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseService<NDTO, SDDTO, SBDTO, T> extends BaseCommonService<SDDTO, SBDTO, T> {

    int add(NDTO ndto);

    void removeById(int id);

    SavedItemsDTO<SBDTO> getAll(int startPosition, String sortBy, String sortDirection);

}
