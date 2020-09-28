package com.cairoshop.web.dtos;

import java.util.Objects;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class CategoryInBriefDTO
            extends ProductClassificationInBriefDTO {

    public CategoryInBriefDTO(int id, String name) {
        setId(id);
        setName(name);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof CategoryInBriefDTO))
            return false;
        CategoryInBriefDTO that = (CategoryInBriefDTO) other;
        return Objects.equals(getId(), that.getId());
    }

    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
