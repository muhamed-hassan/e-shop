package com.cairoshop.dtos;

import java.util.LinkedList;
import java.util.List;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
public class SavedCustomersDTO {
    
    private List<SavedCustomerDTO> customers = new LinkedList<>();
    
    private int allCount;

    public List<SavedCustomerDTO> getCustomers() {
        return customers;
    }

    public void setCustomers(List<SavedCustomerDTO> customers) {
        this.customers = customers;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }
    
}
