package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.BaseService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.DataNotDeletedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseServiceTest<T, DDTO, BDTO>
            extends BaseCommonServiceTest<T, DDTO> {

    protected BaseServiceTest(Class<T> entityClass, Class<DDTO> ddtoClass) {
        super(entityClass, ddtoClass);
    }

    protected void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(DDTO ddto) throws Exception {
        int expectedCreatedId = 1;
        when(((BaseRepository) getRepository()).save(any(getEntityClass())))
            .thenReturn(expectedCreatedId);

        int actualCreatedId = ((BaseService) getService()).add(ddto);

        assertEquals(expectedCreatedId, actualCreatedId);
    }

    protected void testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(DDTO ddto) throws Exception {
        doThrow(DataIntegrityViolationException.class)
            .when((BaseProductClassificationRepository) getRepository()).save(any(getEntityClass()));

        assertThrows(DataIntegrityViolatedException.class,
            () -> ((BaseService) getService()).add(ddto));
    }

    protected void testRemoveById_WhenDataFound_ThenRemoveIt(int idOfObjectToDelete) {
        int affectedRows = 1;
        when(((BaseRepository) getRepository()).deleteById(idOfObjectToDelete))
            .thenReturn(affectedRows);

        ((BaseService) getService()).removeById(idOfObjectToDelete);

        verify((BaseRepository) getRepository()).deleteById(idOfObjectToDelete);
    }

    protected void testRemoveById_WhenDataNotFound_ThenThrowDataNotDeletedException(int idOfObjectToDelete) {
        int affectedRows = 0;
        when(((BaseRepository) getRepository()).deleteById(idOfObjectToDelete))
            .thenReturn(affectedRows);

        assertThrows(DataNotDeletedException.class,
            () -> ((BaseService) getService()).removeById(idOfObjectToDelete));
    }

    protected void testGetAllByPage_WhenDataFound_ThenReturnIt(BDTO bdto) {
        List<BDTO> page = List.of(bdto);
        when(((BaseRepository) getRepository()).findAllByPage(any(int.class), any(int.class), anyString(), anyString()))
            .thenReturn(page);
        int countOfAllActiveItems = 1;
        when(((BaseRepository) getRepository()).countAllActive())
            .thenReturn(countOfAllActiveItems);
        SavedItemsDTO<BDTO> expectedResult = new SavedItemsDTO<>();
        expectedResult.setItems(page);
        expectedResult.setAllSavedItemsCount(countOfAllActiveItems);

        SavedItemsDTO<BDTO> actualResult = ((BaseService) getService()).getAll(0, "name", "ASC");

        assertEquals(expectedResult.getAllSavedItemsCount(), actualResult.getAllSavedItemsCount());
        assertIterableEquals(expectedResult.getItems(), actualResult.getItems());
    }

    protected void testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException() {
        List<BDTO> page = Collections.emptyList();
        when(((BaseRepository) getRepository()).findAllByPage(any(int.class), any(int.class), anyString(), anyString()))
            .thenReturn(page);

        assertThrows(NoResultException.class,
            () -> ((BaseService) getService()).getAll(0, "name", "ASC"));
    }

}
