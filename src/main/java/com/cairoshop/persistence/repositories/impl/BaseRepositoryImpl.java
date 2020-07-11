package com.cairoshop.persistence.repositories.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.web.dtos.UserInBriefDTO;

public class BaseRepositoryImpl<T, DDTO, BDTO>
    extends BaseCommonRepositoryImpl<T, DDTO>
    implements BaseRepository<T, DDTO, BDTO> {

    private Class<BDTO>  bdtoClass;

    public BaseRepositoryImpl(Class<T> entityClass, Class<DDTO> ddtoClass, Class<BDTO>  bdtoClass) {
        super(entityClass, ddtoClass);
        this.bdtoClass = bdtoClass;
    }

    public Class<BDTO> getBdtoClass() {
        return bdtoClass;
    }

    /*
     List<String> fields = new ArrayList<>();
        getFields(fields, ddtoClass);
        fields = fields.stream()
            .map(field -> "d."+field)
            .collect(Collectors.toList());
        StringBuilder query = new StringBuilder()
            .append("SELECT new ").append(ddtoClass.getCanonicalName()).append("(")
            .append(fields).append(") ")
            .append("FROM ").append(entityClass.getName()).append(" d ").append(" WHERE id = ?1");

        try {
            return Optional.of((DDTO) entityManager.createQuery(query.toString(), ddtoClass)
                .setParameter(1, id)
                .getSingleResult());
        } catch (NoResultException nre) {
        return Optional.empty();
        }
    * */

    @Override
    public List<BDTO> findAllByPage(int startPosition, int pageSize, String sortBy, String sortDirection) {

//        List<String> fields = new ArrayList<>();
//        getFields(fields, bdtoClass);
//        fields = fields.stream()
//            .map(field -> "b."+field)
//            .collect(Collectors.toList());
//        StringBuilder query = new StringBuilder()
//            .append("SELECT new ").append(bdtoClass.getCanonicalName()).append("(")
//            .append(fields).append(") ")
//            .append("FROM ").append(getEntityClass().getName()).append(" b ").append("WHERE active = :isActive ")
//            .append("ORDER BY ").append(sortBy).append(" ").append(sortDirection);
//
//
//        return getEntityManager().createNativeQuery(query.toString(), bdtoClass)
//            .setParameter("isActive", true)
//            .setMaxResults(pageSize)
//            .setFirstResult(startPosition)
//            .getResultList();

        /*CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BDTO> criteriaQuery = criteriaBuilder.createQuery(getBdtoClass());
        Root<T> root = criteriaQuery.from(getEntityClass());
        //Selection<?>... selections
        List<String> fields = new ArrayList<>();
        getFields(fields, getBdtoClass());
        List<Selection<?>> selections = fields.stream().map(field -> root.get(field)).collect(Collectors.toList());

        criteriaQuery.select(criteriaBuilder.construct(getBdtoClass(), selections.toArray(new Selection[selections.size()])))
            .where(criteriaBuilder.equal(root.get("active"), true));

        List<BDTO> authors = getEntityManager().createQuery(criteriaQuery)
            .setMaxResults(pageSize)
            .setFirstResult(startPosition)
            .getResultList();

        return authors;*/

        List<BDTO> authors = null;

        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<BDTO> criteriaQuery = criteriaBuilder.createQuery(getBdtoClass());
            Root<T> root = criteriaQuery.from(getEntityClass());
            //Selection<?>... selections
            List<String> fields = new ArrayList<>();
            getFields(fields, getBdtoClass());
            List<Selection<?>> selections = fields.stream().map(field -> root.get(field)).collect(Collectors.toList());

            criteriaQuery.select(criteriaBuilder.construct(getBdtoClass(), selections.toArray(new Selection[selections.size()])))
                .where(criteriaBuilder.equal(root.get("active"), true))
            .orderBy(sortDirection.equals("ASC") ? criteriaBuilder.asc(root.get(sortBy)) : criteriaBuilder.desc(root.get(sortBy)));


            authors = getEntityManager().createQuery(criteriaQuery)
                .setMaxResults(pageSize)
                .setFirstResult(startPosition)
                .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return authors;
    }

    @Transactional
    @Override
    public int update(int id, DDTO ddto) throws Exception {

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<T> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(getEntityClass());
        Root<T> root = criteriaUpdate.from(getEntityClass());


        Method[] dtoMethods = getDdtoClass().getMethods();
        for (Method method : dtoMethods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && !methodName.equals("getClass")) {
                String field = methodName.replace("get", "");
                field = field.substring(0, 1).toLowerCase() + field.substring(1);
                Object valueFromDto = method.invoke(ddto);
                criteriaUpdate.set(root.get(field), valueFromDto);
            }
        }

        criteriaUpdate
            .where(criteriaBuilder.equal(root.get("id"), id));
        int affectedRows = getEntityManager().createQuery(criteriaUpdate).executeUpdate();
        return affectedRows;
    }
    @Override
    public int save(T entity) throws Exception {
        getEntityManager().persist(entity);
        return (int) entity.getClass().getMethod("getId").invoke(entity);
    }
    @Override
    public int countAllActive() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = criteriaQuery.from(getEntityClass());
        criteriaQuery.select(criteriaBuilder.count(root))
            .where(criteriaBuilder.equal(root.get("active"), true));
        int countOfAllActive = getEntityManager().createQuery(criteriaQuery).getSingleResult().intValue();


        return countOfAllActive;
    }
    //@Query("UPDATE #{#entityName} e SET e.active = false WHERE e.id = ?1")
    @Transactional
    @Override
    public int deleteById(int id) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<T> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(getEntityClass());
        Root<T> root = criteriaUpdate.from(getEntityClass());
        criteriaUpdate.set(root.get("active"), false).where(criteriaBuilder.equal(root.get("id"), id));

        int affectedRows = getEntityManager().createQuery(criteriaUpdate).executeUpdate();
        return affectedRows;
    }
}
