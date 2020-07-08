package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.BaseService;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseServiceTest<DDTO, BDTO, T> extends BaseCommonServiceTest<DDTO, T> {

    protected BaseServiceTest(Class<T> entityClass, Class<DDTO> ddtoClass) {
        super(entityClass, ddtoClass);
    }

    protected void testGetAllByPage_WhenDataFound_ThenReturnIt(BDTO bdto) {
        List<BDTO> page = List.of(bdto);
        when(((BaseRepository) getRepository()).findAllBy(any(PageRequest.class)))
            .thenReturn(page);
        Long countOfAllActiveItems = 1L;
        when(getRepository().count())
            .thenReturn(countOfAllActiveItems);
        SavedItemsDTO<BDTO> expectedResult = new SavedItemsDTO<>();
        expectedResult.setItems(page);
        expectedResult.setAllSavedItemsCount(countOfAllActiveItems.intValue());

        SavedItemsDTO<BDTO> actualResult = ((BaseService) getService()).getAll(0, "name", "ASC");

        assertEquals(expectedResult.getAllSavedItemsCount(), actualResult.getAllSavedItemsCount());
        assertIterableEquals(expectedResult.getItems(), actualResult.getItems());
    }

    protected void testRemoveById_WhenDataFound_ThenRemoveIt() {
        int idOfObjectToDelete = 1;
        int affectedRows = 1;
        when(((BaseRepository) getRepository()).softDeleteById(idOfObjectToDelete))
            .thenReturn(affectedRows);

        ((BaseService) getService()).removeById(idOfObjectToDelete);

        verify(((BaseRepository) getRepository()), times(1)).softDeleteById(idOfObjectToDelete);
    }

}
