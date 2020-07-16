package com.cairoshop.web.dtos;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class VendorInBriefDTO
            extends ProductClassificationInBriefDTO {

    public VendorInBriefDTO(int id, String name) {
        setId(id);
        setName(name);
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof VendorInBriefDTO))
            return false;
        VendorInBriefDTO that = (VendorInBriefDTO) other;
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
