package com.cairoshop.persistence.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.web.dtos.UserInBriefDTO;
import com.cairoshop.web.dtos.UserInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public interface UserRepository
            extends BaseRepository<User, UserInDetailDTO, UserInBriefDTO> {

    @Query("SELECT new com.cairoshop.web.dtos.UserInBriefDTO(u.id, u.name, u.active) "
        + "FROM User u "
        + "WHERE u.role.name = 'ROLE_CUSTOMER'")
    Page<UserInBriefDTO> findAllCustomers(Pageable pageable);

    Optional<User> findByUsername(String username);

    @Query("SELECT new com.cairoshop.web.dtos.UserInDetailDTO(u.username, u.email, u.phone, u.address, u.name) "
        + "FROM User u "
        + "WHERE u.id = ?1 AND u.active = ?2")
    Optional<UserInDetailDTO> findByIdAndActive(int id, boolean active);

}
