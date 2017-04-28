package cairoshop.daos;

import cairoshop.entities.*;
import java.util.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface CategoryDAO extends Common {

    boolean insert(Category category);

    boolean update(Category category);

    boolean delete(Category category);

    List<Category> getAll(Integer startPosition);

    List<Category> getAll();
}
