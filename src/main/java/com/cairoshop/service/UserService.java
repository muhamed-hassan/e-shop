package com.cairoshop.service;

import com.cairoshop.web.dtos.SavedItemsDTO;
import com.cairoshop.web.dtos.UserInBriefDTO;
import com.cairoshop.web.dtos.UserInDetailDTO;
import com.cairoshop.web.dtos.UserStatusDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface UserService
            extends BaseCommonService<UserInDetailDTO> {

   void edit(int id, UserStatusDTO userStatusDTO);

   SavedItemsDTO<UserInBriefDTO> getAllCustomers(int startPosition, String sortBy, String sortDirection);

}
