package com.cairoshop.persistence.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

/* ************************************************************************** 
 * Developed by: Muhamed Hassan	                                            *
 * LinkedIn    : https://www.linkedin.com/in/mohamed-qotb/                  *  
 * GitHub      : https://github.com/muhamed-hassan                          *  
 * ************************************************************************ */
@Entity
@DiscriminatorValue(value = "1")
public class Customer extends User {

    @Embedded
    private ContactDetails contactDetails;

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public static class Builder {

        private int id;

        private String name;

        private String username;

        private String email;

        private String password;

        private boolean active;
        
        private ContactDetails contactDetails;

        public Builder(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Builder withContactDetails(String address, String phone) {
            this.contactDetails = new ContactDetails();
            contactDetails.setAddress(address);
            contactDetails.setPhone(phone);
            return this;
        }

        public Customer build() {
            Customer customer = new Customer();
            customer.setActive(active);
            customer.setName(name);
            customer.setEmail(email);
            customer.setUsername(username);
            customer.setPassword(password);
            customer.setContactDetails(contactDetails);
            return customer;
        }

    }

}
