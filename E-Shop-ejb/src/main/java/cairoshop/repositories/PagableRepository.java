package cairoshop.repositories;

import cairoshop.repositories.specs.CriteriaQuerySpecs;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface PagableRepository<T> {
    
    int getCount(CriteriaQuerySpecs querySpecs) throws Exception;
    
    List<T> findAll(CriteriaQuerySpecs querySpecs, int startPosition) throws Exception; 
    
}
