package com.cairoshop.service;

import java.util.List;

import com.cairoshop.web.dtos.SavedItemsDTO;

public interface BaseCommonService<SDDTO, SBDTO, T> {

//    void edit(SDDTO sddto);

    SDDTO getById(int id);

    SavedItemsDTO<SBDTO> getAll(int startPosition, String sortBy, String sortDirection);

}
