package cairoshop.repositories;

import javax.ejb.Stateless;

import cairoshop.entities.Category;
import cairoshop.repositories.interfaces.CategoryRepository;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class CategoryRepositoryImpl extends BaseRepositoryImpl<Category> implements CategoryRepository {
    
    public CategoryRepositoryImpl() {
        super(Category.class );
    }
    
}
