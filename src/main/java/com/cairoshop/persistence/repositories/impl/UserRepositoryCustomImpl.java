package com.cairoshop.persistence.repositories.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cairoshop.persistence.repositories.UserRepositoryCustom;
import com.cairoshop.web.dtos.UserInBriefDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class UserRepositoryCustomImpl
            implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserInBriefDTO> findAllCustomers(int startPosition, int pageSize, String sortBy, String sortDirection) {
        StringBuilder query = new StringBuilder()
                                    .append("SELECT u.id, u.name, u.active ")
                                    .append("FROM user u INNER JOIN role r ON (u.role = r.id AND r.name = :role) ")
                                    .append("ORDER BY ").append(sortBy).append(" ").append(sortDirection);
        return entityManager.createNativeQuery(query.toString(), "UserInBriefDTOMapping")
                                .setParameter("role", "ROLE_CUSTOMER")
                                .setMaxResults(pageSize)
                                .setFirstResult(startPosition)
                                .getResultList();
    }

    @Override
    public int countAllCustomers() {
        StringBuilder query = new StringBuilder()
            .append("SELECT COUNT(*) ")
            .append("FROM user u INNER JOIN role r ON (u.role = r.id AND r.name = :role) ");
        return ((BigInteger) entityManager.createNativeQuery(query.toString())
                                            .setParameter("role", "ROLE_CUSTOMER")
                                            .getSingleResult())
                                        .intValue();
    }
    
}
