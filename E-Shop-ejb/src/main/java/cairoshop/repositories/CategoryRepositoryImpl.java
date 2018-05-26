package cairoshop.repositories;

import cairoshop.entities.Category;
import cairoshop.repositories.interfaces.CategoryRepository;
import javax.ejb.Stateless;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class CategoryRepositoryImpl 
        extends BaseRepository<Category> 
        implements CategoryRepository {
    
    public CategoryRepositoryImpl() {
        super(Category.class );
    }
    
}
