package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.data.domain.PageRequest;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.service.impl.BaseProductClassificationServiceImpl;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedBriefCategoryDTO;
import com.cairoshop.web.dtos.SavedBriefVendorDTO;
import com.cairoshop.web.dtos.SavedDetailedCategoryDTO;
import com.cairoshop.web.dtos.SavedDetailedVendorDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

public class BaseProductClassificationServiceTest<NDTO, SDDTO, SBDTO, T> /*extends BaseServiceTest<NDTO, SDDTO, SBDTO, T>*/ {

    private Class<SDDTO> sddtoClass;
    private Class<T> entityClass;

    private BaseProductClassificationRepository baseProductClassificationRepository;
    private BaseProductClassificationServiceImpl baseProductClassificationService;

    protected BaseProductClassificationServiceTest(Class<T> entityClass, Class<SDDTO> sddtoClass) {
        this.entityClass = entityClass;
        this.sddtoClass = sddtoClass;
    }

    protected void injectRefs(BaseProductClassificationRepository baseProductClassificationRepository, BaseProductClassificationServiceImpl baseProductClassificationService) {
        this.baseProductClassificationRepository = baseProductClassificationRepository;
        this.baseProductClassificationService = baseProductClassificationService;
    }


/*
    @Test
    public void testGetAll() {
        List<SavedBriefVendorDTO> expectedResult = List.of(new SavedBriefVendorDTO(1, "Toshiba", true));
        when(baseProductClassificationRepository.findAllBy())
            .thenReturn(expectedResult);

        List<SavedBriefVendorDTO> actualResult = baseProductClassificationService.getAll();

        assertIterableEquals(expectedResult, actualResult);
    }*/

    protected void testAdd(NDTO ndto) throws Exception {
        int expectedCreatedId = 1;
        T savedEntity = mock(entityClass);
        when((int) savedEntity.getClass().getMethod("getId").invoke(savedEntity))
            .thenReturn(expectedCreatedId);
        when(baseProductClassificationRepository.save(any(entityClass)))
            .thenReturn(savedEntity);

        int actualCreatedId = baseProductClassificationService.add(ndto);

        assertEquals(expectedCreatedId, actualCreatedId);
    }

    protected void testEdit(SDDTO sddto) throws Exception {
        int id = (int) sddtoClass.getMethod("getId").invoke(sddto);
        String name = (String) sddtoClass.getMethod("getName").invoke(sddto);
        int affectedRows = 1;
        when(baseProductClassificationRepository.update(id,name))
            .thenReturn(affectedRows);

        baseProductClassificationService.edit(sddto);

        verify(baseProductClassificationRepository, times(1)).update(id, name);
    }

    protected void testRemoveById() {
        int idOfObjectToDelete = 1;
        int affectedRows = 1;
        when(baseProductClassificationRepository.softDeleteById(idOfObjectToDelete))
            .thenReturn(affectedRows);

        baseProductClassificationService.removeById(idOfObjectToDelete);

        verify(baseProductClassificationRepository, times(1)).softDeleteById(idOfObjectToDelete);
    }

    protected void testGetById(SDDTO sddto) throws Exception {
        Optional<SDDTO> expectedResult = Optional.of(sddto);
        when(baseProductClassificationRepository.findById(1, sddtoClass))
            .thenReturn(expectedResult);

        SDDTO actualResult = (SDDTO) baseProductClassificationService.getById(1);

        assertEquals(expectedResult.get().getClass().getMethod("getId").invoke(sddto), actualResult.getClass().getMethod("getId").invoke(actualResult));
        assertEquals(expectedResult.get().getClass().getMethod("getName").invoke(sddto), actualResult.getClass().getMethod("getName").invoke(actualResult));
        assertEquals(expectedResult.get().getClass().getMethod("isActive").invoke(sddto), actualResult.getClass().getMethod("isActive").invoke(actualResult));
    }

    protected void testGetAllByPage(SBDTO sbdto) {
        List<SBDTO> page = List.of(sbdto);
        when(baseProductClassificationRepository.findAllBy(any(PageRequest.class)))
            .thenReturn(page);
        when(baseProductClassificationRepository.count())
            .thenReturn(1L);
        SavedItemsDTO<SBDTO> expectedResult = new SavedItemsDTO<>();
        expectedResult.setItems(page);
        expectedResult.setAllSavedItemsCount(1);

        SavedItemsDTO<SBDTO> actualResult = baseProductClassificationService.getAll(0, "id", "ASC");

        assertEquals(expectedResult.getAllSavedItemsCount(), actualResult.getAllSavedItemsCount());
        assertIterableEquals(expectedResult.getItems(), actualResult.getItems());
    }


}
