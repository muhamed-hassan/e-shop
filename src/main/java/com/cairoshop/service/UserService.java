package com.cairoshop.service;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.web.dtos.NewCustomerStateDTO;
import com.cairoshop.web.dtos.SavedBriefCustomerDTO;
import com.cairoshop.web.dtos.SavedDetailedCustomerDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface UserService extends BaseCommonService<SavedDetailedCustomerDTO, SavedBriefCustomerDTO, User> {

   void edit(NewCustomerStateDTO newCustomerStateDTO);

   SavedItemsDTO<SavedBriefCustomerDTO> findAllCustomers(int startPosition, String sortBy, String sortDirection);

}
