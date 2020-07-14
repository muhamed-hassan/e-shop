package com.cairoshop.persistence.repositories.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.repositories.ProductRepository;
import com.cairoshop.web.dtos.ProductInBriefDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public class ProductRepositoryImpl 
            extends BaseRepositoryImpl<Product, ProductInDetailDTO, ProductInBriefDTO>
            implements ProductRepository {

    public ProductRepositoryImpl() {
        super(Product.class, ProductInDetailDTO.class, ProductInBriefDTO.class);
    }

    @Override
    public Optional<byte[]> findImageByProductId(int id) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<byte[]> criteriaQuery = criteriaBuilder.createQuery(byte[].class);
        Root<Product> root = criteriaQuery.from(getEntityClass());
        criteriaQuery.select(root.get("image"))
                        .where(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), id),
                                criteriaBuilder.equal(root.get("active"), true)));
        return Optional.ofNullable(getEntityManager().createQuery(criteriaQuery).getSingleResult());
    }

    @Override
    public int update(int id, byte[] image) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Product> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(getEntityClass());
        Root<Product> root = criteriaUpdate.from(getEntityClass());
        criteriaUpdate.set(root.get("image"), image)
                        .set(root.get("imageUploaded"), true)
                        .where(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), id),
                                                    criteriaBuilder.equal(root.get("active"), true)));
        return getEntityManager().createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public List<ProductInBriefDTO> search(String name, int startPosition, int pageSize, String sortBy, String sortDirection) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ProductInBriefDTO> criteriaQuery = criteriaBuilder.createQuery(getBriefDtoClass());
        Root<Product> root = criteriaQuery.from(getEntityClass());
        List<String> fields = new ArrayList<>();
        getReflectionUtils().getFields(fields, getBriefDtoClass());
        List<Selection<?>> selections = fields.stream()
                                                .map(field -> root.get(field))
                                                .collect(Collectors.toList());
        criteriaQuery.select(criteriaBuilder.construct(getBriefDtoClass(), selections.toArray(new Selection[selections.size()])))
                        .where(criteriaBuilder.like(root.get("name"),"%" + name + "%"))
                        .orderBy(sortDirection.equals("ASC") ? criteriaBuilder.asc(root.get(sortBy)) : criteriaBuilder.desc(root.get(sortBy)));
        return getEntityManager().createQuery(criteriaQuery)
                                    .setMaxResults(pageSize)
                                    .setFirstResult(startPosition)
                                    .getResultList();
    }

    @Override
    public int countAllByCriteria(String name) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(criteriaBuilder.count(root))
                        .where(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), true),
                                                    criteriaBuilder.like(root.get("name"), "%" + name + "%")));
        return getEntityManager().createQuery(criteriaQuery).getSingleResult().intValue();
    }

    @Override
    public ProductInDetailDTO findById(int id) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ProductInDetailDTO> criteriaQuery = criteriaBuilder.createQuery(getDetailedDtoClass());
        Root<Product> root = criteriaQuery.from(getEntityClass());
        List<String> fields = new ArrayList<>();
        getReflectionUtils().getFields(fields, getDetailedDtoClass());
        List<Selection<?>> selections = fields.stream()
                                                .map(field -> field.endsWith("Id") ?
                                                                root.get(field.replace("Id", "")).get("id") :
                                                                root.get(field))
                                                .collect(Collectors.toList());
        criteriaQuery.select(criteriaBuilder.construct(getDetailedDtoClass(), selections.toArray(new Selection[selections.size()])))
                        .where(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), id),
                                                    criteriaBuilder.equal(root.get("active"), true)));
        return getEntityManager().createQuery(criteriaQuery).getSingleResult();
    }

    @Transactional
    @Override
    public int update(int id, ProductInDetailDTO detailedDtoClass) throws Exception {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Product> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(getEntityClass());
        Root<Product> root = criteriaUpdate.from(getEntityClass());
        Method[] dtoMethods = getDetailedDtoClass().getMethods();
        for (Method method : dtoMethods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && !methodName.equals("getClass")) {
                String field = methodName.replace("get", "");
                field = field.substring(0, 1).toLowerCase() + field.substring(1);
                Object valueFromDto = method.invoke(detailedDtoClass);
                criteriaUpdate.set(field.endsWith("Id") ?
                                    root.get(field.replace("Id", "")).get("id") :
                                    root.get(field), valueFromDto);
            }
        }
        criteriaUpdate.where(criteriaBuilder.equal(root.get("id"), id));
        return getEntityManager().createQuery(criteriaUpdate).executeUpdate();
    }

}
