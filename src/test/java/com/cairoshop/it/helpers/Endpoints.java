package com.cairoshop.it.helpers;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public final class Endpoints {

    private Endpoints() {}

    public static final String ADD_NEW_VENDOR = "/vendors";
    public static final String EDIT_VENDOR = "/vendors";
    public static final String DELETE_VENDOR_BY_ID = "/vendors/{0}";
    public static final String GET_VENDOR_BY_ID = "/vendors/{0}";
    public static final String GET_VENDORS_BY_PAGINATION = "/vendors?start-position={0}&sort-by=id&sort-direction=ASC";
    public static final String GET_ALL_VENDORS = "/vendors";

    public static final String ADD_NEW_CATEGORY = "/categories";
    public static final String EDIT_CATEGORY = "/categories";
    public static final String DELETE_CATEGORY_BY_ID = "/categories/{0}";
    public static final String GET_CATEGORY_BY_ID = "/categories/{0}";
    public static final String GET_CATEGORIES_BY_PAGINATION = "/categories?start-position={0}&sort-by=id&sort-direction=ASC";
    public static final String GET_ALL_CATEGORIES = "/categories";

    public static final String EDIT_USER = "/users";
    public static final String GET_USER_BY_ID = "/users/{0}";
    public static final String GET_USERS_BY_PAGINATION = "/users?start-position={0}&sort-by=id&sort-direction=ASC";

    public static final String ADD_NEW_PRODUCT = "/products";
    public static final String EDIT_PRODUCT = "/products";
    public static final String DELETE_PRODUCT_BY_ID = "/products/{0}";
    public static final String GET_PRODUCT_BY_ID = "/products/{0}";
    public static final String GET_PRODUCTS_BY_PAGINATION = "/products?start-position={0}&sort-by=id&sort-direction=ASC";
    public static final String SEARCH_PRODUCTS_BY_KEYWORD = "/products?name={0}&start-position={1}&sort-by={2}&sort-direction={3}";
    public static final String UPLOAD_IMAGE_OF_PRODUCT = "/products/{0}";
    public static final String DOWNLOAD_IMAGE_OF_PRODUCT = "/products/{0}";
    public static final String GET_SORTABLE_FIELDS_OF_PRODUCT = "/products/sortable-fields";

}
