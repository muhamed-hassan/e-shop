package com.cairoshop.web.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseDTO {

    @NotBlank(message = "name is required")
    @Size(max = 150, message = "name length exceeded the allowed length of 150 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
