package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.service.BaseProductClassificationService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
class BaseProductClassificationServiceTest<T, D, B>
        extends BaseServiceTest<T, D, B> {

    BaseProductClassificationServiceTest(Class<T> entityClass) {
        super(entityClass);
    }

    void shouldUpdateItemWhenItsDataIsValid(int id, D detailedDtoClass) {
        var entity = mock(getEntityClass());
        when(getRepository().getOne(id))
            .thenReturn(entity);
        when(getRepository().saveAndFlush(entity))
            .thenReturn(entity);

        ((BaseProductClassificationService) getService()).edit(id, detailedDtoClass);

        verify(getRepository()).getOne(id);
        verify(getRepository()).saveAndFlush(entity);
    }

    void shouldThrowDataIntegrityViolatedExceptionWhenDbConstraintViolatedOnItemEditing(int id, D detailedDtoClass) {
        var entity = mock(getEntityClass());
        when(getRepository().getOne(id))
            .thenReturn(entity);
        when(getRepository().saveAndFlush(any(getEntityClass())))
            .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolatedException.class,
            () -> ((BaseProductClassificationService) getService()).edit(id, detailedDtoClass));
    }

    void shouldReturnAllDataWhenTheyAreFound(B briefDtoClass) {
        var expectedResult = List.of(briefDtoClass);
        when(((BaseProductClassificationRepository) getRepository()).findAllByActive(any(boolean.class), any(Class.class)))
            .thenReturn(expectedResult);

        var actualResult = ((BaseProductClassificationService) getService()).getAll();

        assertIterableEquals(expectedResult, actualResult);
    }

    void shouldThrowNoResultExceptionWhenAllDataNotFound() {
        when(((BaseProductClassificationRepository) getRepository()).findAllByActive(any(boolean.class), any(Class.class)))
            .thenReturn(Collections.emptyList());

        assertThrows(NoResultException.class,
            () -> ((BaseProductClassificationService) getService()).getAll());
    }

    void shouldRemoveItemWhenItsDataIsNotAssociatedWithProduct(int idOfObjectToDelete) {
        when(((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(idOfObjectToDelete))
            .thenReturn(0L);
        var entity = mock(getEntityClass());
        when(getRepository().getOne(idOfObjectToDelete))
            .thenReturn(entity);
        when(getRepository().save(entity))
            .thenReturn(entity);

        ((BaseProductClassificationService) getService()).removeById(idOfObjectToDelete);

        verify((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(idOfObjectToDelete);
        verify(getRepository()).getOne(idOfObjectToDelete);
        verify(getRepository()).save(entity);
    }

    void shouldThrowIllegalArgumentExceptionWhenItsDataIsAssociatedWithProduct(int idOfObjectToDelete) {
        when(((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(idOfObjectToDelete))
            .thenReturn(1L);

        assertThrows(IllegalArgumentException.class,
            () -> ((BaseProductClassificationService) getService()).removeById(idOfObjectToDelete));
    }

}
