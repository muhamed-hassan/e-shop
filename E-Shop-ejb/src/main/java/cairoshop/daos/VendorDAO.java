package cairoshop.daos;

import cairoshop.entities.Vendor;
import javax.annotation.ManagedBean;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@ManagedBean
public class VendorDAO extends AbstractDAO<Vendor> {

    public VendorDAO() {
        super(Vendor.class);
    }

}
