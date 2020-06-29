package com.cairoshop.persistence.repositories;

import java.util.List;

import com.cairoshop.web.dtos.SavedBriefCustomerDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface UserRepositoryCustom {

    List<SavedBriefCustomerDTO> findAllCustomers(int startPosition, int pageSize, String sortBy, String sortDirection);

    int countAllCustomers();

}
