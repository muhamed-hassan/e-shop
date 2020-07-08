package com.cairoshop.it;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.service.VendorService;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedBriefVendorDTO;
import com.cairoshop.web.dtos.SavedDetailedVendorDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
//@RestController
//@RequestMapping("vendors")
//@Validated
public class VendorControllerIT extends BaseProductClassificationControllerIT /*extends BaseProductClassificationControllerIT<NewVendorDTO, SavedDetailedVendorDTO, SavedBriefVendorDTO, Vendor>*/ {
    
//    @Autowired
//    private VendorService vendorService;
//
//    @PostConstruct
//    public void injectRefs() {
//        setService(vendorService);
//    }

    // admin or customer
    public void testGetAll_WhenDataExists_ThenReturn200WithData() {
        testGetAll_WhenDataExists_ThenReturn200WithData(null,null,null);
    }

    // admin
    public void testEdit_WhenPayloadIsValid_ThenReturn204() {
        testEdit_WhenPayloadIsValid_ThenReturn204(null,null,null);
    }

    //admin
    protected void testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation() {
        testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation(null,null,null,null);
    }

    //admin
    protected void testRemove_WhenItemExists_ThenRemoveItAndReturn204() {
        testRemove_WhenItemExists_ThenRemoveItAndReturn204(null,null);
    }

    //admin or customer
    protected void testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData() throws Exception {
        testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData(null,null,null);
    }

    //admin or customer
    protected void testGetById_WhenDataFound_ThenReturn200AndData() throws Exception {
        testGetById_WhenDataFound_ThenReturn200AndData(null,null,null);
    }
    
}
