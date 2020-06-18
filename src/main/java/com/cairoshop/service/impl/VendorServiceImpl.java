package com.cairoshop.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.VendorRepository;
import com.cairoshop.service.VendorService;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedVendorDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Service
public class VendorServiceImpl extends BaseServiceImpl<NewVendorDTO, SavedVendorDTO, Vendor> implements VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public VendorServiceImpl() {
        super(Vendor.class, SavedVendorDTO.class);
    }

    @PostConstruct
    public void injectRefs() {
        setRepos(vendorRepository);
    }

    @Transactional
    @Override
    public void edit(Map<String, Object> fields) {
        int affectedRows = vendorRepository.update((int) fields.get("id"), (String) fields.get("name"));
        if (affectedRows == 0) {
            throw new DataNotUpdatedException();
        }
    }

    @Override
    public List<SavedVendorDTO> getAll() {
        List<SavedVendorDTO> result = vendorRepository.findAllBy();
        if (result.isEmpty()) {
            throw new NoResultException();
        }
        return result;
    }

}
