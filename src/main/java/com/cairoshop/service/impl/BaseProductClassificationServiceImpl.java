package com.cairoshop.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.service.BaseProductClassificationService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseProductClassificationServiceImpl<DDTO, BDTO, T>
                    extends BaseServiceImpl<DDTO, BDTO, T>
                    implements BaseProductClassificationService<DDTO, BDTO> {

    public BaseProductClassificationServiceImpl(Class<T> entityClass, Class<DDTO> BDTOClass) {
        super(entityClass, BDTOClass);
    }

    @Transactional
    @Override
    public void edit(int id, DDTO ddto) {
        int affectedRows;
        try {
            String name = (String) ddto.getClass().getMethod("getName").invoke(ddto);
            affectedRows = ((BaseProductClassificationRepository) getRepository()).update(id, name);
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
    public List<BDTO> getAll() {
        List<BDTO> result = ((BaseProductClassificationRepository) getRepository()).findAllBy();
        if (result.isEmpty()) {
            throw new NoResultException();
        }
        return result;
    }

}
