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
public class BaseProductClassificationServiceImpl<T, DDTO, BDTO>
            extends BaseServiceImpl<T, DDTO, BDTO>
            implements BaseProductClassificationService<DDTO, BDTO> {

    protected BaseProductClassificationServiceImpl(Class<T> entityClass, Class<DDTO> ddtoClass) {
        super(entityClass, ddtoClass);
    }

    @Transactional
    @Override
    public void edit(int id, DDTO ddto) {
        int affectedRows;
        try {
            affectedRows = ((BaseProductClassificationRepository) getRepository()).update(id, ddto);
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                throw new DataIntegrityViolatedException();
            }
            throw new RuntimeException(e);
        }
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Override
    public List<BDTO> getAll() {
        List<BDTO> result = ((BaseProductClassificationRepository) getRepository()).findAll();
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
