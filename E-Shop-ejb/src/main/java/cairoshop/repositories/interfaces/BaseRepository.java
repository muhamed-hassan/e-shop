package cairoshop.repositories.interfaces;

import java.util.List;
import java.util.Map;

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
public interface BaseRepository<T> {
    
    Integer add(T entity) throws InsertionException;

    void update(int id, Map<String, Object> fields) throws ModificationException;

    void remove(int id) throws DeletionException;

    Object[] find(QuerySpecs querySpecs) throws RetrievalException;

    List<Object[]> findAll(QuerySpecs querySpecs) throws RetrievalException;

    List<Object[]> findAll(QuerySpecs querySpecs, int startPosition) throws RetrievalException;

    int getCount(QuerySpecs querySpecs) throws RetrievalException;
        
}
