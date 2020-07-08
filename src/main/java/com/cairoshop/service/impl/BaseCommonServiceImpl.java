package com.cairoshop.service.impl;

import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.service.BaseCommonService;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseCommonServiceImpl<DDTO, T>
            implements BaseCommonService<DDTO> {

    private BaseCommonRepository<T> repository;
    private Class<DDTO> savedDetailedDtoClass;

    public BaseCommonServiceImpl(Class<DDTO> savedDetailedDtoClass) {
        this.savedDetailedDtoClass = savedDetailedDtoClass;
    }

    protected void setRepos(BaseCommonRepository<T> repository) {
        this.repository = repository;
    }

    public BaseCommonRepository<T> getRepository() {
        return repository;
    }

    @Override
    public DDTO getById(int id) {
        return repository.findById(id, savedDetailedDtoClass)
                            .orElseThrow(NoResultException::new);
    }

}
