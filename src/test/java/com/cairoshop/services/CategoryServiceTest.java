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
import com.cairoshop.web.dtos.CategoryInBriefDTO;
import com.cairoshop.web.dtos.CategoryInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest
        extends BaseProductClassificationServiceTest<CategoryInDetailDTO, CategoryInBriefDTO, Category> {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    public CategoryServiceTest() {
        super(Category.class, CategoryInDetailDTO.class);
    }

    @BeforeEach
    public void injectRefs() {
        injectRefs(categoryRepository, categoryService);
    }

    @Test
    public void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId() throws Exception {
        CategoryInDetailDTO categoryInDetailDTO = new CategoryInDetailDTO("Tablets");
        testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(categoryInDetailDTO);
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() throws Exception {
        CategoryInDetailDTO savedDetailedCategoryDTO = new CategoryInDetailDTO("Mobiles");
        testEdit_WhenDataIsValid_ThenSave(1, savedDetailedCategoryDTO);
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturnIt() throws Exception {
        CategoryInDetailDTO savedDetailedCategoryDTO = new CategoryInDetailDTO("Mobiles");
        testGetById_WhenDataFound_ThenReturnIt(savedDetailedCategoryDTO, List.of("getName"));
    }

    @Test
    public void testGetAllByPage_WhenDataFound_ThenReturnIt() {
        CategoryInBriefDTO categoryInBriefDTO = new CategoryInBriefDTO(2, "Mobiles");
        testGetAllByPage_WhenDataFound_ThenReturnIt(categoryInBriefDTO);
    }

    @Test
    public void testGetAll_WhenDataFound_ThenReturnIt() {
        CategoryInBriefDTO categoryInBriefDTO = new CategoryInBriefDTO(2, "Mobiles");
        testGetAll_WhenDataFound_ThenReturnIt(categoryInBriefDTO);
    }

    @Test
    public void testRemoveById_WhenDataFound_ThenReturnIt() {
        super.testRemoveById_WhenDataFound_ThenRemoveIt();
    }

}
