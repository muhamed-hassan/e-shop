package com.cairoshop.persistence.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.NamedQuery;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Entity
@Loader(namedQuery = "findVendorById")
@NamedQuery(name = "findVendorById",
            query = "SELECT v " +
                    "FROM Vendor v " +
                    "WHERE v.id = ?1 ")
public class Vendor
            extends ProductClassification {

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
