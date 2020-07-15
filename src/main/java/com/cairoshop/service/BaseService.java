package com.cairoshop.service;

import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseService<D, B>
            extends BaseCommonService<D> {

    int add(D detailedDto);

    void removeById(int id);

    SavedItemsDTO<B> getAll(int startPosition, String sortBy, String sortDirection);

}
