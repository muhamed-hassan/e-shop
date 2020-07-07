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
public class BaseCommonServiceTest<SDDTO, T> {

    private Class<SDDTO> sddtoClass;
    private Class<T> entityClass;

    private BaseCommonRepository baseCommonRepository;
    private BaseCommonServiceImpl baseCommonService;

    protected BaseCommonServiceTest(Class<T> entityClass, Class<SDDTO> sddtoClass) {
        this.entityClass = entityClass;
        this.sddtoClass = sddtoClass;
    }

    protected void injectRefs(BaseCommonRepository baseCommonRepository, BaseCommonServiceImpl baseCommonService) {
        this.baseCommonRepository = baseCommonRepository;
        this.baseCommonService = baseCommonService;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    protected Class<SDDTO> getSavedDetailedDtoClass() {
        return sddtoClass;
    }

    protected BaseCommonRepository getRepository() {
        return baseCommonRepository;
    }

    protected BaseCommonServiceImpl getService() {
        return baseCommonService;
    }

    protected void testGetById_WhenDataFound_ThenReturnIt(SDDTO sddto, List<String> getters) throws Exception {
        Optional<SDDTO> expectedResult = Optional.of(sddto);
        when(getRepository().findById(any(int.class), any(getSavedDetailedDtoClass().getClass())))
            .thenReturn(expectedResult);

        SDDTO actualResult = (SDDTO) getService().getById(1);

        for (String getter : getters) {
            assertEquals(expectedResult.get().getClass().getMethod(getter).invoke(sddto), actualResult.getClass().getMethod(getter).invoke(actualResult));
        }
    }

}
