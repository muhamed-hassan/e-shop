package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;

//import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.service.BaseService;
import com.cairoshop.service.exceptions.NoResultException;
//import com.cairoshop.service.impl.BaseCommonServiceImpl;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@ExtendWith(MockitoExtension.class)
public class BaseCommonServiceTest<T, D, B> {

    private Class<T> entityClass;

    private Class<D> detailedDtoClass;

    private Class<B> briefDtoClass;

//    private BaseCommonRepository baseCommonRepository;
//
//    private BaseCommonServiceImpl baseCommonService;

    /*protected BaseCommonServiceTest(Class<T> entityClass, Class<D> detailedDtoClass, Class<B> briefDtoClass) {
        this.entityClass = entityClass;
        this.detailedDtoClass = detailedDtoClass;
        this.briefDtoClass = briefDtoClass;
    }*/

//    protected void injectRefs(BaseCommonRepository baseCommonRepository, BaseCommonServiceImpl baseCommonService) {
//        this.baseCommonRepository = baseCommonRepository;
//        this.baseCommonService = baseCommonService;
//    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    protected Class<D> getDetailedDtoClass() {
        return detailedDtoClass;
    }

    protected Class<B> getBriefDtoClass() {
        return briefDtoClass;
    }

    /*protected BaseCommonRepository getRepository() {
        return baseCommonRepository;
    }

    protected BaseCommonServiceImpl getService() {
        return baseCommonService;
    }*/

    /*protected void testGetById_WhenDataFound_ThenReturnIt(int id, Optional<D> expectedResult, List<String> getters)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(getRepository().findByIdAndActive(any(int.class), any(boolean.class)))
            .thenReturn(expectedResult);

        D actualResult = (D) getService().getById(id);

        for (String getter : getters) {
            assertEquals(expectedResult.getClass().getMethod(getter).invoke(expectedResult),
                actualResult.getClass().getMethod(getter).invoke(actualResult));
        }
    }

    protected void testGetById_WhenDataNotFound_ThenThrowNoResultException(int id) {
        doThrow(EmptyResultDataAccessException.class)
            .when(getRepository()).findByIdAndActive(any(int.class), any(boolean.class));

        assertThrows(NoResultException.class,
            () -> getService().getById(id));
    }

    //int startPosition, String sortBy, String sortDirection
    protected void testGetAllByPage_WhenDataFound_ThenReturnIt(B briefDtoClass) {
        Page<B> page = mock(Page.class);//List.of(briefDtoClass);
                when(((BaseRepository) getRepository()).findAllByActive(any(boolean.class), any(Pageable.class), any(getBriefDtoClass())))
                    .thenReturn(page);
        int countOfAllActiveItems = 1;
                when(((BaseRepository) getRepository()).countAllActive())
                    .thenReturn(countOfAllActiveItems);
        SavedItemsDTO<B> expectedResult = null;//new SavedItemsDTO<>(page, countOfAllActiveItems);

        SavedItemsDTO<B> actualResult = getService().getAll(0, "name", "ASC");

        assertEquals(expectedResult.getAllSavedItemsCount(), actualResult.getAllSavedItemsCount());
        assertIterableEquals(expectedResult.getItems(), actualResult.getItems());
    }

    protected void testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException() {
        List<B> page = Collections.emptyList();
        //        when(((BaseRepository) getRepository()).findAllByPage(any(int.class), any(int.class), anyString(), anyString()))
        //            .thenReturn(page);

        assertThrows(NoResultException.class,
            () -> ((BaseService) getService()).getAll(0, "name", "ASC"));
    }*/

}
