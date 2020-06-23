package com.cairoshop.web.dtos;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class SavedDetailedCustomerDTO extends SavedBriefCustomerDTO {

    private String username;

    private String email;

    private String phone;

    private String address;

    public SavedDetailedCustomerDTO(int id, String name, boolean active, String username, String email, String phone, String address) {
        super(id, name, active);
        setUsername(username);
        setEmail(email);
        setPhone(phone);
        setAddress(address);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
