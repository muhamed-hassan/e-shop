package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
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
public class BaseProductClassificationServiceTest<T, D, B>
            extends BaseServiceTest<T, D, B> {

    protected BaseProductClassificationServiceTest(Class<T> entityClass) {
        super(entityClass);
    }

    protected void testEdit_WhenDataIsValid_ThenSave(int id, D detailedDtoClass)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        T entity = mock(getEntityClass());
        when(getRepository().getOne(id))
            .thenReturn(entity);
        when(getRepository().save(entity))
            .thenReturn(entity);

        ((BaseProductClassificationService) getService()).edit(id, detailedDtoClass);

        verify(getRepository()).getOne(id);
        verify(getRepository()).save(entity);
    }

    protected void testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(int id, D detailedDtoClass) {
        T entity = mock(getEntityClass());
        when(getRepository().getOne(id))
            .thenReturn(entity);
        when(getRepository().save(any(getEntityClass())))
            .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolatedException.class,
            () -> ((BaseProductClassificationService) getService()).edit(id, detailedDtoClass));
    }

    protected void testGetAll_WhenDataFound_ThenReturnIt(B briefDtoClass) {
        List<B> expectedResult = List.of(briefDtoClass);
        when(((BaseProductClassificationRepository) getRepository()).findAllByActive(any(boolean.class), any(Class.class)))
            .thenReturn(expectedResult);

        List<B> actualResult = ((BaseProductClassificationService) getService()).getAll();

        assertIterableEquals(expectedResult, actualResult);
    }

    protected void testGetAll_WhenDataNotFound_ThenThrowNoResultException() {
        when(((BaseProductClassificationRepository) getRepository()).findAllByActive(any(boolean.class), any(Class.class)))
            .thenReturn(Collections.emptyList());

        assertThrows(NoResultException.class,
            () -> ((BaseProductClassificationService) getService()).getAll());
    }

    protected void testRemoveById_WhenDataIsNotAssociatedWithProduct_ThenRemoveIt(int idOfObjectToDelete)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        when(((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(idOfObjectToDelete))
            .thenReturn(0L);
        T entity = mock(getEntityClass());
        when(getRepository().getOne(idOfObjectToDelete))
            .thenReturn(entity);
        when(getRepository().save(entity))
            .thenReturn(entity);

        ((BaseProductClassificationService) getService()).removeById(idOfObjectToDelete);

        verify((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(idOfObjectToDelete);
        verify(getRepository()).getOne(idOfObjectToDelete);
        verify(getRepository()).save(entity);
    }

    protected void testRemoveById_WhenDataIsAssociatedWithProduct_ThenThrowIllegalArgumentException(int idOfObjectToDelete) {
        when(((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(idOfObjectToDelete))
            .thenReturn(1L);

        assertThrows(IllegalArgumentException.class,
            () -> ((BaseProductClassificationService) getService()).removeById(idOfObjectToDelete));
    }

}
