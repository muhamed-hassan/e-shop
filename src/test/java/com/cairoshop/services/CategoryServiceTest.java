package com.cairoshop.services;

import static org.mockito.Mockito.mock;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.service.impl.CategoryServiceImpl;
import com.cairoshop.web.dtos.CategoryInBriefDTO;
import com.cairoshop.web.dtos.CategoryInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class CategoryServiceTest
           extends BaseProductClassificationServiceTest<Category, CategoryInDetailDTO, CategoryInBriefDTO> {

    public CategoryServiceTest() {
        super(Category.class);
    }

    @BeforeEach
    public void injectRefs() {
        CategoryServiceImpl categoryService = new CategoryServiceImpl(mock(CategoryRepository.class));
        injectRefs(categoryService);
    }

    @Test
    public void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId()
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        CategoryInDetailDTO categoryInDetailDTO = new CategoryInDetailDTO("Tablets");
        testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(categoryInDetailDTO);
    }

    @Test
    public void testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() {
        CategoryInDetailDTO categoryInDetailDTO = new CategoryInDetailDTO("Tablets");
        testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(categoryInDetailDTO);
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() {
        CategoryInDetailDTO savedDetailedCategoryDTO = new CategoryInDetailDTO("Mobiles");
        testEdit_WhenDataIsValid_ThenSave(1, savedDetailedCategoryDTO);
    }

    @Test
    public void testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() {
        CategoryInDetailDTO savedDetailedCategoryDTO = new CategoryInDetailDTO("Tablets");
        testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(1, savedDetailedCategoryDTO);
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturnIt()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Optional<CategoryInDetailDTO> categoryInDetailDTO = Optional.of(new CategoryInDetailDTO("Mobiles"));
        testGetById_WhenDataFound_ThenReturnIt(1, categoryInDetailDTO, List.of("getName"));
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
    public void testRemoveById_WhenDataIsNotAssociatedWithProduct_ThenRemoveIt()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        super.testRemoveById_WhenDataIsNotAssociatedWithProduct_ThenRemoveIt(1);
    }

    @Test
    public void testRemoveById_WhenDataIsAssociatedWithProduct_ThenThrowIllegalArgumentException() {
        super.testRemoveById_WhenDataIsAssociatedWithProduct_ThenThrowIllegalArgumentException(2);
    }

}
