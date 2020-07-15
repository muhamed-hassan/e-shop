package com.cairoshop.web.dtos;

import java.util.List;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class SavedItemsDTO<T> {

    private final List<T> items;

    private final int allSavedItemsCount;

    public SavedItemsDTO(List<T> items, int allSavedItemsCount) {
        this.items = items;
        this.allSavedItemsCount = allSavedItemsCount;
    }

    public List<T> getItems() {
        return items;
    }

    public int getAllSavedItemsCount() {
        return allSavedItemsCount;
    }

}
