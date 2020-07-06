package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.persistence.repositories.VendorRepository;
import com.cairoshop.service.impl.BaseProductClassificationServiceImpl;
import com.cairoshop.service.impl.VendorServiceImpl;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedBriefVendorDTO;
import com.cairoshop.web.dtos.SavedDetailedVendorDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

@ExtendWith(MockitoExtension.class)
public class VendorServiceTest extends BaseProductClassificationServiceTest<NewVendorDTO, SavedDetailedVendorDTO, SavedBriefVendorDTO, Vendor> {

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
    public void testAdd() throws Exception {
        NewVendorDTO newVendorDTO = new NewVendorDTO();
        newVendorDTO.setName("Sony");
        testAdd(newVendorDTO);
    }

    @Test
    public void testEdit() throws Exception{
        SavedDetailedVendorDTO savedDetailedVendorDTO = new SavedDetailedVendorDTO(1, "Toshiba", true);
        testEdit(savedDetailedVendorDTO);
    }

    @Test
    public void testRemoveById() {
        super.testRemoveById();
    }

    @Test
    public void testGetById() throws Exception {
        SavedDetailedVendorDTO savedDetailedVendorDTO = new SavedDetailedVendorDTO(1, "Toshiba", true);
        testGetById(savedDetailedVendorDTO);
    }

    @Test
    public void testGetAllByPage() {
        SavedBriefVendorDTO savedBriefVendorDTO = new SavedBriefVendorDTO(1, "Toshiba", true);
        testGetAllByPage(savedBriefVendorDTO);
    }

}
