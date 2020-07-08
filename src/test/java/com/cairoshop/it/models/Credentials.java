package com.cairoshop.it.models;

public class Credentials {

    private String username;

    private String password;

    public static Credentials from(String username, String password) {
        Credentials credentials = new Credentials();
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
