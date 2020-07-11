package com.cairoshop.persistence.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.cairoshop.persistence.repositories.BaseCommonRepository;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseCommonRepositoryImpl<T, DDTO>
            implements BaseCommonRepository<DDTO> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityClass;
    private Class<DDTO> ddtoClass;

    protected BaseCommonRepositoryImpl(Class<T> entityClass, Class<DDTO> ddtoClass) {
        this.entityClass = entityClass;
        this.ddtoClass = ddtoClass;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    protected Class<DDTO> getDdtoClass() {
        return ddtoClass;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public DDTO findById(int id) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DDTO> criteriaQuery = criteriaBuilder.createQuery(getDdtoClass());
        Root<T> root = criteriaQuery.from(getEntityClass());
        List<String> fields = new ArrayList<>();
        getFields(fields, getDdtoClass());
        List<Selection<?>> selections = fields.stream()
                                                .map(field -> root.get(field))
                                                .collect(Collectors.toList());
        criteriaQuery.select(criteriaBuilder.construct(getDdtoClass(), selections.toArray(new Selection[selections.size()])))
                        .where(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), id),
                                                    criteriaBuilder.equal(root.get("active"), true)));
        return getEntityManager().createQuery(criteriaQuery).getSingleResult();
    }

    protected void getFields(List<String> fields, Class<?> current) {
        if (current.equals(Object.class)) return;

        fields.addAll(Stream.of(current.getDeclaredFields())
                            .map(field -> field.getName())
                            .collect(Collectors.toList()));

        getFields(fields, current.getSuperclass());
    }

}
