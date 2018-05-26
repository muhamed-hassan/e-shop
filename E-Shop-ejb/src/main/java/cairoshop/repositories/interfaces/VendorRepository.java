package cairoshop.repositories.interfaces;

import cairoshop.repositories.*;
import cairoshop.entities.Vendor;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public interface VendorRepository 
        extends AbstractRepository<Vendor>, PagableRepository<Vendor> {
    
}
