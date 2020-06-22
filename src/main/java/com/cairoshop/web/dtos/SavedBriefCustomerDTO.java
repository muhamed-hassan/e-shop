package com.cairoshop.web.dtos;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class SavedBriefCustomerDTO extends BaseSavedDTO {

    public SavedBriefCustomerDTO(int id, String name, boolean active) {
        setId(id);
        setName(name);
        setActive(active);
    }

}
