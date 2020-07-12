package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.service.impl.BaseCommonServiceImpl;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@ExtendWith(MockitoExtension.class)
public class BaseCommonServiceTest<T, DDTO> {

    private Class<T> entityClass;
    private Class<DDTO> ddtoClass;

    private BaseCommonRepository baseCommonRepository;
    private BaseCommonServiceImpl baseCommonService;

    protected BaseCommonServiceTest(Class<T> entityClass, Class<DDTO> ddtoClass) {
        this.entityClass = entityClass;
        this.ddtoClass = ddtoClass;
    }

    protected void injectRefs(BaseCommonRepository baseCommonRepository, BaseCommonServiceImpl baseCommonService) {
        this.baseCommonRepository = baseCommonRepository;
        this.baseCommonService = baseCommonService;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    protected Class<DDTO> getDetailedDtoClass() {
        return ddtoClass;
    }

    protected BaseCommonRepository getRepository() {
        return baseCommonRepository;
    }

    protected BaseCommonServiceImpl getService() {
        return baseCommonService;
    }

    protected void testGetById_WhenDataFound_ThenReturnIt(int id, DDTO expectedResult, List<String> getters) throws Exception {
        when(getRepository().findById(any(int.class)))
            .thenReturn(expectedResult);

        DDTO actualResult = (DDTO) getService().getById(id);

        for (String getter : getters) {
            assertEquals(expectedResult.getClass().getMethod(getter).invoke(expectedResult),
                            actualResult.getClass().getMethod(getter).invoke(actualResult));
        }
    }

    protected void testGetById_WhenDataNotFound_ThenThrowNoResultException(int id) {
        doThrow(EmptyResultDataAccessException.class)
            .when(getRepository()).findById(any(int.class));

        assertThrows(NoResultException.class,
            () -> getService().getById(id));
    }

}
