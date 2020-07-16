package com.cairoshop.services;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.VendorRepository;
import com.cairoshop.service.impl.VendorServiceImpl;
import com.cairoshop.web.dtos.VendorInBriefDTO;
import com.cairoshop.web.dtos.VendorInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class VendorServiceTest
            /*extends BaseProductClassificationServiceTest<Vendor, VendorInDetailDTO, VendorInBriefDTO>*/ {

   /* @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private VendorServiceImpl vendorService;

    public VendorServiceTest() {
        super(Vendor.class, VendorInDetailDTO.class, VendorInBriefDTO.class);
    }

    @BeforeEach
    public void injectRefs() {
        injectRefs(vendorRepository, vendorService);
    }

    @Test
    public void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId() throws Exception {
        VendorInDetailDTO vendorInDetailDTO = new VendorInDetailDTO("Sony");
        testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(vendorInDetailDTO);
    }

    @Test
    public void testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() throws Exception {
        VendorInDetailDTO vendorInDetailDTO = new VendorInDetailDTO("Sony");
        testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(vendorInDetailDTO);
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() throws Exception {
        VendorInDetailDTO vendorInDetailDTO = new VendorInDetailDTO("Toshiba");
        testEdit_WhenDataIsValid_ThenSave(1, vendorInDetailDTO);
    }

    @Test
    public void testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() throws Exception {
        VendorInDetailDTO vendorInDetailDTO = new VendorInDetailDTO("Sony");
        testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(1, vendorInDetailDTO);
    }

    @Test
    public void testEdit_WhenRecordNotUpdated_ThenThrowDataNotUpdatedException() throws Exception {
        VendorInDetailDTO vendorInDetailDTO = new VendorInDetailDTO("Toshiba");
        testEdit_WhenRecordNotUpdated_ThenThrowDataNotUpdatedException(1, vendorInDetailDTO);
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturnIt() throws Exception {
        VendorInDetailDTO vendorInDetailDTO = new VendorInDetailDTO("Toshiba");
//        testGetById_WhenDataFound_ThenReturnIt(1, vendorInDetailDTO, List.of("getName"));
    }

    @Test
    public void testGetById_WhenDataNotFound_ThenThrowNoResultException() {
        testGetById_WhenDataNotFound_ThenThrowNoResultException(404);
    }

    @Test
    public void testGetAllByPage_WhenDataFound_ThenReturnIt() {
        VendorInBriefDTO vendorInBriefDTO = new VendorInBriefDTO(1, "Toshiba");
        testGetAllByPage_WhenDataFound_ThenReturnIt(vendorInBriefDTO);
    }

    @Test
    public void testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException() {
        super.testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException();
    }

    @Test
    public void testGetAll_WhenDataFound_ThenReturnIt() {
        VendorInBriefDTO vendorInBriefDTO = new VendorInBriefDTO(1, "Toshiba");
        testGetAll_WhenDataFound_ThenReturnIt(vendorInBriefDTO);
    }

    @Test
    public void testGetAll_WhenDataNotFound_ThenThrowNoResultException() {
        super.testGetAll_WhenDataNotFound_ThenThrowNoResultException();
    }

    @Test
    public void testRemoveById_WhenDataFound_ThenReturnIt() {
        super.testRemoveById_WhenDataFound_ThenRemoveIt(1);
    }

    @Test
    public void testRemoveById_WhenDataNotFound_ThenThrowDataNotDeletedException() {
        super.testRemoveById_WhenDataNotFound_ThenThrowDataNotDeletedException(404);
    }

    @Test
    public void testRemoveById_WhenDataIsAssociatedWithProduct_ThenThrowIllegalArgumentException() {
        super.testRemoveById_WhenDataIsAssociatedWithProduct_ThenThrowIllegalArgumentException(2);
    }*/

}
