package com.cairoshop.web.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseProductDTO extends BaseDTO {

    @Min(value = 1, message = "min allowed price is 1")
    @Max(value = 1_000_000, message = "max allowed price is 1,000,000")
    private double price;

    @Min(value = 1, message = "min allowed quantity is 1")
    @Max(value = 2_147_483_647, message = "max allowed quantity is 2,147,483,647")
    private int quantity;

    private String description;

    @Min(value = 1, message = "min allowed categoryId is 1")
    @Max(value = 2_147_483_647, message = "max allowed categoryId is 2,147,483,647")
    private int categoryId;

    @Min(value = 1, message = "min allowed vendorId is 1")
    @Max(value = 2_147_483_647, message = "max allowed vendorId is 2,147,483,647")
    private int vendorId;

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

}
