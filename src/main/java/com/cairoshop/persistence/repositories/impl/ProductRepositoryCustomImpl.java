package com.cairoshop.persistence.repositories.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cairoshop.persistence.repositories.ProductRepositoryCustom;
import com.cairoshop.web.dtos.ProductInBriefDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProductInBriefDTO> search(String name, int startPosition, int pageSize, String sortBy, String sortDirection) {
        StringBuilder query = new StringBuilder()
                                    .append("SELECT id, name ")
                                    .append("FROM product ")
                                    .append("WHERE active = :isActive AND name LIKE :keyword ")
                                    .append("ORDER BY ").append(sortBy).append(" ").append(sortDirection);
        return entityManager.createNativeQuery(query.toString(), "ProductInBriefDTOMapping")
                                .setParameter("isActive", true)
                                .setParameter("keyword", "%" + name + "%")
                                .setMaxResults(pageSize)
                                .setFirstResult(startPosition)
                                .getResultList();
    }

    @Override
    public int countAllByCriteria(String name) {
        return ((BigInteger) entityManager.createNativeQuery("SELECT COUNT(*) FROM product WHERE active = :isActive AND name LIKE :keyword")
                                            .setParameter("isActive", true)
                                            .setParameter("keyword", "%" + name + "%")
                                            .getSingleResult())
                                                .intValue();
    }

}
