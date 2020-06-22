package com.cairoshop.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.web.dtos.SavedBriefCustomerDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public interface UserRepository extends BaseCommonRepository<SavedBriefCustomerDTO, User> {

    @Query("UPDATE User u SET u.active = ?2, u.enabled = ?2 WHERE u.id =?1")
    @Modifying
    int update(int id, boolean newState);

    Optional<User> findByUsername(String username);

}
