package com.cairoshop.persistence.repositories.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.repositories.BaseRepository;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseRepositoryImpl<T, DDTO, BDTO>
            extends BaseCommonRepositoryImpl<T, DDTO>
            implements BaseRepository<T, DDTO, BDTO> {

    private Class<BDTO>  bdtoClass;

    protected BaseRepositoryImpl(Class<T> entityClass, Class<DDTO> ddtoClass, Class<BDTO>  bdtoClass) {
        super(entityClass, ddtoClass);
        this.bdtoClass = bdtoClass;
    }

    protected Class<BDTO> getBdtoClass() {
        return bdtoClass;
    }

    @Override
    public List<BDTO> findAllByPage(int startPosition, int pageSize, String sortBy, String sortDirection) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BDTO> criteriaQuery = criteriaBuilder.createQuery(getBdtoClass());
        Root<T> root = criteriaQuery.from(getEntityClass());
        List<String> fields = new ArrayList<>();
        getFields(fields, getBdtoClass());
        List<Selection<?>> selections = fields.stream()
                                                .map(field -> root.get(field))
                                                .collect(Collectors.toList());
        criteriaQuery.select(criteriaBuilder.construct(getBdtoClass(), selections.toArray(new Selection[selections.size()])))
                        .where(criteriaBuilder.equal(root.get("active"), true))
                        .orderBy(sortDirection.equals("ASC") ? criteriaBuilder.asc(root.get(sortBy)) : criteriaBuilder.desc(root.get(sortBy)));
        return getEntityManager().createQuery(criteriaQuery)
                                    .setMaxResults(pageSize)
                                    .setFirstResult(startPosition)
                                    .getResultList();
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
        criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), id));
        return getEntityManager().createQuery(criteriaUpdate).executeUpdate();
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
        return getEntityManager().createQuery(criteriaQuery).getSingleResult().intValue();
    }

    @Transactional
    @Override
    public int deleteById(int id) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<T> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(getEntityClass());
        Root<T> root = criteriaUpdate.from(getEntityClass());
        criteriaUpdate.set(root.get("active"), false)
                        .where(criteriaBuilder.equal(root.get("id"), id));
        return getEntityManager().createQuery(criteriaUpdate).executeUpdate();
    }

}
