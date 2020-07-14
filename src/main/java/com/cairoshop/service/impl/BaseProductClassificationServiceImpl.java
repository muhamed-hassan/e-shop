package com.cairoshop.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.persistence.repositories.BaseRepository;
import com.cairoshop.service.BaseProductClassificationService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.DataNotDeletedException;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseProductClassificationServiceImpl<T, D, B>
            extends BaseServiceImpl<T, D, B>
            implements BaseProductClassificationService<D, B> {

    protected BaseProductClassificationServiceImpl(Class<T> entityClass) {
        super(entityClass);
    }

    @Transactional
    @Override
    public void edit(int id, D detailedDto) {
        int affectedRows;
        try {
            affectedRows = ((BaseProductClassificationRepository) getRepository()).update(id, detailedDto);
        } catch (DataIntegrityViolationException dive) {
            throw new DataIntegrityViolatedException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Override
    public List<B> getAll() {
        List<B> result = ((BaseProductClassificationRepository) getRepository()).findAll();
        if (result.isEmpty()) {
            throw new NoResultException();
        }
        return result;
    }

    @Transactional
    @Override
    public void removeById(int id) {
        boolean safeToDelete = ((BaseProductClassificationRepository) getRepository()).safeToDelete(id);
        if (!safeToDelete) {
            throw new IllegalArgumentException("Can not delete this item because it is associated with an active product");
        }
        int affectedRows = ((BaseRepository) getRepository()).deleteById(id);
        if (affectedRows == 0) {
            throw new DataNotDeletedException();
        }
    }

}
