package com.cairoshop.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.web.dtos.UserInBriefDTO;
import com.cairoshop.web.dtos.UserInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface UserRepository
            extends BaseCommonRepository<UserInDetailDTO> {

    List<UserInBriefDTO> findAllCustomers(int startPosition, int pageSize, String sortBy, String sortDirection);

    int countAllCustomers();

    int update(int id, boolean newState);

    Optional<User> findByUsername(String username);

}
