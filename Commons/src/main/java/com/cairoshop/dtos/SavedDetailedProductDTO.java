package com.cairoshop.dtos;

/* **************************************************************************
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *
 * GitHub      : https://github.com/muhamed-hassan                          *
 * ************************************************************************ */
public class SavedDetailedProductDTO extends BaseProductDTO {

    private int id;

    private boolean active;

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
