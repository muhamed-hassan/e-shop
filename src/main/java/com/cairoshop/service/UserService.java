package com.cairoshop.service;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.web.dtos.SavedCustomerDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface UserService extends BaseService<Void, SavedCustomerDTO, User> {

    void edit(SavedCustomerDTO savedCustomerDTO);

}
