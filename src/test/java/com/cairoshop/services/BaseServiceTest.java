package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;

import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.BaseService;
import com.cairoshop.service.impl.BaseCommonServiceImpl;
import com.cairoshop.web.dtos.SavedItemsDTO;

public class BaseServiceTest<NDTO, SDDTO, SBDTO, T> /*extends BaseCommonServiceTest<SDDTO, SBDTO, T>*/ {

    private Class<SDDTO> sddtoClass;
    private Class<T> entityClass;

    private BaseCommonRepository baseCommonRepository;
    private BaseCommonServiceImpl baseCommonService;

    protected BaseServiceTest(Class<T> entityClass, Class<SDDTO> sddtoClass) {
        this.entityClass = entityClass;
        this.sddtoClass = sddtoClass;
    }

    protected void injectRefs(BaseCommonRepository baseCommonRepository, BaseCommonServiceImpl baseCommonService) {
        this.baseCommonRepository = baseCommonRepository;
        this.baseCommonService = baseCommonService;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public Class<SDDTO> getSavedDetailedDtoClass() {
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

    protected void testGetAllByPage_WhenDataFound_ThenReturnIt(SBDTO sbdto) {
        List<SBDTO> page = List.of(sbdto);
        when(getRepository().findAllBy(any(PageRequest.class)))
            .thenReturn(page);
        when(getRepository().count())
            .thenReturn(1L);
        SavedItemsDTO<SBDTO> expectedResult = new SavedItemsDTO<>();
        expectedResult.setItems(page);
        expectedResult.setAllSavedItemsCount(1);

        SavedItemsDTO<SBDTO> actualResult = getService().getAll(0, "name", "ASC");

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
