package cairoshop.repositories;

import cairoshop.repositories.exceptions.*;
import cairoshop.repositories.specs.CriteriaQuerySpecs;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface AbstractRepository<T> {
    
    void add(T entity) throws InsertionException;    
    void update(T entity) throws ModificationException;    
    void remove(int id) throws DeletionException;    
    T find(CriteriaQuerySpecs querySpecs) throws Exception;    
    List<T> findAll(CriteriaQuerySpecs querySpecs) throws Exception;
        
}
