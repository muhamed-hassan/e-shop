package com.cairoshop.service.impl;

import org.springframework.dao.EmptyResultDataAccessException;

import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.service.BaseCommonService;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseCommonServiceImpl<DDTO>
            implements BaseCommonService<DDTO> {

    private BaseCommonRepository<DDTO> repository;

    protected void setRepos(BaseCommonRepository<DDTO> repository) {
        this.repository = repository;
    }

    public BaseCommonRepository<DDTO> getRepository() {
        return repository;
    }

    @Override
    public DDTO getById(int id) {
        try {
            return repository.findById(id);
        } catch (EmptyResultDataAccessException erde) {
            throw new NoResultException();
        }
    }

}
