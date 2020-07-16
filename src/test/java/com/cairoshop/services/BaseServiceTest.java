package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.BaseService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.DataNotDeletedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseServiceTest//<T, D, B>
           /* extends BaseCommonServiceTest<T, D, B>*/ {

    /*protected BaseServiceTest(Class<T> entityClass/*, Class<D> detailedDtoClass, Class<B> briefDtoClass) {
        super(entityClass, detailedDtoClass, briefDtoClass);
    }

    protected void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(D detailedDtoClass)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int expectedCreatedId = 1;
        when(getRepository().save(any(getEntityClass())))
            .thenReturn(expectedCreatedId);

        int actualCreatedId = ((BaseService) getService()).add(detailedDtoClass);

        assertEquals(expectedCreatedId, actualCreatedId);
    }

    protected void testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(D detailedDtoClass) {
        doThrow(DataIntegrityViolationException.class)
            .when((BaseProductClassificationRepository) getRepository()).save(any(getEntityClass()));

        assertThrows(DataIntegrityViolatedException.class,
            () -> ((BaseService) getService()).add(detailedDtoClass));
    }

    protected void testRemoveById_WhenDataFound_ThenRemoveIt(int idOfObjectToDelete) {
        int affectedRows = 1;
        when(((BaseRepository) getRepository()).softDelete(idOfObjectToDelete))
            .thenReturn(affectedRows);

        ((BaseService) getService()).removeById(idOfObjectToDelete);

        verify((BaseRepository) getRepository()).softDelete(idOfObjectToDelete);
    }

    protected void testRemoveById_WhenDataNotFound_ThenThrowDataNotDeletedException(int idOfObjectToDelete) {
        int affectedRows = 0;
        when(((BaseRepository) getRepository()).softDelete(idOfObjectToDelete))
            .thenReturn(affectedRows);

        assertThrows(DataNotDeletedException.class,
            () -> ((BaseService) getService()).removeById(idOfObjectToDelete));
    }*/



}
