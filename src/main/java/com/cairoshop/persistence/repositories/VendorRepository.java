package com.cairoshop.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    
}
