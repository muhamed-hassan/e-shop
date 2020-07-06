package com.cairoshop.services;

import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.service.impl.BaseCommonServiceImpl;

public class BaseCommonServiceTest<SDDTO, SBDTO, T> {

    private BaseCommonServiceImpl<SDDTO, SBDTO, T> service;
    private BaseCommonRepository<SBDTO, T> repository;
    private Class<SDDTO> savedDetailedDtoClass;

    public BaseCommonServiceTest(Class<SDDTO> savedDetailedDtoClass) {
        this.savedDetailedDtoClass = savedDetailedDtoClass;
    }

    public void setRepos(BaseCommonRepository<SBDTO, T> repository) {
        this.repository = repository;
    }

    public void setServices(BaseCommonServiceImpl<SDDTO, SBDTO, T> service) {
        this.service = service;
    }

    public BaseCommonRepository<SBDTO, T> getRepository() {
        return repository;
    }

    public BaseCommonServiceImpl<SDDTO, SBDTO, T> getService() {
        return service;
    }

    /*


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
    * */
}
