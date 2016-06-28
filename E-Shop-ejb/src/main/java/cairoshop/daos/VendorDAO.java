package cairoshop.daos;

import cairoshop.entities.*;
import java.util.*;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface VendorDAO extends Common
{

    boolean insert(Vendor vendor);

    boolean update(Vendor vendor);

    boolean delete(Vendor vendor);

    List<Vendor> getAll(Integer startPosition);

    List<Vendor> getAll();
}
