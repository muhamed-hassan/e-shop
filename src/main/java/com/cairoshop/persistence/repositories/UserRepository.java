package com.cairoshop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
