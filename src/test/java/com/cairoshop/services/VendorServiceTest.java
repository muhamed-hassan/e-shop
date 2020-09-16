package com.cairoshop.services;

import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
class VendorServiceTest extends BaseProductClassificationServiceTest<Vendor, VendorInDetailDTO, VendorInBriefDTO> {

    VendorServiceTest() {
        super(Vendor.class);
    }

    @BeforeEach
    public void injectRefs() {
        VendorServiceImpl vendorService = new VendorServiceImpl(mock(VendorRepository.class));
        injectRefs(vendorService);
    }

    @Test
    void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId()
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        VendorInDetailDTO vendorInDetailDTO = new VendorInDetailDTO("Sony");
        testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(vendorInDetailDTO);
    }

    @Test
    void testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() {
        VendorInDetailDTO vendorInDetailDTO = new VendorInDetailDTO("Sony");
        testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(vendorInDetailDTO);
    }

    @Test
    void testEdit_WhenDataIsValid_ThenSave() {
        VendorInDetailDTO vendorInDetailDTO = new VendorInDetailDTO("Toshiba");
        testEdit_WhenDataIsValid_ThenSave(1, vendorInDetailDTO);
    }

    @Test
    void testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() {
        VendorInDetailDTO vendorInDetailDTO = new VendorInDetailDTO("Sony");
        testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(1, vendorInDetailDTO);
    }

    @Test
    void testGetById_WhenDataFound_ThenReturnIt()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Optional<VendorInDetailDTO> vendorInDetailDTO = Optional.of(new VendorInDetailDTO("Toshiba"));
        testGetById_WhenDataFound_ThenReturnIt(1, vendorInDetailDTO, List.of("getName"));
    }

    @Test
    void testGetAllByPage_WhenDataFound_ThenReturnIt() {
        VendorInBriefDTO vendorInBriefDTO = new VendorInBriefDTO(1, "Toshiba");
        testGetAllByPage_WhenDataFound_ThenReturnIt(vendorInBriefDTO);
    }

    @Test
    void testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException() {
        super.testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException();
    }

    @Test
    void testGetAll_WhenDataFound_ThenReturnIt() {
        VendorInBriefDTO vendorInBriefDTO = new VendorInBriefDTO(1, "Toshiba");
        testGetAll_WhenDataFound_ThenReturnIt(vendorInBriefDTO);
    }

    @Test
    void testGetAll_WhenDataNotFound_ThenThrowNoResultException() {
        super.testGetAll_WhenDataNotFound_ThenThrowNoResultException();
    }


    @Test
    void testRemoveById_WhenDataIsNotAssociatedWithProduct_ThenRemoveIt()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        super.testRemoveById_WhenDataIsNotAssociatedWithProduct_ThenRemoveIt(1);
    }

    @Test
    void testRemoveById_WhenDataIsAssociatedWithProduct_ThenThrowIllegalArgumentException() {
        super.testRemoveById_WhenDataIsAssociatedWithProduct_ThenThrowIllegalArgumentException(2);
    }

}
