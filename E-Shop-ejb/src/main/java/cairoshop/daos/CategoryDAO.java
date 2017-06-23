package cairoshop.daos;

import cairoshop.entities.Category;
import javax.annotation.ManagedBean;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
public class CategoryDAO extends AbstractDAO<Category> {

    public CategoryDAO() {
        super(Category.class);
    }
}
