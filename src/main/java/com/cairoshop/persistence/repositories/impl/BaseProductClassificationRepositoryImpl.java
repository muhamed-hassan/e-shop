package com.cairoshop.persistence.repositories.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;

public class BaseProductClassificationRepositoryImpl<T, DDTO, BDTO> 
    extends BaseRepositoryImpl<T, DDTO, BDTO> 
    implements BaseProductClassificationRepository<T, DDTO, BDTO> {


    protected BaseProductClassificationRepositoryImpl(Class<T> entityClass, Class<DDTO> ddtoClass, Class<BDTO> bdtoClass) {
        super(entityClass, ddtoClass, bdtoClass);
    }

    @Override
    public List<BDTO> findAll() {
        //getEntityManager()
        List<BDTO> authors = null;

        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<BDTO> criteriaQuery = criteriaBuilder.createQuery(getBdtoClass());
            Root<T> root = criteriaQuery.from(getEntityClass());
            //Selection<?>... selections
            List<String> fields = new ArrayList<>();
            getFields(fields, getBdtoClass());
            List<Selection<?>> selections = fields.stream().map(field -> root.get(field)).collect(Collectors.toList());

            criteriaQuery.select(criteriaBuilder.construct(getBdtoClass(), selections.toArray(new Selection[selections.size()])));

            TypedQuery<BDTO> query = getEntityManager().createQuery(criteriaQuery);
            authors = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return authors;
    }
}
