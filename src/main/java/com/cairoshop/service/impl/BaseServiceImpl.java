package com.cairoshop.service.impl;

import static com.cairoshop.configs.Constants.MAX_PAGE_SIZE;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.BaseService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseServiceImpl<T, D, B>
            implements BaseService<D, B> {

    private BaseRepository<T, D, B> repository;

    private Class<T> entityClass;

    private Class<B> briefDtoClass;

    public BaseServiceImpl(Class<T> entityClass, Class<B> briefDtoClass, BaseRepository<T, D, B> repository) {
        this.briefDtoClass = briefDtoClass;
        this.entityClass = entityClass;
        this.repository = repository;
    }

    public BaseRepository<T, D, B> getRepository() {
        return repository;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public Class<B> getBriefDtoClass() {
        return briefDtoClass;
    }

    public Sort sortFrom(String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        switch (sortDirection) {
            case "DESC":
                sort = sort.descending();
                break;
            case "ASC":
                sort = sort.ascending();
                break;
            default:
                throw new IllegalArgumentException("allowed sort directions are DESC or ASC");
        }
        return sort;
    }

    @Transactional
    @Override
    public int add(D detailedDto)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        int id;
        try {
            T entity = getEntityClass().getDeclaredConstructor().newInstance();
            Method[] dtoMethods = detailedDto.getClass().getMethods();
            for (Method method : dtoMethods) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.equals("getClass")) {
                    Object valueFromDto = method.invoke(detailedDto);
                    String setterNameFromEntity = methodName.replace("get", "set");
                    entity.getClass().getMethod(setterNameFromEntity, method.getReturnType()).invoke(entity, valueFromDto);
                }
            }
            entity.getClass().getMethod("setActive", boolean.class).invoke(entity, true);
            entity = repository.saveAndFlush(entity);
            id = (int) entity.getClass().getMethod("getId").invoke(entity);
        } catch (DataIntegrityViolationException dive) {
            throw new DataIntegrityViolatedException();
        }
        return id;
    }

    @Override
    public D getById(int id) {
        return repository.findByIdAndActive(id, true)
                            .orElseThrow(NoResultException::new);
    }

    @Override
    public SavedItemsDTO<B> getAll(int startPosition, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(startPosition, MAX_PAGE_SIZE, sortFrom(sortBy, sortDirection));
        Page<B> page = repository.findAllByActive(true, pageable, briefDtoClass);
        if (page.isEmpty()) {
            throw new NoResultException();
        }
        return new SavedItemsDTO<>(page.getContent(), Long.valueOf(page.getTotalElements()).intValue());
    }

    @Transactional
    @Override
    public void removeById(int id) {
        try {
            T entity = getRepository().getOne(id);
            entity.getClass().getMethod("setActive", boolean.class).invoke(entity, false);
            getRepository().save(entity);
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getClass() == EntityNotFoundException.class) {
                throw new NoResultException();
            }
            throw new RuntimeException(e);
        }
    }

}
