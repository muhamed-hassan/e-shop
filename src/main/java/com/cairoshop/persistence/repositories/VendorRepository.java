package com.cairoshop.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.web.dtos.VendorInBriefDTO;
import com.cairoshop.web.dtos.VendorInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public interface VendorRepository
            extends BaseProductClassificationRepository<Vendor, VendorInDetailDTO, VendorInBriefDTO> {

    @Query("SELECT COUNT(p.id) "
        + "FROM Product p "
        + "WHERE p.active = true AND p.vendor.id = ?1")
    long countOfAssociationsWithProduct(int vendorId);

    @Query("SELECT new com.cairoshop.web.dtos.VendorInDetailDTO(v.name) "
        + "FROM Vendor v "
        + "WHERE v.id = ?1 AND v.active = ?2")
    Optional<VendorInDetailDTO> findByIdAndActive(int id, boolean active);

}
