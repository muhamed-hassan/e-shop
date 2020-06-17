package com.cairoshop.service;

import java.util.List;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedCategoryDTO;
import com.cairoshop.web.dtos.SavedVendorDTO;

public interface VendorService extends BaseService<NewVendorDTO, SavedVendorDTO, Vendor> {



}
