package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.service.impl.BaseCommonServiceImpl;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseCommonServiceTest<DDTO, T> {

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

    protected void testGetById_WhenDataFound_ThenReturnIt(DDTO ddto, List<String> getters) throws Exception {
        Optional<DDTO> expectedResult = Optional.of(ddto);
        when(getRepository().findById(any(int.class), any(getDetailedDtoClass().getClass())))
            .thenReturn(expectedResult);

        DDTO actualResult = (DDTO) getService().getById(1);

        for (String getter : getters) {
            assertEquals(expectedResult.get().getClass().getMethod(getter).invoke(ddto), actualResult.getClass().getMethod(getter).invoke(actualResult));
        }
    }

}
