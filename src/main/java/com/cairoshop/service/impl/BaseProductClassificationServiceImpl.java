package com.cairoshop.service.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

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

    public BaseProductClassificationServiceImpl(Class<T> entityClass, Class<B> briefDtoClass, BaseProductClassificationRepository<T, D, B> repository) {
        super(entityClass, briefDtoClass, repository);
    }

    @Transactional
    @Override
    public void edit(int id, D detailedDto) {
        try {
            var name = (String) detailedDto.getClass().getMethod("getName").invoke(detailedDto);
            var entity = getRepository().getOne(id);
            entity.getClass().getMethod("setName", String.class).invoke(entity, name);
            getRepository().saveAndFlush(entity);
        } catch (DataIntegrityViolationException dive) {
            throw new DataIntegrityViolatedException();
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getClass() == EntityNotFoundException.class) {
                throw new NoResultException();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<B> getAll() {
        var result = ((BaseProductClassificationRepository) getRepository()).findAllByActive(true, getBriefDtoClass());
        if (result.isEmpty()) {
            throw new NoResultException();
        }
        return result;
    }

    @Transactional
    @Override
    public void removeById(int id) {
        var notSafeToDelete = ((BaseProductClassificationRepository) getRepository()).countOfAssociationsWithProduct(id) > 0L;
        if (notSafeToDelete) {
            throw new IllegalArgumentException("Can not delete this item because it is associated with an active product");
        }
        super.removeById(id);
    }

}
