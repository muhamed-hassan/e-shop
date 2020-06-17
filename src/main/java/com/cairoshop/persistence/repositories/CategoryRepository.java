package com.cairoshop.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.web.dtos.SavedCategoryDTO;

@Repository
public interface CategoryRepository extends BaseRepository<SavedCategoryDTO, Category, Integer> {



}
