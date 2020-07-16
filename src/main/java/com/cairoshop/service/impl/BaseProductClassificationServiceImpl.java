package com.cairoshop.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.service.BaseProductClassificationService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseProductClassificationServiceImpl<T, D, B>
            extends BaseServiceImpl<T, D, B>
            implements BaseProductClassificationService<D, B> {

    protected BaseProductClassificationServiceImpl(Class<T> entityClass, Class<B> briefDtoClass) {
        super(entityClass, briefDtoClass);
    }

    @Transactional
    @Override
    public void edit(int id, D detailedDto)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        try {
            String name = (String) detailedDto.getClass().getMethod("getName").invoke(detailedDto);
            T entity = getRepository().getOne(id);
            entity.getClass().getMethod("setName", String.class).invoke(entity, name);
            getRepository().save(entity);
        } catch (DataIntegrityViolationException dive) {
            throw new DataIntegrityViolatedException();
        }
    }

    @Override
    public List<B> getAll() {
        List<B> result = ((BaseProductClassificationRepository) getRepository()).findAllByActive(true, getBriefDtoClass());
        if (result.isEmpty()) {
            throw new NoResultException();
        }
        return result;
    }

    @Transactional
    @Override
    public void removeById(int id)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        boolean notSafeToDelete = ((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(id) > 0L;
        if (notSafeToDelete) {
            throw new IllegalArgumentException("Can not delete this item because it is associated with an active product");
        }
        super.removeById(id);
    }

}
