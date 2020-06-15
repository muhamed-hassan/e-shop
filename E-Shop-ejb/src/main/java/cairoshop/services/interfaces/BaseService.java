package cairoshop.services.interfaces;

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
public interface BaseService<DTO> {
    
    Integer add(DTO dto) throws InsertionException;

    void update(DTO dto) throws ModificationException;

    void remove(int id) throws DeletionException;

    Object[] find(QuerySpecs querySpecs) throws RetrievalException;

    List<Object[]> findAll(QuerySpecs querySpecs) throws RetrievalException;

    List<Object[]> findAll(QuerySpecs querySpecs, int startPosition) throws RetrievalException;

    int getCount(QuerySpecs querySpecs) throws RetrievalException;
        
}
