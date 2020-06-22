package com.cairoshop.web.dtos;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class SavedBriefCategoryDTO extends BaseSavedDTO {

    public SavedBriefCategoryDTO(int id, String name, boolean active) {
        setId(id);
        setName(name);
        setActive(active);
    }

}
