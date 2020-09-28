package com.cairoshop.web.dtos;

import java.util.Objects;

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

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof UserInBriefDTO))
            return false;
        UserInBriefDTO that = (UserInBriefDTO) other;
        return Objects.equals(getId(), that.getId());
    }

    public int hashCode() {
        return Objects.hashCode(getId());
    }
    
}
