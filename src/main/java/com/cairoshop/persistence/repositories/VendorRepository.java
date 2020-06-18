package com.cairoshop.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.web.dtos.SavedVendorDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository
public interface VendorRepository extends BaseRepository<SavedVendorDTO, Vendor, Integer> {

    @Query("UPDATE Vendor v SET v.name = ?2 WHERE v.id = ?1")
    @Modifying
    int update(int id, String name);

    List<SavedVendorDTO> findAllBy();

}
