package com.cairoshop.persistence.repositories;

import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.web.dtos.SavedBriefVendorDTO;
import com.cairoshop.web.dtos.SavedDetailedVendorDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Repository //SDDTO, SBDTO, T
public interface VendorRepository extends BaseProductClassificationRepository<SavedBriefVendorDTO, Vendor> {}
