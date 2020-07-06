package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.service.impl.CategoryServiceImpl;
import com.cairoshop.service.impl.CategoryServiceImpl;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.SavedBriefCategoryDTO;
import com.cairoshop.web.dtos.SavedBriefCategoryDTO;
import com.cairoshop.web.dtos.SavedBriefCategoryDTO;
import com.cairoshop.web.dtos.SavedBriefVendorDTO;
import com.cairoshop.web.dtos.SavedDetailedCategoryDTO;
import com.cairoshop.web.dtos.SavedDetailedCategoryDTO;
import com.cairoshop.web.dtos.SavedDetailedCategoryDTO;
import com.cairoshop.web.dtos.SavedDetailedVendorDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest extends BaseProductClassificationServiceTest<NewCategoryDTO, SavedDetailedCategoryDTO, SavedBriefCategoryDTO, Category> {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;
    public CategoryServiceTest() {
        super(Category.class, SavedDetailedCategoryDTO.class);
    }

    @BeforeEach
    public void injectRefs() {
        injectRefs(categoryRepository, categoryService);
    }

    @Test
    public void testAdd() throws Exception {
        NewCategoryDTO newCategoryDTO = new NewCategoryDTO();
        newCategoryDTO.setName("Tablets");
        testAdd(newCategoryDTO);
    }

    @Test
    public void testEdit() throws Exception {
        SavedDetailedCategoryDTO savedDetailedCategoryDTO = new SavedDetailedCategoryDTO(1, "Mobiles", true);
        testEdit(savedDetailedCategoryDTO);
    }

    @Test
    public void testRemoveById() {
        super.testRemoveById();
    }
    @Test
    public void testGetById() throws Exception {
        SavedDetailedCategoryDTO savedDetailedCategoryDTO = new SavedDetailedCategoryDTO(1, "Mobiles", true);
        testGetById(savedDetailedCategoryDTO);
    }

    @Test
    public void testGetAllByPage() {
        SavedBriefCategoryDTO savedBriefCategoryDTO = new SavedBriefCategoryDTO(1, "Mobiles", true);
        testGetAllByPage(savedBriefCategoryDTO);
    }


}
