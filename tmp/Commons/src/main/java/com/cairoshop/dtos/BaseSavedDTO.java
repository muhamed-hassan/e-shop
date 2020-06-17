package com.cairoshop.dtos;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* **************************************************************************
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *
 * GitHub      : https://github.com/muhamed-hassan                          *
 * ************************************************************************ */
public class BaseSavedDTO extends BaseDTO {

    private int id;

    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (!(object instanceof BaseSavedDTO))
            return false;
        BaseSavedDTO that = (BaseSavedDTO) object;
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
