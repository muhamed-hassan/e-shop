package cairoshop.repositories.interfaces;

import cairoshop.repositories.exceptions.DeletionException;
import cairoshop.repositories.exceptions.InsertionException;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.specs.QuerySpecs;
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
    T find(QuerySpecs querySpecs) throws RetrievalException;    
    List<T> findAll(QuerySpecs querySpecs) throws RetrievalException; 
        
}
