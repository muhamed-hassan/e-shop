package com.cairoshop.dtos;

import java.util.List;

/* **************************************************************************
 * Developed by: Muhamed Hassan	                                            *
* LinkedIn    : https://www.linkedin.com/in/muhamed-hassan/                *
 * GitHub      : https://github.com/muhamed-hassan                          *
 * ************************************************************************ */
public class SavedItemsDTO<T> {

    private List<T> items;

    private int allSavedItemsCount;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getAllSavedItemsCount() {
        return allSavedItemsCount;
    }

    public void setAllSavedItemsCount(int allSavedItemsCount) {
        this.allSavedItemsCount = allSavedItemsCount;
    }

}
