package com.cairoshop.web.dtos;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof CategoryInBriefDTO))
            return false;
        CategoryInBriefDTO that = (CategoryInBriefDTO) object;
        return new EqualsBuilder()
            .append(getId(), that.getId())
            .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getId())
            .toHashCode();
    }

}
