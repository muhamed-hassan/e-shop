package com.cairoshop.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;

import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.service.BaseCommonService;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseCommonServiceImpl<D>
            implements BaseCommonService<D> {

    private BaseCommonRepository<D> repository;

    protected void setRepos(BaseCommonRepository<D> repository) {
        this.repository = repository;
    }

    public BaseCommonRepository<D> getRepository() {
        return repository;
    }

    @Override
    public D getById(int id) {
        try {
            return repository.findById(id);
        } catch (EmptyResultDataAccessException erde) {
            throw new NoResultException();
        }
    }

}
