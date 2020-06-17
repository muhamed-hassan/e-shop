package com.cairoshop.service.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.BaseService;

public class BaseServiceImpl<NDTO, SDTO, T> implements BaseService<NDTO, SDTO, T> {

    private BaseRepository<SDTO, T, Integer> repository;
    private Class<T> entityClass;
    private Class<SDTO> savedDtoClass;

    public BaseServiceImpl(Class<T> entityClass, Class<SDTO> savedDtoClass) {
        this.entityClass = entityClass;
        this.savedDtoClass = savedDtoClass;
    }

    protected void setRepos(BaseRepository<SDTO, T, Integer> repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public int add(NDTO ndto) {
        Integer id = -1;
        try {
            T entity = entityClass.newInstance();
            Method[] dtoMethods = ndto.getClass().getMethods();
            for (Method method : dtoMethods) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.equals("getClass")) {
                    Object valueFromDto = method.invoke(ndto);
                    String setterNameFromEntity = methodName.replace("get", "set");
                    entity.getClass().getMethod(setterNameFromEntity, method.getReturnType()).invoke(entity, valueFromDto);
                }
            }
            entity.getClass().getMethod("setActive", boolean.class).invoke(entity, true);
            entity = repository.save(entity);
            id = (Integer) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Transactional
    @Override
    public void edit(Map<String, Object> fields) {
        int affectedRows = repository.update((String) fields.get("name"), (Integer) fields.get("id"));
    }

    @Override
    public SDTO getById(int id) {
        return repository.findById(id, savedDtoClass).get();
    }

    @Transactional
    @Override
    public void removeById(int id) {
        repository.softDeleteById(id);
    }

    @Override
    public List<SDTO> getAll() {

        return repository.findAllBy();
    }

    @Override
    public List<SDTO> getAll(int startPosition, String sortBy, String sortDirection) {

        Sort sort = Sort.by(sortBy);
        // TODO enhance later
        if ( sortDirection.equals("DESC") ) {
            sort = sort.descending();
        } else { // ASC
            sort = sort.ascending();
        }

        Pageable sortedByPriceDesc =
            PageRequest.of(startPosition, 5, sort);
        return repository.findAllBy(sortedByPriceDesc);
    }
}
