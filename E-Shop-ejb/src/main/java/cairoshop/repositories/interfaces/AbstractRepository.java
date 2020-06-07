package cairoshop.repositories.interfaces;

import java.util.List;

import cairoshop.repositories.exceptions.DeletionException;
import cairoshop.repositories.exceptions.InsertionException;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.specs.QuerySpecs;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface AbstractRepository<T> {
    
    void add(T entity) throws InsertionException;    
    void update(T entity) throws ModificationException;    
    void remove(int id) throws DeletionException;    
    T find(QuerySpecs querySpecs) throws RetrievalException;    
    List<T> findAll(QuerySpecs querySpecs) throws RetrievalException; 
        
}
