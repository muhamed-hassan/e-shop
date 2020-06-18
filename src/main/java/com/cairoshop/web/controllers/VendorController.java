package com.cairoshop.web.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cairoshop.service.VendorService;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedVendorDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@RestController
@RequestMapping("vendors")
@Validated
public class VendorController extends BaseConrtollerForClassifiedResources<NewVendorDTO, SavedVendorDTO> {
    
    @Autowired
    private VendorService vendorService;

    @PostConstruct
    public void injectRefs() {
        setService(vendorService);
    }
    
}
