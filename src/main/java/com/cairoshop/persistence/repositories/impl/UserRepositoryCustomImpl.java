package com.cairoshop.persistence.repositories.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cairoshop.persistence.repositories.UserRepositoryCustom;
import com.cairoshop.web.dtos.SavedBriefCustomerDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SavedBriefCustomerDTO> findAllCustomers(int startPosition, int pageSize, String sortBy, String sortDirection) {
        StringBuilder query = new StringBuilder()
            .append("SELECT u.id, u.name ")
            .append("FROM user u INNER JOIN role r ON (u.role = r.id AND r.name = :role) ")
            .append("ORDER BY ").append(sortBy).append(" ").append(sortDirection);
        return ((List<Object[]>) entityManager.createNativeQuery(query.toString())
                                                .setParameter("role", "ROLE_CUSTOMER")
                                                .setMaxResults(pageSize)
                                                .setFirstResult(startPosition)
                                                .getResultList())
                                                .stream()
                                                .map(record -> new SavedBriefCustomerDTO((Integer) record[0], (String) record[1], true))
                                                .collect(Collectors.toList());
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
