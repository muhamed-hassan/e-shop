package com.cairoshop.persistence.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Entity
public class Product
            extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    private String name;

    private double price;

    private int quantity;

    private String description;

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

    private static Builder builder = new Builder();

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

    public void setImageUploaded(boolean imageUploaded) {
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

    public static Builder builder() {
        return builder;
    }

    public static class Builder {

        private int id;

        private String name;

        private double price;

        private int quantity;

        private String description;

        private Category category;

        private Vendor vendor;

        private boolean imageUploaded;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder category(Category category) {
            this.category = category;
            return this;
        }

        public Builder vendor(Vendor vendor) {
            this.vendor = vendor;
            return this;
        }

        public Builder imageUploaded(boolean imageUploaded) {
            this.imageUploaded = imageUploaded;
            return this;
        }

        public Product build() {
            var product = new Product();
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setQuantity(quantity);
            product.setDescription(description);
            product.setCategory(category);
            product.setVendor(vendor);
            product.setActive(true);
            product.setImageUploaded(imageUploaded);
            return product;
        }

    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        var that = (Product) other;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    
}
