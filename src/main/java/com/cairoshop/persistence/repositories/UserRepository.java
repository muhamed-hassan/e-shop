package com.cairoshop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.web.dtos.SavedCustomerDTO;

@Repository
public interface UserRepository extends BaseRepository<SavedCustomerDTO, User, Integer> {

}
