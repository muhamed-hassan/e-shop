package cairoshop.repositories.interfaces;

import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.specs.QuerySpecs;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface PagableRepository<T> {
    
    int getCount(QuerySpecs querySpecs) throws RetrievalException;    
    List<T> findAll(QuerySpecs querySpecs, int startPosition) throws RetrievalException; 
    
}
