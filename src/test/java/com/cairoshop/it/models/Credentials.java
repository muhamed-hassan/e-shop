package com.cairoshop.it.models;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class Credentials {

    private String username;

    private String password;

    public static Credentials from(String username, String password) {
        var credentials = new Credentials();
        credentials.username = username;
        credentials.password = password;
        return credentials;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
