package com.cairoshop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.web.dtos.SavedCategoryDTO;
import com.cairoshop.web.dtos.SavedVendorDTO;

@Repository
public interface VendorRepository extends BaseRepository<SavedVendorDTO, Vendor, Integer> {
    
}
