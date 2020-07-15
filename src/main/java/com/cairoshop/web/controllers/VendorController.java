package com.cairoshop.web.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cairoshop.service.VendorService;
import com.cairoshop.web.dtos.VendorInBriefDTO;
import com.cairoshop.web.dtos.VendorInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@RestController
@RequestMapping("vendors")
@Validated
public class VendorController
            extends BaseProductClassificationController<VendorInDetailDTO, VendorInBriefDTO> {
    
    @Autowired
    private VendorService vendorService;

    @PostConstruct
    public void injectRefs() {
        setService(vendorService);
    }
    
}
