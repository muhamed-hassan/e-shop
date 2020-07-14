package com.cairoshop.persistence.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.persistence.repositories.UserRepository;
import com.cairoshop.web.dtos.UserInBriefDTO;
import com.cairoshop.web.dtos.UserInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public class UserRepositoryImpl
            extends BaseCommonRepositoryImpl<User, UserInDetailDTO>
            implements UserRepository {

    public UserRepositoryImpl() {
        super(User.class, UserInDetailDTO.class);
    }

    @Override
    public List<UserInBriefDTO> findAllCustomers(int startPosition, int pageSize, String sortBy, String sortDirection) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserInBriefDTO> criteriaQuery = criteriaBuilder.createQuery(UserInBriefDTO.class);
        Root<User> root = criteriaQuery.from(User.class);
        List<String> fields = new ArrayList<>();
        getReflectionUtils().getFields(fields, UserInBriefDTO.class);
        criteriaQuery.select(criteriaBuilder.construct(UserInBriefDTO.class, root.get("id"), root.get("name"), root.get("active")))
                        .where(criteriaBuilder.equal(root.get("role").get("name"), "ROLE_CUSTOMER"));
        return getEntityManager().createQuery(criteriaQuery)
                                    .setMaxResults(pageSize)
                                    .setFirstResult(startPosition)
                                    .getResultList();
    }

    @Override
    public int countAllCustomers() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.count(root))
                        .where(criteriaBuilder.equal(root.get("role").get("name"), "ROLE_CUSTOMER"));
        return getEntityManager().createQuery(criteriaQuery).getSingleResult().intValue();
    }

    @Override
    public int update(int id, boolean newState) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<User> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(User.class);
        Root<User> root = criteriaUpdate.from(User.class);
        criteriaUpdate.set(root.get("active"), newState)
                        .set(root.get("enabled"), newState)
                        .where(criteriaBuilder.equal(root.get("id"), id));
        return getEntityManager().createQuery(criteriaUpdate).executeUpdate();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));
        User user = getEntityManager().createQuery(criteriaQuery).getSingleResult();
        return Optional.ofNullable(user);
    }

}
