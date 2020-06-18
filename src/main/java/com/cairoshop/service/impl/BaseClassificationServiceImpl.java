package com.cairoshop.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.repositories.BaseClassificationRepository;
import com.cairoshop.service.BaseClassificationService;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseClassificationServiceImpl<NDTO, SDTO, T>
                    extends BaseServiceImpl<NDTO, SDTO, T>
                    implements BaseClassificationService<NDTO, SDTO, T> {

    public BaseClassificationServiceImpl(Class<T> entityClass, Class<SDTO> savedDtoClass) {
        super(entityClass, savedDtoClass);
    }

    @Transactional
    @Override
    public void edit(SDTO sdto) {
        int id = -1;
        String name = null;
        try {
            id = (int) sdto.getClass().getMethod("getId").invoke(sdto);
            name = (String) sdto.getClass().getMethod("getName").invoke(sdto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int affectedRows = ((BaseClassificationRepository) getRepository()).update(id, name);
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Override
    public List<SDTO> getAll() {
        List<SDTO> result = ((BaseClassificationRepository) getRepository()).findAllBy();
        if (result.isEmpty()) {
            throw new NoResultException();
        }
        return result;
    }

}
