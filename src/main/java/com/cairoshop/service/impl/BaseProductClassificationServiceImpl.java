package com.cairoshop.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.service.BaseProductClassificationService;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseProductClassificationServiceImpl<NDTO, SDDTO, SBDTO, T>
                    extends BaseServiceImpl<NDTO, SDDTO, SBDTO, T>
                    implements BaseProductClassificationService<NDTO, SDDTO, SBDTO, T> {

    public BaseProductClassificationServiceImpl(Class<T> entityClass, Class<SDDTO> savedDetailedDtoClass) {
        super(entityClass, savedDetailedDtoClass);
    }

    @Transactional
    @Override
    public void edit(SDDTO sdto) {
        int id = -1;
        String name = null;
        try {
            id = (int) sdto.getClass().getMethod("getId").invoke(sdto);
            name = (String) sdto.getClass().getMethod("getName").invoke(sdto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int affectedRows = ((BaseProductClassificationRepository) getRepository()).update(id, name);
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Override
    public List<SBDTO> getAll() {
        List<SBDTO> result = ((BaseProductClassificationRepository) getRepository()).findAllBy();
        if (result.isEmpty()) {
            throw new NoResultException();
        }
        return result;
    }

}
