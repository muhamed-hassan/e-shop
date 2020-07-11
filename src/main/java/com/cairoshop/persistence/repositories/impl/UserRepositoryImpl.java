package com.cairoshop.persistence.repositories.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
//        StringBuilder query = new StringBuilder()
//                                    .append("SELECT u.id, u.name, u.active ")
//                                    .append("FROM user u INNER JOIN role r ON (u.role = r.id AND r.name = :role) ")
//                                    .append("ORDER BY ").append(sortBy).append(" ").append(sortDirection);
//        return getEntityManager().createNativeQuery(query.toString(), "UserInBriefDTOMapping")
//                                .setParameter("role", "ROLE_CUSTOMER")
//                                .setMaxResults(pageSize)
//                                .setFirstResult(startPosition)
//                                .getResultList();

        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UserInBriefDTO> criteriaQuery = criteriaBuilder.createQuery(UserInBriefDTO.class);
        Root<User> root = criteriaQuery.from(User.class);
        //Selection<?>... selections
        List<String> fields = new ArrayList<>();
        getFields(fields, UserInBriefDTO.class);

        criteriaQuery.select(criteriaBuilder.construct(UserInBriefDTO.class, root.get("id"), root.get("name"), root.get("active")))
        .where(criteriaBuilder.equal(root.get("role").get("name"), "ROLE_CUSTOMER"));

        List<UserInBriefDTO> authors = getEntityManager().createQuery(criteriaQuery)
            .setMaxResults(pageSize)
            .setFirstResult(startPosition)
            .getResultList();

        return authors;
    }

    @Override
    public int countAllCustomers() {
        StringBuilder query = new StringBuilder()
            .append("SELECT COUNT(*) ")
            .append("FROM user u INNER JOIN role r ON (u.role = r.id AND r.name = :role) ");
        return ((BigInteger) getEntityManager().createNativeQuery(query.toString())
                                            .setParameter("role", "ROLE_CUSTOMER")
                                            .getSingleResult())
                                        .intValue();
    }

    // @Query("UPDATE User u SET u.active = ?2, u.enabled = ?2 WHERE u.id =?1")
    @Override
    public int update(int id, boolean newState) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<User> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(User.class);
        Root<User> root = criteriaUpdate.from(User.class);
        criteriaUpdate.set(root.get("active"), newState).where(criteriaBuilder.equal(root.get("id"), id));
        int affectedRows = getEntityManager().createQuery(criteriaUpdate).executeUpdate();
        return affectedRows;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));
        User authors = null;
        try {
         authors = getEntityManager().createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException nre) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }


        return Optional.of(authors);
    }

}