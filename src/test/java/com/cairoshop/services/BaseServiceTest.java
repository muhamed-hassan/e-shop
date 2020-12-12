package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.service.impl.BaseServiceImpl;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@ExtendWith(MockitoExtension.class)
class BaseServiceTest<T, D, B> {

    private Class<T> entityClass;

    private BaseRepository<T, D, B> repository;

    private BaseServiceImpl<T, D, B> service;

    BaseServiceTest(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    void injectRefs(BaseServiceImpl service) {
        this.service = service;
        this.repository = service.getRepository();
    }

    Class<T> getEntityClass() {
        return entityClass;
    }

    BaseRepository<T, D, B> getRepository() {
        return repository;
    }

    BaseServiceImpl<T, D, B> getService() {
        return service;
    }

    void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(D detailedDto)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var entity = mock(entityClass);
        var expectedCreatedId = 1;
        when(entity.getClass().getMethod("getId").invoke(entity))
            .thenReturn(expectedCreatedId);
        when(repository.saveAndFlush(any(entityClass)))
            .thenReturn(entity);

        var actualCreatedId = service.add(detailedDto);

        assertEquals(expectedCreatedId, actualCreatedId);
    }

    void testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(D detailedDto) {
        when(repository.saveAndFlush(any(entityClass)))
            .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolatedException.class,
            () -> service.add(detailedDto));
    }

    void testGetById_WhenDataFound_ThenReturnIt(int id, Optional<D> expectedResult, List<String> getters)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(repository.findByIdAndActive(any(int.class), any(boolean.class)))
            .thenReturn(expectedResult);

        var actualResult = service.getById(id);

        for (var getter : getters) {
            assertEquals(expectedResult.get().getClass().getMethod(getter).invoke(expectedResult.get()),
                            actualResult.getClass().getMethod(getter).invoke(actualResult));
        }
    }

    void testGetAllByPage_WhenDataFound_ThenReturnIt(B briefDto) {
        var page = mock(Page.class);
        when(page.isEmpty())
            .thenReturn(false);
        when(page.getContent())
            .thenReturn(List.of(briefDto));
        when(page.getTotalElements())
            .thenReturn(1L);
        when(repository.findAllByActive(any(boolean.class), any(Pageable.class), any(Class.class)))
            .thenReturn(page);

        var actualResult = service.getAll(0, "id", "ASC");

        assertEquals(page.getTotalElements(), Long.valueOf(actualResult.getAllSavedItemsCount()));
        assertIterableEquals(page.getContent(), actualResult.getItems());
    }

    void testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException() {
        var page = mock(Page.class);
        when(page.isEmpty())
            .thenReturn(true);
        when(repository.findAllByActive(any(boolean.class), any(Pageable.class), any(Class.class)))
            .thenReturn(page);

        assertThrows(NoResultException.class,
            () -> service.getAll(0, "id", "ASC"));
    }

    void testRemoveById_WhenDataFound_ThenRemoveIt(int idOfObjectToDelete) {
        var entity = mock(entityClass);
        when(repository.getOne(idOfObjectToDelete))
            .thenReturn(entity);
        when(repository.save(entity))
            .thenReturn(entity);

        service.removeById(idOfObjectToDelete);

        verify(repository).save(entity);
    }

}
