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
class CategoryServiceTest
        extends BaseProductClassificationServiceTest<Category, CategoryInDetailDTO, CategoryInBriefDTO> {

    CategoryServiceTest() {
        super(Category.class);
    }

    @BeforeEach
    void injectRefs() {
        injectRefs(new CategoryServiceImpl(mock(CategoryRepository.class)));
    }

    @Test
    void shouldCreateCategoryAndReturnItsIdWhenDataIsValid()
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        shouldSaveAndReturnNewIdWhenDataIsValid(new CategoryInDetailDTO("Tablets"));
    }

    @Test
    void shouldThrowDataIntegrityViolatedExceptionWhenDbConstraintViolatedOnCategoryCreation() {
        shouldThrowDataIntegrityViolatedExceptionWhenDbConstraintViolatedOnItemCreation(new CategoryInDetailDTO("Tablets"));
    }

    @Test
    void shouldUpdateCategoryWhenItsDataIsValid() {
        shouldUpdateItemWhenItsDataIsValid(1, new CategoryInDetailDTO("Mobiles"));
    }

    @Test
    void shouldThrowDataIntegrityViolatedExceptionWhenDbConstraintViolatedOnCategoryEditing() {
        shouldThrowDataIntegrityViolatedExceptionWhenDbConstraintViolatedOnItemEditing(1, new CategoryInDetailDTO("Tablets"));
    }

    @Test
    void shouldReturnCategoryQueriedByIdWhenItsFound()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        shouldReturnSingleItemWhenItsDataFound(1, Optional.of(new CategoryInDetailDTO("Mobiles")), List.of("getName"));
    }

    @Test
    void shouldReturnPageOfCategoriesWhenItsDataFound() {
        shouldReturnPageWhenItsDataFound(new CategoryInBriefDTO(2, "Mobiles"));
    }

    @Test
    void shouldThrowNoResultExceptionWhenPageOfCategoriesNotFound() {
        super.shouldThrowNoResultExceptionWhenDataOfPageNotFound();
    }

    @Test
    void shouldReturnAllCategoriesWhenTheyAreFound() {
        shouldReturnAllDataWhenTheyAreFound(new CategoryInBriefDTO(2, "Mobiles"));
    }

    @Test
    void shouldThrowNoResultExceptionWhenAllCategoriesNotFound() {
        super.shouldThrowNoResultExceptionWhenAllDataNotFound();
    }

    @Test
    void shouldRemoveCategoryWhenItsDataIsNotAssociatedWithProduct() {
        super.shouldRemoveItemWhenItsDataIsNotAssociatedWithProduct(1);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCategoryIsAssociatedWithProduct() {
        super.shouldThrowIllegalArgumentExceptionWhenItsDataIsAssociatedWithProduct(2);
    }

}
