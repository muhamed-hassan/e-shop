package com.cairoshop.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.VendorRepository;
import com.cairoshop.service.VendorService;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedBriefVendorDTO;
import com.cairoshop.web.dtos.SavedDetailedVendorDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class VendorServiceImpl
                extends BaseProductClassificationServiceImpl<NewVendorDTO, SavedDetailedVendorDTO, SavedBriefVendorDTO, Vendor>
                implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public VendorServiceImpl() {
        super(Vendor.class, SavedDetailedVendorDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(vendorRepository);
    }

}
