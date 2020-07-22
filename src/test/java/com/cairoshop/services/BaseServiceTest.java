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
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@ExtendWith(MockitoExtension.class)
public class BaseServiceTest<T, D, B> {

    private Class<T> entityClass;

    private BaseRepository<T, D, B> repository;

    private BaseServiceImpl<T, D, B> service;

    protected BaseServiceTest(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected void injectRefs(BaseServiceImpl service) {
        this.service = service;
        this.repository = service.getRepository();
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    protected BaseRepository<T, D, B> getRepository() {
        return repository;
    }

    protected BaseServiceImpl<T, D, B> getService() {
        return service;
    }

    protected void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId(D detailedDto)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        T entity = mock(entityClass);
        int expectedCreatedId = 1;
        when(entity.getClass().getMethod("getId").invoke(entity))
            .thenReturn(expectedCreatedId);
        when(repository.saveAndFlush(any(entityClass)))
            .thenReturn(entity);

        int actualCreatedId = service.add(detailedDto);

        assertEquals(expectedCreatedId, actualCreatedId);
    }

    protected void testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException(D detailedDto) {
        when(repository.saveAndFlush(any(entityClass)))
            .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolatedException.class,
            () -> service.add(detailedDto));
    }

    protected void testGetById_WhenDataFound_ThenReturnIt(int id, Optional<D> expectedResult, List<String> getters)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(repository.findByIdAndActive(any(int.class), any(boolean.class)))
            .thenReturn(expectedResult);

        D actualResult = service.getById(id);

        for (String getter : getters) {
            assertEquals(expectedResult.get().getClass().getMethod(getter).invoke(expectedResult.get()),
                            actualResult.getClass().getMethod(getter).invoke(actualResult));
        }
    }

    protected void testGetAllByPage_WhenDataFound_ThenReturnIt(B briefDto) {
        Page<B> page = mock(Page.class);
        when(page.isEmpty())
            .thenReturn(false);
        when(page.getContent())
            .thenReturn(List.of(briefDto));
        when(page.getTotalElements())
            .thenReturn(1L);
        when(repository.findAllByActive(any(boolean.class), any(Pageable.class), any(Class.class)))
            .thenReturn(page);

        SavedItemsDTO<B> actualResult = service.getAll(0, "id", "ASC");

        assertEquals(page.getTotalElements(), Long.valueOf(actualResult.getAllSavedItemsCount()));
        assertIterableEquals(page.getContent(), actualResult.getItems());
    }

    protected void testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException() {
        Page<B> page = mock(Page.class);
        when(page.isEmpty())
            .thenReturn(true);
        when(repository.findAllByActive(any(boolean.class), any(Pageable.class), any(Class.class)))
            .thenReturn(page);

        assertThrows(NoResultException.class,
            () -> service.getAll(0, "id", "ASC"));
    }

    protected void testRemoveById_WhenDataFound_ThenRemoveIt(int idOfObjectToDelete)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        T entity = mock(entityClass);
        int id = 1;
        when(repository.getOne(id))
            .thenReturn(entity);
        when(repository.save(entity))
            .thenReturn(entity);

        service.removeById(id);

        verify(repository).save(entity);
    }

}
