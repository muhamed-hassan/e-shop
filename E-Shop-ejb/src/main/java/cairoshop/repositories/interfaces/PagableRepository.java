package cairoshop.repositories.interfaces;

import java.util.List;

import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.specs.QuerySpecs;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface PagableRepository<T> {
    
    int getCount(QuerySpecs querySpecs) throws RetrievalException;    
    List<T> findAll(QuerySpecs querySpecs, int startPosition) throws RetrievalException; 
    
}
