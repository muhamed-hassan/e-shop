package com.cairoshop.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.persistence.repositories.VendorRepository;
import com.cairoshop.service.VendorService;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedCategoryDTO;
import com.cairoshop.web.dtos.SavedVendorDTO;

@Service
public class VendorServiceImpl extends BaseServiceImpl<NewVendorDTO, SavedVendorDTO, Vendor> implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public VendorServiceImpl() {
        super(Vendor.class, SavedVendorDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(vendorRepository);
    }
}
