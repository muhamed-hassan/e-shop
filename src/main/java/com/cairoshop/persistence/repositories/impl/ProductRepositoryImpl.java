package com.cairoshop.persistence.repositories.impl;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.TypedQuery;
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

//    @PersistenceContext
//    private EntityManager entityManager;

    protected ProductRepositoryImpl() {
        super(Product.class, ProductInDetailDTO.class, ProductInBriefDTO.class);
    }

    @Override
    public Optional<byte[]> findImageByProductId(int id) {

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<byte[]> criteriaQuery = criteriaBuilder.createQuery(byte[].class);
        Root<Product> root = criteriaQuery.from(getEntityClass());


        criteriaQuery.select(root.get("image"))
            .where(criteriaBuilder.equal(root.get("id"), id));




        return Optional.ofNullable(getEntityManager().createQuery(criteriaQuery).getSingleResult());


    }

    @Override
    public int update(int id, byte[] image) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Product> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(getEntityClass());
        Root<Product> root = criteriaUpdate.from(getEntityClass());

//
//        Method[] dtoMethods = getDdtoClass().getMethods();
//        for (Method method : dtoMethods) {
//            String methodName = method.getName();
//            if (methodName.startsWith("get") && !methodName.equals("getClass")) {
//                String field = methodName.replace("get", "");
//                field = field.substring(0, 1).toLowerCase() + field.substring(1);
//                Object valueFromDto = method.invoke(ddto);
//                criteriaUpdate.set(field.endsWith("Id") ? root.get(field.replace("Id", "")).get("id") : root.get(field), valueFromDto);
//            }
//        }

        criteriaUpdate.set(root.get("image"), image);
        criteriaUpdate
            .where(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), id), criteriaBuilder.equal(root.get("active"), true)));
        int affectedRows = getEntityManager().createQuery(criteriaUpdate).executeUpdate();
        //return affectedRows;

        return affectedRows;
    }

    @Override
    public List<ProductInBriefDTO> search(String name, int startPosition, int pageSize, String sortBy, String sortDirection) {
//        StringBuilder query = new StringBuilder()
//                                    .append("SELECT id, name ")
//                                    .append("FROM product ")
//                                    .append("WHERE active = :isActive AND name LIKE :keyword ")
//                                    .append("ORDER BY ").append(sortBy).append(" ").append(sortDirection);
//        return getEntityManager().createNativeQuery(query.toString(), "ProductInBriefDTOMapping")
//                                .setParameter("isActive", true)
//                                .setParameter("keyword", "%" + name + "%")
//                                .setMaxResults(pageSize)
//                                .setFirstResult(startPosition)
//                                .getResultList();


        List<ProductInBriefDTO> authors = null;

        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<ProductInBriefDTO> criteriaQuery = criteriaBuilder.createQuery(getBdtoClass());
            Root<Product> root = criteriaQuery.from(getEntityClass());
            //Selection<?>... selections
            List<String> fields = new ArrayList<>();
            getFields(fields, getBdtoClass());
            List<Selection<?>> selections = fields.stream().map(field -> root.get(field)).collect(Collectors.toList());

            criteriaQuery.select(criteriaBuilder.construct(getBdtoClass(), selections.toArray(new Selection[selections.size()])))
                .where(criteriaBuilder.and(criteriaBuilder.like(root.get("name"),"%" + name + "%"), criteriaBuilder.equal(root.get("active"), true)))
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

    @Override
    public int countAllByCriteria(String name) {
        return ((BigInteger) getEntityManager().createNativeQuery("SELECT COUNT(*) FROM product WHERE active = :isActive AND name LIKE :keyword")
                                            .setParameter("isActive", true)
                                            .setParameter("keyword", "%" + name + "%")
                                            .getSingleResult())
                                                .intValue();
    }

//    @Override
//    public ProductInDetailDTO findById(int id) {
//
//
//
//        List<String> fields = new ArrayList<>();
//        getFields(fields, getDdtoClass());
//        String collectedFields = fields.stream()
//            .map(field -> "d."+field)
//            .map(field -> field.replace("Id", ".id"))
//            .collect(Collectors.joining(","));
//
//        //String name, double price, int quantity, String description, int categoryId, int vendorId, boolean imageUploaded
//        StringBuilder query = new StringBuilder()
//            .append("SELECT new ").append(getDdtoClass().getCanonicalName()).append("(")
//            .append(collectedFields).append(") ")
//            .append("FROM ").append(getEntityClass().getName()).append(" d ").append(" WHERE id = ?1");
//
//        return getEntityManager().createQuery(query.toString(), getDdtoClass())
//            .setParameter(1, id)
//            .getSingleResult();
//
//
//
//    }

    @Override
    public ProductInDetailDTO findById(int id) {


        ProductInDetailDTO authors = null;

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ProductInDetailDTO> criteriaQuery = criteriaBuilder.createQuery(getDdtoClass());
        Root<Product> root = criteriaQuery.from(getEntityClass());
        //Selection<?>... selections
        List<String> fields = new ArrayList<>();
        getFields(fields, getDdtoClass());
        List<Selection<?>> selections = fields.stream()
            .map(field -> field.endsWith("Id") ? root.get(field.replace("Id", "")).get("id") : root.get(field))
            .collect(Collectors.toList());

        criteriaQuery.select(criteriaBuilder.construct(getDdtoClass(), selections.toArray(new Selection[selections.size()])))
            .where(criteriaBuilder.and(criteriaBuilder.equal(root.get("id"), id), criteriaBuilder.equal(root.get("active"), true)));

        TypedQuery<ProductInDetailDTO> query = getEntityManager().createQuery(criteriaQuery);
        authors = query.getSingleResult();


        return authors;



    }

    @Transactional
    @Override
    public int update(int id, ProductInDetailDTO ddto) throws Exception {

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Product> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(getEntityClass());
        Root<Product> root = criteriaUpdate.from(getEntityClass());


        Method[] dtoMethods = getDdtoClass().getMethods();
        for (Method method : dtoMethods) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && !methodName.equals("getClass")) {
                String field = methodName.replace("get", "");
                field = field.substring(0, 1).toLowerCase() + field.substring(1);
                Object valueFromDto = method.invoke(ddto);
                criteriaUpdate.set(field.endsWith("Id") ? root.get(field.replace("Id", "")).get("id") : root.get(field), valueFromDto);
            }
        }

        criteriaUpdate
            .where(criteriaBuilder.equal(root.get("id"), id));
        int affectedRows = getEntityManager().createQuery(criteriaUpdate).executeUpdate();
        return affectedRows;
    }
}
