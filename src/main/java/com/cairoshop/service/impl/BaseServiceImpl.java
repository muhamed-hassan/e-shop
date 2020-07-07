package com.cairoshop.service.impl;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.configs.Constants;
import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.BaseService;
import com.cairoshop.service.exceptions.DataNotDeletedException;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseServiceImpl<NDTO, SDDTO, SBDTO, T>
                extends BaseCommonServiceImpl<SDDTO, SBDTO, T>
                implements BaseService<NDTO, SDDTO, SBDTO, T> {

    private Class<T> entityClass;

    public BaseServiceImpl(Class<T> entityClass, Class<SDDTO> savedDetailedDtoClass) {
        super(savedDetailedDtoClass);
        this.entityClass = entityClass;
    }

    @Transactional
    @Override
    public int add(NDTO ndto) {
        int id = -1;
        try {
            T entity = entityClass.getDeclaredConstructor().newInstance();
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
            entity = getRepository().save(entity);
            id = (int) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Transactional
    @Override
    public void removeById(int id) {
        int affectedRows = ((BaseRepository) getRepository()).softDeleteById(id);
        if (affectedRows == 0) {
            throw new DataNotDeletedException();
        }
    }

    @Override
    public SavedItemsDTO<SBDTO> getAll(int startPosition, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        sortDirection = sortDirection.toLowerCase();
        switch (sortDirection) {
            case "desc":
                sort = sort.descending();
                break;
            case "asc":
                sort = sort.ascending();
                break;
        }
        List<SBDTO> page = ((BaseRepository) getRepository()).findAllBy(PageRequest.of(startPosition, Constants.MAX_PAGE_SIZE, sort));
        long allCount = ((BaseRepository) getRepository()).count();
        SavedItemsDTO<SBDTO> sbdtoSavedItemsDTO = new SavedItemsDTO<>();
        sbdtoSavedItemsDTO.setItems(page);
        sbdtoSavedItemsDTO.setAllSavedItemsCount(Long.valueOf(allCount).intValue());
        return sbdtoSavedItemsDTO;
    }

}
