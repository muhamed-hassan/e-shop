package com.cairoshop.persistence.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseProductClassificationRepositoryImpl<T, DDTO, BDTO> 
            extends BaseRepositoryImpl<T, DDTO, BDTO>
            implements BaseProductClassificationRepository<T, DDTO, BDTO> {

    protected BaseProductClassificationRepositoryImpl(Class<T> entityClass, Class<DDTO> ddtoClass, Class<BDTO> bdtoClass) {
        super(entityClass, ddtoClass, bdtoClass);
    }

    @Override
    public List<BDTO> findAll() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<BDTO> criteriaQuery = criteriaBuilder.createQuery(getBdtoClass());
        Root<T> root = criteriaQuery.from(getEntityClass());
        List<String> fields = new ArrayList<>();
        getFields(fields, getBdtoClass());
        List<Selection<?>> selections = fields.stream()
                                                .map(field -> root.get(field))
                                                .collect(Collectors.toList());
        criteriaQuery.select(criteriaBuilder.construct(getBdtoClass(), selections.toArray(new Selection[selections.size()])));
        return getEntityManager().createQuery(criteriaQuery).getResultList();
    }

    @Override
    public boolean safeToDelete(int productClassificationId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> cqOfProductClassification = cb.createQuery(Long.class);
        Root<Product> productRoot = cqOfProductClassification.from(Product.class);
        cqOfProductClassification.select(cb.count(productRoot))
                                    .where(cb.and(cb.equal(productRoot.get(getEntityClass().getSimpleName().toLowerCase()).get("id"), productClassificationId),
                                                    cb.equal(productRoot.get("active"), true)));
        int countOfActiveProductClassification = getEntityManager().createQuery(cqOfProductClassification)
                                                                    .getSingleResult()
                                                                    .intValue();
        return countOfActiveProductClassification == 0;
    }

}
