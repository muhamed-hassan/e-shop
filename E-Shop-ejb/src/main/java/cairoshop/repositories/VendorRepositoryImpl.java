package cairoshop.repositories;

import cairoshop.entities.Vendor;
import cairoshop.repositories.interfaces.VendorRepository;
import javax.ejb.Stateless;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://eg.linkedin.com/in/muhamedhassanqotb               *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class VendorRepositoryImpl extends BaseRepository<Vendor> implements VendorRepository {

    public VendorRepositoryImpl() {
        super(Vendor.class);
    }

}
