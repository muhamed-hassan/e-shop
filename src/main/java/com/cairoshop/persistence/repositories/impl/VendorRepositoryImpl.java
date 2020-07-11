package com.cairoshop.persistence.repositories.impl;

import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.VendorRepository;
import com.cairoshop.web.dtos.VendorInBriefDTO;
import com.cairoshop.web.dtos.VendorInDetailDTO;

@Repository
public class VendorRepositoryImpl
    extends BaseProductClassificationRepositoryImpl<Vendor, VendorInDetailDTO, VendorInBriefDTO>
    implements VendorRepository {

    public VendorRepositoryImpl() {
        super(Vendor.class, VendorInDetailDTO.class, VendorInBriefDTO.class);
    }

}
