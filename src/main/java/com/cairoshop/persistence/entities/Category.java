package com.cairoshop.persistence.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Where;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Entity
@Loader(namedQuery = "findCategoryById")
@NamedQuery(name = "findCategoryById", query =
    "SELECT c " +
        "FROM Category c " +
        "WHERE " +
        "    c.id = ?1 ")
@Where(clause = "active = true")
public class Category {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    private String name;

    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
                        .append(active, that.active)
                        .append(name, that.name)
                    .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                        .append(id)
                        .append(name)
                        .append(active)
                    .toHashCode();
    }

}
