package com.cairoshop.web.dtos;

/* **************************************************************************
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *
 * GitHub      : https://github.com/muhamed-hassan                          *
 * ************************************************************************ */
public class SavedCategoryDTO extends BaseSavedDTO {

    public SavedCategoryDTO(int id, String name, boolean active) {
        setId(id);
        setName(name);
        setActive(active);
    }

}
