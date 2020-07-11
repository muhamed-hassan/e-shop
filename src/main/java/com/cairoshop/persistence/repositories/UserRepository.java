package com.cairoshop.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.web.dtos.UserInBriefDTO;
import com.cairoshop.web.dtos.UserInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
//@Repository
public interface UserRepository
            extends BaseCommonRepository<UserInDetailDTO> {

    List<UserInBriefDTO> findAllCustomers(int startPosition, int pageSize, String sortBy, String sortDirection);

    int countAllCustomers();

//    @Query("UPDATE User u SET u.active = ?2, u.enabled = ?2 WHERE u.id =?1")
//    @Modifying
    int update(int id, boolean newState);

    Optional<User> findByUsername(String username);

}
