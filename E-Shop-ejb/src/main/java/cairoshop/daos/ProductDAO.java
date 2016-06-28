package cairoshop.daos;

import cairoshop.entities.*;
import cairoshop.helpers.*;
import java.util.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface ProductDAO extends Common
{

    boolean insert(Product product);

    boolean update(Product product);

    boolean update(byte[] imgStream, Integer pID);

    boolean update(Integer pID, Integer cID);

    Product get(Integer id);
    
    byte[] getImage(Integer pID);

    Integer getCount(Object object);

    Integer getFavoriteCount(Integer custId);
    
    List<Integer> getLikedProducts(Integer custId);
    
    boolean delete(Integer pID);
    
    List<Object[]> getAll(String pName);

    List<Object[]> getAll(Integer startPosition);

    List<Object[]> getAll(Object object, Integer startPosition);

    List<Object[]> getAll(SortCriteria sortCriteria, SortDirection sortDirection, Integer startPosition);
    
}
