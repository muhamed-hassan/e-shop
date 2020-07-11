package com.cairoshop.persistence.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Where;

import com.cairoshop.web.dtos.CategoryInBriefDTO;
import com.cairoshop.web.dtos.VendorInBriefDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Entity
@Loader(namedQuery = "findVendorById")
@NamedQuery(name = "findVendorById",
            query = "SELECT v " +
                    "FROM Vendor v " +
                    "WHERE v.id = ?1 ")
@Where(clause = "active = true")
@SqlResultSetMapping(name = "VendorInBriefDTO",
    classes = { @ConstructorResult(targetClass = VendorInBriefDTO.class,
        columns = { @ColumnResult(name = "name", type = String.class) })
    })
public class Vendor extends ProductClassification {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Vendor that = (Vendor) object;
        return new EqualsBuilder()
                    .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                    .append(id)
                .toHashCode();
    }

}
