package com.cairoshop.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.VendorRepository;
import com.cairoshop.service.VendorService;
import com.cairoshop.web.dtos.VendorInBriefDTO;
import com.cairoshop.web.dtos.VendorInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class VendorServiceImpl
            extends BaseProductClassificationServiceImpl<Vendor, VendorInDetailDTO, VendorInBriefDTO>
            implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public VendorServiceImpl() {
        super(Vendor.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(vendorRepository);
    }

}
