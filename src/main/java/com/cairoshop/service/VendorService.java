package com.cairoshop.service;

import java.util.List;
import java.util.Map;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedVendorDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public interface VendorService extends BaseService<NewVendorDTO, SavedVendorDTO, Vendor> {

    void edit(Map<String, Object> fields);

    List<SavedVendorDTO> getAll();

}
