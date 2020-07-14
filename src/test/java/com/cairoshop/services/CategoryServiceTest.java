package com.cairoshop.services;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

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
public class CategoryServiceTest
            extends BaseProductClassificationServiceTest<Category, CategoryInDetailDTO, CategoryInBriefDTO> {

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
    public void testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() throws Exception {
        CategoryInDetailDTO categoryInDetailDTO = new CategoryInDetailDTO("Tablets");
        testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(categoryInDetailDTO);
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() throws Exception {
        CategoryInDetailDTO savedDetailedCategoryDTO = new CategoryInDetailDTO("Mobiles");
        testEdit_WhenDataIsValid_ThenSave(1, savedDetailedCategoryDTO);
    }

    @Test
    public void testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() throws Exception {
        CategoryInDetailDTO savedDetailedCategoryDTO = new CategoryInDetailDTO("Tablets");
        testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(1, savedDetailedCategoryDTO);
    }

    @Test
    public void testEdit_WhenRecordNotUpdated_ThenThrowDataNotUpdatedException() throws Exception {
        CategoryInDetailDTO savedDetailedCategoryDTO = new CategoryInDetailDTO("Mobiles");
        testEdit_WhenRecordNotUpdated_ThenThrowDataNotUpdatedException(1, savedDetailedCategoryDTO);
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturnIt() throws Exception {
        CategoryInDetailDTO savedDetailedCategoryDTO = new CategoryInDetailDTO("Mobiles");
        testGetById_WhenDataFound_ThenReturnIt(1, savedDetailedCategoryDTO, List.of("getName"));
    }

    @Test
    public void testGetById_WhenDataNotFound_ThenThrowNoResultException() {
        testGetById_WhenDataNotFound_ThenThrowNoResultException(404);
    }

    @Test
    public void testGetAllByPage_WhenDataFound_ThenReturnIt() {
        CategoryInBriefDTO categoryInBriefDTO = new CategoryInBriefDTO(2, "Mobiles");
        testGetAllByPage_WhenDataFound_ThenReturnIt(categoryInBriefDTO);
    }

    @Test
    public void testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException() {
        super.testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException();
    }

    @Test
    public void testGetAll_WhenDataFound_ThenReturnIt() {
        CategoryInBriefDTO categoryInBriefDTO = new CategoryInBriefDTO(2, "Mobiles");
        testGetAll_WhenDataFound_ThenReturnIt(categoryInBriefDTO);
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
    }

}
