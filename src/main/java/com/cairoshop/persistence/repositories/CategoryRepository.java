package com.cairoshop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
