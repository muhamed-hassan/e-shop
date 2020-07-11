package com.cairoshop.persistence.repositories;

import org.springframework.stereotype.Repository;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.web.dtos.VendorInBriefDTO;
import com.cairoshop.web.dtos.VendorInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
//@Repository
public interface VendorRepository
            extends BaseProductClassificationRepository<Vendor, VendorInDetailDTO, VendorInBriefDTO> {}
