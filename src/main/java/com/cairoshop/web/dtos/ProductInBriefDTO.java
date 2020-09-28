package com.cairoshop.web.dtos;

import java.util.Objects;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class ProductInBriefDTO
            extends BaseDTO {

    private int id;

    public ProductInBriefDTO(int id, String name) {
        this.id = id;
        setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof ProductInBriefDTO))
            return false;
        ProductInBriefDTO that = (ProductInBriefDTO) other;
        return Objects.equals(getId(), that.getId());
    }

    public int hashCode() {
        return Objects.hashCode(getId());
    }

}
