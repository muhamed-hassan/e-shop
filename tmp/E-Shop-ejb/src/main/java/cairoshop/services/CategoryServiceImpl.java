package cairoshop.services;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.cairoshop.dtos.SavedCategoryDTO;

import cairoshop.entities.Category;
import cairoshop.repositories.interfaces.CategoryRepository;
import cairoshop.services.interfaces.CategoryService;
import com.cairoshop.dtos.NewCategoryDTO;

@Stateless
public class CategoryServiceImpl extends BaseServiceImpl<NewCategoryDTO, SavedCategoryDTO, Category> implements CategoryService {

    @EJB
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl() {
        super(NewCategoryDTO.class, SavedCategoryDTO.class, Category.class);
    }

    @PostConstruct
    public void injectReposRefs() {
        setRepo(categoryRepository);
    }

}
