package com.cairoshop.service;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedBriefVendorDTO;
import com.cairoshop.web.dtos.SavedDetailedVendorDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface VendorService extends BaseProductClassificationService<NewVendorDTO, SavedDetailedVendorDTO, SavedBriefVendorDTO, Vendor> {}
