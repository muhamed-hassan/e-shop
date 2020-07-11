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
import com.cairoshop.web.dtos.ProductInBriefDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Entity
@Loader(namedQuery = "findCategoryById")
@NamedQuery(name = "findCategoryById",
            query = "SELECT c " +
                    "FROM Category c " +
                    "WHERE c.id = ?1 ")
@Where(clause = "active = true")
@SqlResultSetMapping(name = "CategoryInBriefDTO",
    classes = { @ConstructorResult(targetClass = CategoryInBriefDTO.class,
        columns = { @ColumnResult(name = "name", type = String.class) })
    })
public class Category extends ProductClassification {

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
        Category that = (Category) object;
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
