package com.cairoshop.persistence.repositories;

import java.util.List;

import com.cairoshop.web.dtos.SavedBriefProductDTO;

public interface ProductRepositoryCustom {

    List<SavedBriefProductDTO> search(String name, int startPosition, int pageSize, String sortBy, String sortDirection);

    int countAllByCriteria(String name);

}
