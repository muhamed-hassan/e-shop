package com.cairoshop.service;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.web.dtos.NewCustomerDTO;
import com.cairoshop.web.dtos.SavedCustomerDTO;

public interface UserService extends BaseService<NewCustomerDTO, SavedCustomerDTO, User> {

}
