package cairoshop.services.interfaces;

import java.util.List;

import cairoshop.repositories.exceptions.DeletionException;
import cairoshop.repositories.exceptions.InsertionException;
import cairoshop.repositories.exceptions.ModificationException;
import cairoshop.repositories.exceptions.RetrievalException;
import cairoshop.repositories.specs.QuerySpecs;
import java.util.Map;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface BaseService<NDTO, SDTO, T> {
    
    Integer add(NDTO dto);

    void edit(Map<String, Object> fields);

    void remove(int id);

    SDTO getById(int id);

    List<SDTO> findAll(QuerySpecs querySpecs);

    List<SDTO> findAll(QuerySpecs querySpecs, int startPosition);

    int getCount(QuerySpecs querySpecs);
        
}
