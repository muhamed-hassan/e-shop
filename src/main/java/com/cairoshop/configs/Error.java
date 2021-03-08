package com.cairoshop.configs;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public final class Error {

    private final String message;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String toJson() {
        return new StringBuilder("{\"message").append("\":\"").append(message).append("\"}").toString();
    }

}
