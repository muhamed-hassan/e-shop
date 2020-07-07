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
import com.cairoshop.web.dtos.SavedBriefVendorDTO;

public class BaseProductClassificationServiceTest<NDTO, SDDTO, SBDTO, T>
        extends BaseServiceTest<SDDTO, SBDTO, T> {

    protected BaseProductClassificationServiceTest(Class<T> entityClass, Class<SDDTO> sddtoClass) {
        super(entityClass, sddtoClass);
    }

    protected void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(NDTO ndto) throws Exception {
        int expectedCreatedId = 1;
        T savedEntity = mock(getEntityClass());
        when((int) savedEntity.getClass().getMethod("getId").invoke(savedEntity))
            .thenReturn(expectedCreatedId);
        when(getRepository().save(any(getEntityClass())))
            .thenReturn(savedEntity);

        int actualCreatedId = ((BaseProductClassificationService) getService()).add(ndto);

        assertEquals(expectedCreatedId, actualCreatedId);
    }

    protected void testEdit_WhenDataIsValid_ThenSave(SDDTO sddto) throws Exception {
        int id = (int) getSavedDetailedDtoClass().getMethod("getId").invoke(sddto);
        String name = (String) getSavedDetailedDtoClass().getMethod("getName").invoke(sddto);
        int affectedRows = 1;
        when(((BaseProductClassificationRepository) getRepository()).update(id,name))
            .thenReturn(affectedRows);

        ((BaseProductClassificationService) getService()).edit(sddto);

        verify(((BaseProductClassificationRepository) getRepository()), times(1)).update(id, name);
    }

    protected void testGetAll_WhenDataFound_ThenReturnIt(SBDTO sbdto) {
        List<SBDTO> expectedResult = List.of(sbdto);
        when(((BaseProductClassificationRepository) getRepository()).findAllBy())
            .thenReturn(expectedResult);

        List<SavedBriefVendorDTO> actualResult = ((BaseProductClassificationService) getService()).getAll();

        assertIterableEquals(expectedResult, actualResult);
    }

}
