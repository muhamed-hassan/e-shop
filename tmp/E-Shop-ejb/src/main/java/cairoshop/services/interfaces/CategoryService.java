package cairoshop.services.interfaces;

import com.cairoshop.dtos.SavedCategoryDTO;

import cairoshop.entities.Category;
import com.cairoshop.dtos.NewCategoryDTO;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
* LinkedIn    : https://www.linkedin.com/in/muhamed-hassan/                *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface CategoryService extends BaseService<NewCategoryDTO, SavedCategoryDTO, Category> {}
