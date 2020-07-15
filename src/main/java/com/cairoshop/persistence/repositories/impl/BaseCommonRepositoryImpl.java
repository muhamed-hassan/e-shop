package com.cairoshop.persistence.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;

import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.persistence.utils.ReflectionUtils;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseCommonRepositoryImpl<T, D>
            implements BaseCommonRepository<D> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ReflectionUtils reflectionUtils;

    private Class<T> entityClass;
    private Class<D> detailedDtoClass;

    protected BaseCommonRepositoryImpl(Class<T> entityClass, Class<D> detailedDtoClass) {
        this.entityClass = entityClass;
        this.detailedDtoClass = detailedDtoClass;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    protected Class<D> getDetailedDtoClass() {
        return detailedDtoClass;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected ReflectionUtils getReflectionUtils() {
        return reflectionUtils;
    }

    @Override
    public D findById(int id) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<D> criteriaQuery = criteriaBuilder.createQuery(getDetailedDtoClass());
        Root<T> root = criteriaQuery.from(getEntityClass());
        List<String> fields = new ArrayList<>();
        reflectionUtils.getFields(fields, getDetailedDtoClass());
        List<Selection<?>> selections = fields.stream()
                                                .map(field -> root.get(field))
                                                .collect(Collectors.toList());
        criteriaQuery.select(criteriaBuilder.construct(getDetailedDtoClass(), selections.toArray(new Selection[selections.size()])))
                        .where(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), id),
                                                    criteriaBuilder.equal(root.get("active"), true)));
        return getEntityManager().createQuery(criteriaQuery).getSingleResult();
    }

}
