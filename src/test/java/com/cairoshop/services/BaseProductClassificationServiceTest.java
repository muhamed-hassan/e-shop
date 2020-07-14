package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseProductClassificationServiceTest<T, DDTO, BDTO>
            extends BaseServiceTest<T, DDTO, BDTO> {

    protected BaseProductClassificationServiceTest(Class<T> entityClass, Class<DDTO> DDTOClass) {
        super(entityClass, DDTOClass);
    }

    protected void testEdit_WhenDataIsValid_ThenSave(int id, DDTO ddto) throws Exception {
        int affectedRows = 1;
        when(((BaseProductClassificationRepository) getRepository()).update(id, ddto))
            .thenReturn(affectedRows);

        ((BaseProductClassificationService) getService()).edit(id, ddto);

        verify(((BaseProductClassificationRepository) getRepository())).update(id, ddto);
    }

    protected void testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(int id, DDTO ddto) throws Exception {
        doThrow(DataIntegrityViolationException.class)
            .when((BaseProductClassificationRepository) getRepository()).update(any(int.class), any(getDetailedDtoClass()));

        assertThrows(DataIntegrityViolatedException.class,
            () -> ((BaseProductClassificationService) getService()).edit(id, ddto));
    }

    protected void testEdit_WhenRecordNotUpdated_ThenThrowDataNotUpdatedException(int id, DDTO ddto) throws Exception {
        int affectedRows = 0;
        when(((BaseProductClassificationRepository) getRepository()).update(id, ddto))
            .thenReturn(affectedRows);

        assertThrows(DataNotUpdatedException.class,
            () -> ((BaseProductClassificationService) getService()).edit(id, ddto));
    }

    protected void testGetAll_WhenDataFound_ThenReturnIt(BDTO bdto) {
        List<BDTO> expectedResult = List.of(bdto);
        when(((BaseProductClassificationRepository) getRepository()).findAll())
            .thenReturn(expectedResult);

        List<BDTO> actualResult = ((BaseProductClassificationService) getService()).getAll();

        assertIterableEquals(expectedResult, actualResult);
    }

    protected void testGetAll_WhenDataNotFound_ThenThrowNoResultException() {
        when(((BaseProductClassificationRepository) getRepository()).findAll())
            .thenReturn(Collections.emptyList());

        assertThrows(NoResultException.class,
            () -> ((BaseProductClassificationService) getService()).getAll());
    }

    protected void testRemoveById_WhenDataFound_ThenRemoveIt(int idOfObjectToDelete) {
        when(((BaseProductClassificationRepository) getRepository()).safeToDelete(idOfObjectToDelete))
            .thenReturn(true);
        int affectedRows = 1;
        when(((BaseRepository) getRepository()).deleteById(idOfObjectToDelete))
            .thenReturn(affectedRows);

        ((BaseProductClassificationService) getService()).removeById(idOfObjectToDelete);

        verify((BaseProductClassificationRepository) getRepository()).safeToDelete(idOfObjectToDelete);
        verify((BaseRepository) getRepository()).deleteById(idOfObjectToDelete);
    }

    protected void testRemoveById_WhenDataNotFound_ThenThrowDataNotDeletedException(int idOfObjectToDelete) {
        when(((BaseProductClassificationRepository) getRepository()).safeToDelete(idOfObjectToDelete))
            .thenReturn(true);
        int affectedRows = 0;
        when(((BaseRepository) getRepository()).deleteById(idOfObjectToDelete))
            .thenReturn(affectedRows);

        assertThrows(DataNotDeletedException.class,
            () -> ((BaseProductClassificationService) getService()).removeById(idOfObjectToDelete));
    }

    protected void testRemoveById_WhenDataIsAssociatedWithProduct_ThenThrowIllegalArgumentException(int idOfObjectToDelete) {
        when(((BaseProductClassificationRepository) getRepository()).safeToDelete(idOfObjectToDelete))
            .thenReturn(false);

        assertThrows(IllegalArgumentException.class,
            () -> ((BaseProductClassificationService) getService()).removeById(idOfObjectToDelete));
    }

}
