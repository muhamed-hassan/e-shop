package com.cairoshop.service.impl;

import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.service.BaseCommonService;
import com.cairoshop.service.exceptions.NoResultException;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseCommonServiceImpl<SDDTO, SBDTO, T> implements BaseCommonService<SDDTO, SBDTO, T> {

    private BaseCommonRepository<SBDTO, T> repository;
    private Class<SDDTO> savedDetailedDtoClass;

    public BaseCommonServiceImpl(Class<SDDTO> savedDetailedDtoClass) {
        this.savedDetailedDtoClass = savedDetailedDtoClass;
    }

    protected void setRepos(BaseCommonRepository<SBDTO, T> repository) {
        this.repository = repository;
    }

    public BaseCommonRepository<SBDTO, T> getRepository() {
        return repository;
    }

    @Override
    public SDDTO getById(int id) {
        return repository.findById(id, savedDetailedDtoClass)
                            .orElseThrow(NoResultException::new);
    }

}
