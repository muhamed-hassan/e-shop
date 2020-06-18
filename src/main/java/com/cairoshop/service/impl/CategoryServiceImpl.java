package com.cairoshop.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.service.CategoryService;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.SavedCategoryDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<NewCategoryDTO, SavedCategoryDTO, Category> implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl() {
        super(Category.class, SavedCategoryDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(categoryRepository);
    }

    @Transactional
    @Override
    public void edit(Map<String, Object> fields) {
        int affectedRows = categoryRepository.update((int) fields.get("id"), (String) fields.get("name"));
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Override
    public List<SavedCategoryDTO> getAll() {
        List<SavedCategoryDTO> result = categoryRepository.findAllBy();
        if (result.isEmpty()) {
            throw new NoResultException();
        }
        return result;
    }

}
