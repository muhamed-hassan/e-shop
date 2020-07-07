package com.cairoshop.services;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.VendorRepository;
import com.cairoshop.service.impl.VendorServiceImpl;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedBriefVendorDTO;
import com.cairoshop.web.dtos.SavedDetailedVendorDTO;

@ExtendWith(MockitoExtension.class)
public class VendorServiceTest
        extends BaseProductClassificationServiceTest<NewVendorDTO, SavedDetailedVendorDTO, SavedBriefVendorDTO, Vendor> {

    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private VendorServiceImpl vendorService;

    public VendorServiceTest() {
        super(Vendor.class, SavedDetailedVendorDTO.class);
    }

    @BeforeEach
    public void injectRefs() {
        injectRefs(vendorRepository, vendorService);
    }

    @Test
    public void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId() throws Exception {
        NewVendorDTO newVendorDTO = new NewVendorDTO();
        newVendorDTO.setName("Sony");
        testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(newVendorDTO);
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() throws Exception{
        SavedDetailedVendorDTO savedDetailedVendorDTO = new SavedDetailedVendorDTO(1, "Toshiba", true);
        testEdit_WhenDataIsValid_ThenSave(savedDetailedVendorDTO);
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturnIt() throws Exception {
        SavedDetailedVendorDTO savedDetailedVendorDTO = new SavedDetailedVendorDTO(1, "Toshiba", true);
        testGetById_WhenDataFound_ThenReturnIt(savedDetailedVendorDTO, List.of("getId", "getName", "isActive"));
    }

    @Test
    public void testGetAllByPage_WhenDataFound_ThenReturnIt() {
        SavedBriefVendorDTO savedBriefVendorDTO = new SavedBriefVendorDTO(1, "Toshiba", true);
        testGetAllByPage_WhenDataFound_ThenReturnIt(savedBriefVendorDTO);
    }

    @Test
    public void testGetAll_WhenDataFound_ThenReturnIt() {
        SavedBriefVendorDTO savedBriefVendorDTO = new SavedBriefVendorDTO(1, "Toshiba", true);
        testGetAll_WhenDataFound_ThenReturnIt(savedBriefVendorDTO);
    }

    @Test
    public void testRemoveById_WhenDataFound_ThenReturnIt() {
        super.testRemoveById_WhenDataFound_ThenRemoveIt();
    }

}
