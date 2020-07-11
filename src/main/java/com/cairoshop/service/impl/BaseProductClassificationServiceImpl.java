package com.cairoshop.service.impl;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.repositories.BaseProductClassificationRepository;
import com.cairoshop.service.BaseProductClassificationService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.DataNotUpdatedException;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseProductClassificationServiceImpl<T, DDTO, BDTO>
                    extends BaseServiceImpl<T, DDTO, BDTO>
                    implements BaseProductClassificationService<DDTO, BDTO> {

    public BaseProductClassificationServiceImpl(Class<T> entityClass, Class<DDTO> ddtoClass) {
        super(entityClass, ddtoClass);
    }

    @Transactional
    @Override
    public void edit(int id, DDTO ddto) {
        int affectedRows;
        try {
            //String name = (String) ddto.getClass().getMethod("getName").invoke(ddto);
            affectedRows = ((BaseProductClassificationRepository) getRepository()).update(id, ddto);
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
        List<BDTO> result = ((BaseProductClassificationRepository) getRepository()).findAll();
        if (result.isEmpty()) {
            throw new NoResultException();
        }
        return result;
    }

}
