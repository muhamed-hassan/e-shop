package com.cairoshop.persistence.repositories.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cairoshop.persistence.repositories.ProductRepositoryCustom;
import com.cairoshop.web.dtos.SavedBriefProductDTO;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SavedBriefProductDTO> search(String name, int startPosition, int pageSize, String sortBy, String sortDirection) {
        StringBuilder query = new StringBuilder()
            .append("SELECT id, name ")
            .append("FROM product ")
            .append("WHERE active = true AND name LIKE :keyword ")
            .append("ORDER BY ").append(sortBy).append(" ").append(sortDirection);
        return ((List<Object[]>) entityManager.createNativeQuery(query.toString())
                                                .setParameter("keyword", "%" + name + "%")
                                                .setMaxResults(pageSize)
                                                .setFirstResult(startPosition)
                                                .getResultList())
                                                .stream()
                                                .map(record -> new SavedBriefProductDTO((Integer) record[0], (String) record[1], true))
                                                .collect(Collectors.toList());
    }

    @Override
    public int countAllByCriteria(String name) {
        return ((BigInteger) entityManager.createNativeQuery("SELECT COUNT(*) FROM product WHERE active = true AND name LIKE ?1")
                                    .setParameter(1, "%" + name + "%")
                                    .getSingleResult())
                                        .intValue();
    }

}
