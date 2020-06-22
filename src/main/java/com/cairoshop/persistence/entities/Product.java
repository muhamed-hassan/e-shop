package com.cairoshop.persistence.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Where;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Entity
@Loader(namedQuery = "findProductById")
@NamedQuery(name = "findProductById",
            query = "SELECT p " +
                    "FROM Product p " +
                    "WHERE p.id = ?1 ")
@Where(clause = "active = true")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    private String name;

    private double price;

    private int quantity;

    private String description; // max 1000 char - TODO update creation script

    @Lob
    @Column(length = 200000)
    private byte[] image;

    @Column(name = "image_uploaded")
    private boolean imageUploaded;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "vendor")
    private Vendor vendor;

    public static final List<String> SORTABLE_FIELDS = List.of("name", "price", "quantity");

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isImageUploaded() {
        return imageUploaded;
    }

    public void setImageUploaded(final boolean imageUploaded) {
        this.imageUploaded = imageUploaded;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        Product that = (Product) object;
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
