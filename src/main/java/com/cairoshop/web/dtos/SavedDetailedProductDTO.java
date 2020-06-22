package com.cairoshop.web.dtos;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class SavedDetailedProductDTO extends BaseProductDTO {

    private int id;

    private boolean active;

    public SavedDetailedProductDTO(int id, String name, double price, int quantity, String description,
                                    int categoryId, int vendorId, boolean active, boolean imageUploaded) {
        this.id = id;
        this.active = active;
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        setDescription(description);
        setCategoryId(categoryId);
        setVendorId(vendorId);
        setImageUploaded(imageUploaded);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
