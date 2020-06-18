package com.cairoshop.service.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.BaseService;
import com.cairoshop.service.exceptions.DataNotDeletedException;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
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

    public BaseRepository<SDTO, T, Integer> getRepository() {
        return repository;
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

    @Override
    public SDTO getById(int id) {
        return repository.findById(id, savedDtoClass)
                            .orElseThrow(NoResultException::new);
    }

    @Transactional
    @Override
    public void removeById(int id) {
        int affectedRows = repository.softDeleteById(id);
        if (affectedRows == 0) {
            throw new DataNotDeletedException();
        }
    }

    @Override
    public List<SDTO> getAll(int startPosition, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        switch (sortDirection) {
            case "DESC":
                sort = sort.descending();
                break;
            case "ASC":
                sort = sort.ascending();
                break;
            default:
                throw new IllegalArgumentException("Allowed sort directions are DESC and ASC");
        }
        final int MAX_PAGE_SIZE = 5;
        return repository.findAllBy(PageRequest.of(startPosition, MAX_PAGE_SIZE, sort));
    }

}
