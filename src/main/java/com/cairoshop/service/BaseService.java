package com.cairoshop.service;

import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseService<DDTO, BDTO>
            extends BaseCommonService<DDTO> {

    int add(DDTO ddto);

    void removeById(int id);

    SavedItemsDTO<BDTO> getAll(int startPosition, String sortBy, String sortDirection);

}
