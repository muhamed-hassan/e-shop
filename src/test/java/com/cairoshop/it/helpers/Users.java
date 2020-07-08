package com.cairoshop.it.helpers;

import com.cairoshop.it.models.Credentials;

public final class Users {

    private Users() {}

    public static final Credentials ADMIN = Credentials.from("admin", "1234");
    public static final Credentials CUSTOMER = Credentials.from("demo", "1234");

}
