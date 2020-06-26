package com.cairoshop.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.cairoshop.configs.Constants;
import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.service.BaseCommonService;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.SavedItemsDTO;

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

    @Override
    public SavedItemsDTO<SBDTO> getAll(int startPosition, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        sortDirection = sortDirection.toLowerCase();
        switch (sortDirection) {
            case "desc":
                sort = sort.descending();
                break;
            case "asc":
                sort = sort.ascending();
                break;
        }
        List<SBDTO> page = repository.findAllBy(PageRequest.of(startPosition, Constants.MAX_PAGE_SIZE, sort));
        long allCount = repository.count();
        SavedItemsDTO<SBDTO> sbdtoSavedItemsDTO = new SavedItemsDTO<>();
        sbdtoSavedItemsDTO.setItems(page);
        sbdtoSavedItemsDTO.setAllSavedItemsCount(Long.valueOf(allCount).intValue());
        return sbdtoSavedItemsDTO;
    }

}
