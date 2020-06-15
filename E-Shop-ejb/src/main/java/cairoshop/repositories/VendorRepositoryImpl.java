package cairoshop.repositories;

import javax.ejb.Stateless;

import cairoshop.entities.Vendor;
import cairoshop.repositories.interfaces.VendorRepository;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Stateless
public class VendorRepositoryImpl extends BaseRepositoryImpl<Vendor> implements VendorRepository {

    public VendorRepositoryImpl() {
        super(Vendor.class);
    }

}
