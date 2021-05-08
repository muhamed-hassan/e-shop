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
class VendorServiceTest
        extends BaseProductClassificationServiceTest<Vendor, VendorInDetailDTO, VendorInBriefDTO> {

    VendorServiceTest() {
        super(Vendor.class);
    }

    @BeforeEach
    void injectRefs() {
        injectRefs(new VendorServiceImpl(mock(VendorRepository.class)));
    }

    @Test
    void shouldCreateVendorAndReturnItsIdWhenDataIsValid()
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        shouldSaveAndReturnNewIdWhenDataIsValid(new VendorInDetailDTO("Sony"));
    }

    @Test
    void shouldThrowDataIntegrityViolatedExceptionWhenDbConstraintViolatedOnVendorCreation() {
        shouldThrowDataIntegrityViolatedExceptionWhenDbConstraintViolatedOnItemCreation(new VendorInDetailDTO("Sony"));
    }

    @Test
    void shouldUpdateVendorWhenItsDataIsValid() {
        shouldUpdateItemWhenItsDataIsValid(1, new VendorInDetailDTO("Toshiba"));
    }

    @Test
    void shouldThrowDataIntegrityViolatedExceptionWhenDbConstraintViolatedOnVendorEditing() {
        shouldThrowDataIntegrityViolatedExceptionWhenDbConstraintViolatedOnItemEditing(1, new VendorInDetailDTO("Sony"));
    }

    @Test
    void shouldReturnVendorQueriedByIdWhenItsFound()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        shouldReturnSingleItemWhenItsDataFound(1, Optional.of(new VendorInDetailDTO("Toshiba")), List.of("getName"));
    }

    @Test
    void shouldReturnPageOfVendorsWhenItsDataFound() {
        shouldReturnPageWhenItsDataFound(new VendorInBriefDTO(1, "Toshiba"));
    }

    @Test
    void shouldThrowNoResultExceptionWhenPageOfVendorsNotFound() {
        super.shouldThrowNoResultExceptionWhenDataOfPageNotFound();
    }

    @Test
    void shouldReturnAllVendorsWhenTheyAreFound() {
        shouldReturnAllDataWhenTheyAreFound(new VendorInBriefDTO(1, "Toshiba"));
    }

    @Test
    void shouldThrowNoResultExceptionWhenAllVendorsNotFound() {
        super.shouldThrowNoResultExceptionWhenAllDataNotFound();
    }

    @Test
    void shouldRemoveVendorWhenItsDataIsNotAssociatedWithProduct() {
        super.shouldRemoveItemWhenItsDataIsNotAssociatedWithProduct(1);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenVendorIsAssociatedWithProduct() {
        super.shouldThrowIllegalArgumentExceptionWhenItsDataIsAssociatedWithProduct(2);
    }

}
