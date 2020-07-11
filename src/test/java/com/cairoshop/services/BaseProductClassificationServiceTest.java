package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.service.BaseProductClassificationService;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseProductClassificationServiceTest<DDTO, BDTO, T>
        extends BaseServiceTest<DDTO, BDTO, T> {

    protected BaseProductClassificationServiceTest(Class<T> entityClass, Class<DDTO> DDTOClass) {
        super(entityClass, DDTOClass);
    }

    protected void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(DDTO ddto) throws Exception {
        int expectedCreatedId = 1;
        T savedEntity = mock(getEntityClass());
        when((int) savedEntity.getClass().getMethod("getId").invoke(savedEntity))
            .thenReturn(expectedCreatedId);
//        when(getRepository().save(any(getEntityClass())))
//            .thenReturn(savedEntity);

        int actualCreatedId = ((BaseProductClassificationService) getService()).add(ddto);

        assertEquals(expectedCreatedId, actualCreatedId);
    }

    protected void testEdit_WhenDataIsValid_ThenSave(int id, DDTO ddto) throws Exception {
        String name = (String) getDetailedDtoClass().getMethod("getName").invoke(ddto);
        int affectedRows = 1;
        when(((BaseProductClassificationRepository) getRepository()).update(id, name))
            .thenReturn(affectedRows);

        ((BaseProductClassificationService) getService()).edit(id, ddto);

        verify(((BaseProductClassificationRepository) getRepository()), times(1)).update(id, name);
    }

    protected void testGetAll_WhenDataFound_ThenReturnIt(BDTO bdto) {
        List<BDTO> expectedResult = List.of(bdto);
        when(((BaseProductClassificationRepository) getRepository()).findAll())
            .thenReturn(expectedResult);

        List<BDTO> actualResult = ((BaseProductClassificationService) getService()).getAll();

        assertIterableEquals(expectedResult, actualResult);
    }

}
