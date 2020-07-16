package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.BaseProductClassificationService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.DataNotDeletedException;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseProductClassificationServiceTest<T, D, B>
            /*extends BaseServiceTest<T, D, B>*/ {

    /*protected BaseProductClassificationServiceTest(Class<T> entityClass, Class<D> detailedDtoClass, Class<B> briefDtoClass) {
        super(entityClass, detailedDtoClass, briefDtoClass);
    }

    protected void testEdit_WhenDataIsValid_ThenSave(int id, D detailedDtoClass)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String name = (String) detailedDtoClass.getClass().getMethod("getName").invoke(detailedDtoClass);
        int affectedRows = 1;
        when(((BaseProductClassificationRepository) getRepository()).update(id, name))
            .thenReturn(affectedRows);

        ((BaseProductClassificationService) getService()).edit(id, detailedDtoClass);

        verify(((BaseProductClassificationRepository) getRepository())).update(id, name);
    }

    protected void testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(int id, D detailedDtoClass) {
        doThrow(DataIntegrityViolationException.class)
            .when((BaseProductClassificationRepository) getRepository()).update(any(int.class), anyString());

        assertThrows(DataIntegrityViolatedException.class,
            () -> ((BaseProductClassificationService) getService()).edit(id, detailedDtoClass));
    }

    protected void testEdit_WhenRecordNotUpdated_ThenThrowDataNotUpdatedException(int id, D detailedDtoClass)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String name = (String) detailedDtoClass.getClass().getMethod("getName").invoke(detailedDtoClass);
        int affectedRows = 0;
        when(((BaseProductClassificationRepository) getRepository()).update(id, name))
            .thenReturn(affectedRows);

        assertThrows(DataNotUpdatedException.class,
            () -> ((BaseProductClassificationService) getService()).edit(id, detailedDtoClass));
    }

    protected void testGetAll_WhenDataFound_ThenReturnIt(B briefDtoClass) {
        List<B> expectedResult = List.of(briefDtoClass);
        when(((BaseProductClassificationRepository) getRepository()).findAll())
            .thenReturn(expectedResult);

        List<B> actualResult = ((BaseProductClassificationService) getService()).getAll();

        assertIterableEquals(expectedResult, actualResult);
    }

    protected void testGetAll_WhenDataNotFound_ThenThrowNoResultException() {
        when(((BaseProductClassificationRepository) getRepository()).findAll())
            .thenReturn(Collections.emptyList());

        assertThrows(NoResultException.class,
            () -> ((BaseProductClassificationService) getService()).getAll());
    }

    protected void testRemoveById_WhenDataFound_ThenRemoveIt(int idOfObjectToDelete) {
        when(((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(idOfObjectToDelete))
            .thenReturn(0L);
        int affectedRows = 1;
        when(((BaseRepository) getRepository()).softDelete(idOfObjectToDelete))
            .thenReturn(affectedRows);

        ((BaseProductClassificationService) getService()).removeById(idOfObjectToDelete);

        verify((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(idOfObjectToDelete);
        verify((BaseRepository) getRepository()).softDelete(idOfObjectToDelete);
    }

    protected void testRemoveById_WhenDataNotFound_ThenThrowDataNotDeletedException(int idOfObjectToDelete) {
        when(((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(idOfObjectToDelete))
            .thenReturn(0L);
        int affectedRows = 0;
        when(((BaseRepository) getRepository()).softDelete(idOfObjectToDelete))
            .thenReturn(affectedRows);

        assertThrows(DataNotDeletedException.class,
            () -> ((BaseProductClassificationService) getService()).removeById(idOfObjectToDelete));
    }

    protected void testRemoveById_WhenDataIsAssociatedWithProduct_ThenThrowIllegalArgumentException(int idOfObjectToDelete) {
        when(((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(idOfObjectToDelete))
            .thenReturn(1L);

        assertThrows(IllegalArgumentException.class,
            () -> ((BaseProductClassificationService) getService()).removeById(idOfObjectToDelete));
    }*/

}
