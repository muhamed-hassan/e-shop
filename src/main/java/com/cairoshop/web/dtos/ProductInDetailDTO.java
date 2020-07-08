package com.cairoshop.web.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class ProductInDetailDTO extends BaseDTO {

    private double price;

    private int quantity;

    private String description;

    @JsonProperty(value = "category_id")
    private int categoryId;

    @JsonProperty(value = "vendor_id")
    private int vendorId;

    private boolean imageUploaded = false;

    public ProductInDetailDTO(String name, double price, int quantity, String description, int categoryId, int vendorId, boolean imageUploaded) {
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
    
}
