package com.cairoshop.services;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.service.impl.CategoryServiceImpl;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.SavedBriefCategoryDTO;
import com.cairoshop.web.dtos.SavedDetailedCategoryDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest
        extends BaseProductClassificationServiceTest<NewCategoryDTO, SavedDetailedCategoryDTO, SavedBriefCategoryDTO, Category> {

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
    public void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId() throws Exception {
        NewCategoryDTO newCategoryDTO = new NewCategoryDTO();
        newCategoryDTO.setName("Tablets");
        testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(newCategoryDTO);
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() throws Exception {
        SavedDetailedCategoryDTO savedDetailedCategoryDTO = new SavedDetailedCategoryDTO(1, "Mobiles", true);
        testEdit_WhenDataIsValid_ThenSave(savedDetailedCategoryDTO);
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturnIt() throws Exception {
        SavedDetailedCategoryDTO savedDetailedCategoryDTO = new SavedDetailedCategoryDTO(1, "Mobiles", true);
        testGetById_WhenDataFound_ThenReturnIt(savedDetailedCategoryDTO, List.of("getId", "getName", "isActive"));
    }

    @Test
    public void testGetAllByPage_WhenDataFound_ThenReturnIt() {
        SavedBriefCategoryDTO savedBriefCategoryDTO = new SavedBriefCategoryDTO(1, "Mobiles", true);
        testGetAllByPage_WhenDataFound_ThenReturnIt(savedBriefCategoryDTO);
    }

    @Test
    public void testGetAll_WhenDataFound_ThenReturnIt() {
        SavedBriefCategoryDTO savedBriefCategoryDTO = new SavedBriefCategoryDTO(1, "Mobiles", true);
        testGetAll_WhenDataFound_ThenReturnIt(savedBriefCategoryDTO);
    }

    @Test
    public void testRemoveById_WhenDataFound_ThenReturnIt() {
        super.testRemoveById_WhenDataFound_ThenRemoveIt();
    }

}
