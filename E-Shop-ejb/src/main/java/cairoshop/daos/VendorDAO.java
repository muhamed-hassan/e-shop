package cairoshop.daos;

import cairoshop.entities.Vendor;
import javax.annotation.ManagedBean;

/* ************************************************************************** 
 * Developed by: Mohamed Hassan	                                            *
 * Email       : mohamed.hassan.work@gmail.com			            *     
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
public class VendorDAO extends AbstractDAO<Vendor> {

    public VendorDAO() {
        super(Vendor.class);
    }

}
