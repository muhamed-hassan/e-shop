package com.cairoshop.service;

import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface BaseCommonService<SDDTO, SBDTO, T> {

    SDDTO getById(int id);

    SavedItemsDTO<SBDTO> getAll(int startPosition, String sortBy, String sortDirection);

}
