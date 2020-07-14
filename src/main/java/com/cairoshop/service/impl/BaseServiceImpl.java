package com.cairoshop.service.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.configs.Constants;
import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.BaseService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.DataNotDeletedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseServiceImpl<T, D, B>
            extends BaseCommonServiceImpl<D>
            implements BaseService<D, B> {

    private Class<T> entityClass;

    public BaseServiceImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Transactional
    @Override
    public int add(D detailedDto) {
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
            id = ((BaseRepository) getRepository()).save(entity);
        } catch (DataIntegrityViolationException dive) {
            throw new DataIntegrityViolatedException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Transactional
    @Override
    public void removeById(int id) {
        int affectedRows = ((BaseRepository) getRepository()).deleteById(id);
        if (affectedRows == 0) {
            throw new DataNotDeletedException();
        }
    }

    @Override
    public SavedItemsDTO<B> getAll(int startPosition, String sortBy, String sortDirection) {
        List<B> page = ((BaseRepository) getRepository()).findAllByPage(startPosition, Constants.MAX_PAGE_SIZE, sortBy, sortDirection);
        if (page.isEmpty()) {
            throw new NoResultException();
        }
        int allCount = ((BaseRepository) getRepository()).countAllActive();
        SavedItemsDTO<B> savedItemsDTO = new SavedItemsDTO<>();
        savedItemsDTO.setItems(page);
        savedItemsDTO.setAllSavedItemsCount(allCount);
        return savedItemsDTO;
    }

}
