package com.cairoshop.configs.security;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.cairoshop.configs.Error;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@Component
public class Utils {

    public void generateResponseFrom(HttpServletResponse response, int httpStatus, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus);
        var writer = response.getWriter();
        writer.write(new Error(message).toJson());
        writer.flush();
    }

}
