package com.cairoshop.persistence.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.web.dtos.SavedCustomerDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public interface UserRepository extends BaseRepository<SavedCustomerDTO, User, Integer> {

    @Query("UPDATE User u SET u.active = ?2 WHERE u.id =?1")
    @Modifying
    int update(int id, boolean newState);

}