package com.cairoshop.web.dtos;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class UserInBriefDTO
            extends BaseDTO {
    
    private int id;
    
    private boolean active;

    public UserInBriefDTO(int id, String name, boolean active) {
        setName(name);
        this.id = id;
        this.active = active;
    }

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
        if (!(object instanceof UserInBriefDTO))
            return false;
        UserInBriefDTO that = (UserInBriefDTO) object;
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
