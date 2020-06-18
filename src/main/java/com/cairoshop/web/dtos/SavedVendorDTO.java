package com.cairoshop.web.dtos;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class SavedVendorDTO extends BaseSavedDTO  {

    public SavedVendorDTO(int id, String name, boolean active) {
        setId(id);
        setName(name);
        setActive(active);
    }

}
