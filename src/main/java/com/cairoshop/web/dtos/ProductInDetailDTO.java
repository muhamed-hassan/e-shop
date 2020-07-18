package com.cairoshop.web.dtos;

import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonProperty;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class ProductInDetailDTO
            extends BaseDTO {

    @Min(value = 1, message = "price should be greater than zero")
    private double price;

    @Min(value = 1, message = "quantity should be greater than zero")
    private int quantity;

    private String description;

    @Min(value = 1, message = "category is required")
    @JsonProperty(value = "category_id")
    private int categoryId;

    @Min(value = 1, message = "vendor is required")
    @JsonProperty(value = "vendor_id")
    private int vendorId;

    @JsonProperty(value = "image_uploaded")
    private boolean imageUploaded = false;

    private static Builder builder = new Builder();
    
    public ProductInDetailDTO(double price, int quantity, String description, int categoryId, int vendorId, boolean imageUploaded, String name) {
        setName(name);
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.categoryId = categoryId;
        this.vendorId = vendorId;
        this.imageUploaded = imageUploaded;       
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public boolean isImageUploaded() {
        return imageUploaded;
    }

    public void setImageUploaded(boolean imageUploaded) {
        this.imageUploaded = imageUploaded;
    }

    public static Builder builder() {
        return builder;
    }

    public static class Builder {

        private String name;

        private double price;

        private int quantity;

        private String description;
        
        private int categoryId;

        private int vendorId;

        private boolean imageUploaded;

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

        public Builder categoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder vendorId(int vendorId) {
            this.vendorId = vendorId;
            return this;
        }

        public Builder imageUploaded(boolean imageUploaded) {
            this.imageUploaded = imageUploaded;
            return this;
        }

        public ProductInDetailDTO build() {
            return new ProductInDetailDTO(price, quantity, description, categoryId, vendorId, imageUploaded, name);
        }

    }
    
}
