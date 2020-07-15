package com.cairoshop.configs.security;

import static com.cairoshop.configs.Constants.ERROR_KEY;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

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
        PrintWriter writer = response.getWriter();
        writer.write(new StringBuilder("{\"").append(ERROR_KEY).append("\":\"").append(message).append("\"}").toString());
        writer.flush();
    }

}
