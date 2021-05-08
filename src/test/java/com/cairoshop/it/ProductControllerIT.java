package com.cairoshop.it;

import static com.cairoshop.it.helpers.Endpoints.ADD_NEW_PRODUCT;
import static com.cairoshop.it.helpers.Endpoints.DELETE_PRODUCT_BY_ID;
import static com.cairoshop.it.helpers.Endpoints.EDIT_PRODUCT;
import static com.cairoshop.it.helpers.Endpoints.GET_PRODUCTS_BY_PAGINATION;
import static com.cairoshop.it.helpers.Endpoints.GET_PRODUCT_BY_ID;
import static com.cairoshop.it.helpers.Endpoints.GET_SORTABLE_FIELDS_OF_PRODUCT;
import static com.cairoshop.it.helpers.Endpoints.SEARCH_PRODUCTS_BY_KEYWORD;
import static com.cairoshop.it.helpers.Errors.ACCESS_DENIED_JSON;
import static com.cairoshop.it.helpers.Errors.DB_VIOLATED_CONSTRAINTS_JSON;
import static com.cairoshop.it.helpers.Errors.INVALID_PRICE_JSON;
import static com.cairoshop.it.helpers.Errors.INVALID_QUANTITY_JSON;
import static com.cairoshop.it.helpers.Errors.NO_DATA_FOUND_JSON;
import static com.cairoshop.it.helpers.Users.ADMIN;
import static com.cairoshop.it.helpers.Users.CUSTOMER;
import static java.text.MessageFormat.format;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.jdbc.Sql;

import com.cairoshop.it.models.Credentials;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
class ProductControllerIT
        extends BaseControllerIT {

    private static final String VALID_NEW_PRODUCT_JSON = "valid_new_product.json";
    private static final String VALID_NEW_PRODUCT_FOR_UPDATE_JSON = "valid_new_product_for_update.json";
    private static final String INVALID_NEW_PRODUCT_WITH_DUPLICATED_NAME_JSON = "invalid_new_product_with_duplicated_name.json";
    private static final String INVALID_NEW_PRODUCT_WITH_INVALID_PRICE_JSON = "invalid_new_product_with_invalid_price.json";
    private static final String INVALID_NEW_PRODUCT_WITH_INVALID_QUANTITY_JSON = "invalid_new_product_with_invalid_quantity.json";
    private static final String PRODUCT_1_JSON = "product_1.json";
    private static final String PRODUCTS_WITH_PAGINATION_JSON = "products_with_pagination.json";
    private static final String SORTABLE_FIELDS_JSON = "sortable_fields.json";
    private static final String SEARCH_PRODUCTS_WITH_PAGINATION_JSON = "search_products_with_pagination.json";

    @Sql(scripts = { "classpath:db/scripts/new_category.sql",
                        "classpath:db/scripts/new_vendor.sql" },
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = { "classpath:db/scripts/reset_product_table.sql",
                        "classpath:db/scripts/reset_category_table.sql",
                        "classpath:db/scripts/reset_vendor_table.sql" },
            executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldAddWhenProductPayloadIsValid() throws Exception {
        shouldAddAndReturnStatus201AndLocationOfCreatedItemWhenPayloadIsValidAndUserIsAuthorized(
            ADD_NEW_PRODUCT,
            ADMIN,
            VALID_NEW_PRODUCT_JSON);
    }

    @Sql(scripts = { "classpath:db/scripts/new_category.sql",
                        "classpath:db/scripts/new_vendor.sql",
                        "classpath:db/scripts/new_product.sql" },
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = { "classpath:db/scripts/reset_product_table.sql",
                        "classpath:db/scripts/reset_category_table.sql",
                        "classpath:db/scripts/reset_vendor_table.sql" },
            executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldFailAddWhenProductNameIsDuplicated() throws Exception {
        shouldFailAddAndReturnStatus400WithErrMsgWhenPayloadIsInvalidAndUserIsAuthorized(
            ADD_NEW_PRODUCT,
            ADMIN,
            INVALID_NEW_PRODUCT_WITH_DUPLICATED_NAME_JSON,
            DB_VIOLATED_CONSTRAINTS_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsWhenProductPayloadIsInvalid")
    void shouldFailAddWhenProductPayloadIsInvalid(String requestBodyFile, String errorMsgFile) throws Exception {
        shouldFailAddAndReturnStatus400WithErrMsgWhenPayloadIsInvalidAndUserIsAuthorized(
            ADD_NEW_PRODUCT,
            ADMIN,
            requestBodyFile,
            errorMsgFile);
    }

    @Test
    void shouldFailAddWhenUserIsUnauthorized() throws Exception {
        shouldFailAddAndReturnStatus403WithErrMsgWhenPayloadIsValidAndUserIsUnauthorized(
            ADD_NEW_PRODUCT,
            CUSTOMER,
            VALID_NEW_PRODUCT_JSON,
            ACCESS_DENIED_JSON);
    }

    @Sql(scripts = { "classpath:db/scripts/new_category.sql",
                        "classpath:db/scripts/new_vendor.sql",
                        "classpath:db/scripts/new_product.sql" },
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = { "classpath:db/scripts/reset_product_table.sql",
                        "classpath:db/scripts/reset_category_table.sql",
                        "classpath:db/scripts/reset_vendor_table.sql" },
            executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldEditWhenProductPayloadIsValid() throws Exception {
        shouldEditAndReturnStatus204WhenPayloadIsValidAndUserIsAuthorized(
            format(EDIT_PRODUCT, 1),
            ADMIN,
            VALID_NEW_PRODUCT_FOR_UPDATE_JSON);
    }

    @Sql(scripts = { "classpath:db/scripts/new_category.sql",
                        "classpath:db/scripts/new_vendor.sql",
                        "classpath:db/scripts/products.sql" },
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = { "classpath:db/scripts/reset_product_table.sql",
                        "classpath:db/scripts/reset_category_table.sql",
                        "classpath:db/scripts/reset_vendor_table.sql" },
            executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldFailEditWhenProductNameIsDuplicated() throws Exception {
        shouldFailEditAndReturnStatus400WithErrMsgWhenPayloadIsInvalidAndUserIsAuthorized(
            format(EDIT_PRODUCT, 2),
            ADMIN,
            INVALID_NEW_PRODUCT_WITH_DUPLICATED_NAME_JSON, DB_VIOLATED_CONSTRAINTS_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsWhenProductPayloadIsInvalid")
    void shouldFailEditWhenProductPayloadIsInvalid(String requestBodyFile, String errorMsgFile) throws Exception {
        shouldFailEditAndReturnStatus400WithErrMsgWhenPayloadIsInvalidAndUserIsAuthorized(
            format(EDIT_PRODUCT, 2),
            ADMIN,
            requestBodyFile,
            errorMsgFile);
    }

    @Test
    void shouldFailEditWhenUserIsUnauthorized() throws Exception {
        shouldFailEditAndReturnStatus402WithErrMsgWhenPayloadIsValidAndUserIsUnauthorized(
            format(EDIT_PRODUCT, 2),
            CUSTOMER,
            VALID_NEW_PRODUCT_FOR_UPDATE_JSON,
            ACCESS_DENIED_JSON);
    }

    @Sql(scripts = { "classpath:db/scripts/new_category.sql",
                        "classpath:db/scripts/new_vendor.sql",
                        "classpath:db/scripts/new_product.sql" },
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = { "classpath:db/scripts/reset_product_table.sql",
                        "classpath:db/scripts/reset_category_table.sql",
                        "classpath:db/scripts/reset_vendor_table.sql" },
            executionPhase = AFTER_TEST_METHOD)
    @ParameterizedTest
    @MethodSource("provideArgsOfUsers")
    void shouldReturnProductQueriedByIdWhenDataFound(Credentials credentials) throws Exception {
        shouldReturnStatus200WithDataWhenItsFoundAndUserIsAuthorized(
            format(GET_PRODUCT_BY_ID, 1),
            credentials,
            PRODUCT_1_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsOfUsers")
    void shouldFailRetrievalWhenProductQueriedByIdAndDataNotFound(Credentials credentials) throws Exception {
        shouldReturnStatus404WithErrMsgWhenDataNotFoundAndUserIsAuthorized(
            format(GET_PRODUCT_BY_ID, 404),
            credentials,
            NO_DATA_FOUND_JSON);
    }

    @Sql(scripts = { "classpath:db/scripts/new_category.sql",
                        "classpath:db/scripts/new_vendor.sql",
                        "classpath:db/scripts/products.sql" },
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = { "classpath:db/scripts/reset_product_table.sql",
                        "classpath:db/scripts/reset_category_table.sql",
                        "classpath:db/scripts/reset_vendor_table.sql" },
            executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldReturnProductsQueriedByPageWhenDataFound() throws Exception {
        shouldReturnStatus200WithDataWhenItsFoundAndUserIsAuthorized(
            format(GET_PRODUCTS_BY_PAGINATION, 0),
            ADMIN,
            PRODUCTS_WITH_PAGINATION_JSON);
    }

    @Test
    void shouldFailRetrievalWhenProductsQueriedByPageAndDataNotFound() throws Exception {
        shouldReturnStatus404WithErrMsgWhenDataNotFoundAndUserIsAuthorized(
            format(GET_PRODUCTS_BY_PAGINATION, 404),
            ADMIN,
            NO_DATA_FOUND_JSON);
    }

    @Test
    void shouldReturnSortableFieldsWhenDataFound() throws Exception {
        shouldReturnStatus200WithDataWhenItsFoundAndUserIsAuthorized(
            GET_SORTABLE_FIELDS_OF_PRODUCT,
            CUSTOMER,
            SORTABLE_FIELDS_JSON);
    }

    @Sql(scripts = { "classpath:db/scripts/new_category.sql",
                        "classpath:db/scripts/new_vendor.sql",
                        "classpath:db/scripts/products.sql" },
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = { "classpath:db/scripts/reset_product_table.sql",
                        "classpath:db/scripts/reset_category_table.sql",
                        "classpath:db/scripts/reset_vendor_table.sql" },
            executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldReturnProductsQueriedByProductNameWhenDataFound() throws Exception {
        shouldReturnStatus200WithDataWhenItsFoundAndUserIsAuthorized(
            format(SEARCH_PRODUCTS_BY_KEYWORD, "duct", 0, "name", "ASC"),
            CUSTOMER,
            SEARCH_PRODUCTS_WITH_PAGINATION_JSON);
    }

    @Test
    void shouldFailRetrievalWhenProductsQueriedByProductNameAndDataNotFound() throws Exception {
        shouldReturnStatus404WithErrMsgWhenDataNotFoundAndUserIsAuthorized(
            format(SEARCH_PRODUCTS_BY_KEYWORD, "XXX", 0, "name", "ASC"),
            CUSTOMER,
            NO_DATA_FOUND_JSON);
    }

    @Sql(scripts = { "classpath:db/scripts/new_category.sql",
        "classpath:db/scripts/new_vendor.sql",
        "classpath:db/scripts/new_product.sql" },
        executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = { "classpath:db/scripts/reset_product_table.sql",
        "classpath:db/scripts/reset_category_table.sql",
        "classpath:db/scripts/reset_vendor_table.sql" },
        executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldRemoveWhenDataFound() throws Exception {
        shouldRemoveItemAndReturnStatus204WhenDataFoundAndUserIsAuthorized(
            format(DELETE_PRODUCT_BY_ID, 1),
            ADMIN);
    }

    @Test
    void shouldFailRemoveWhenUserIsUnauthorized() throws Exception {
        shouldFailDataRemovalAndReturnStatus403WithErrMsgWhenUserIsUnauthorized(
            format(DELETE_PRODUCT_BY_ID, 1),
            CUSTOMER,
            ACCESS_DENIED_JSON);
    }

    private static Stream<Arguments> provideArgsWhenProductPayloadIsInvalid() {
        return Stream.of(
            Arguments.of(INVALID_NEW_PRODUCT_WITH_INVALID_PRICE_JSON, INVALID_PRICE_JSON),
            Arguments.of(INVALID_NEW_PRODUCT_WITH_INVALID_QUANTITY_JSON, INVALID_QUANTITY_JSON)
        );
    }

    private static Stream<Arguments> provideArgsOfUsers() {
        return Stream.of(
            Arguments.of(ADMIN),
            Arguments.of(CUSTOMER)
        );
    }

}
