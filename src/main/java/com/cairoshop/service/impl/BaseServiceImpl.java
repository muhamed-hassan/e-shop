package com.cairoshop.service.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
public class BaseServiceImpl<T, DDTO, BDTO>
            extends BaseCommonServiceImpl<DDTO>
            implements BaseService<DDTO, BDTO> {

    private Class<T> entityClass;

    public BaseServiceImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Transactional
    @Override
    public int add(DDTO ddto) {
        int id;
        try {
            T entity = getEntityClass().getDeclaredConstructor().newInstance();
            Method[] dtoMethods = ddto.getClass().getMethods();
            for (Method method : dtoMethods) {
                String methodName = method.getName();
                if (methodName.startsWith("get") && !methodName.equals("getClass")) {
                    Object valueFromDto = method.invoke(ddto);
                    String setterNameFromEntity = methodName.replace("get", "set");
                    entity.getClass().getMethod(setterNameFromEntity, method.getReturnType()).invoke(entity, valueFromDto);
                }
            }
            entity.getClass().getMethod("setActive", boolean.class).invoke(entity, true);
            id = ((BaseRepository) getRepository()).save(entity);
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                throw new DataIntegrityViolatedException();
            }
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
    public SavedItemsDTO<BDTO> getAll(int startPosition, String sortBy, String sortDirection) {
        List<BDTO> page = ((BaseRepository) getRepository()).findAllByPage(startPosition, Constants.MAX_PAGE_SIZE, sortBy, sortDirection);
        if (page.isEmpty()) {
            throw new NoResultException();
        }
        int allCount = ((BaseRepository) getRepository()).countAllActive();
        SavedItemsDTO<BDTO> BDTOSavedItemsDTO = new SavedItemsDTO<>();
        BDTOSavedItemsDTO.setItems(page);
        BDTOSavedItemsDTO.setAllSavedItemsCount(allCount);
        return BDTOSavedItemsDTO;
    }

}
