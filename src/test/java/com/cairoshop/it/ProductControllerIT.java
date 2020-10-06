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
class ProductControllerIT extends BaseControllerIT {

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
    void testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation()
            throws Exception {
        testAddingDataWithValidPayloadAndAuthorizedUser(
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
    void testAdd_WhenProductNameIsDuplicated_ThenReturn400WithErrorMsg()
            throws Exception {
        testAddingDataWithInvalidPayloadAndAuthorizedUser(
            ADD_NEW_PRODUCT,
            ADMIN,
            INVALID_NEW_PRODUCT_WITH_DUPLICATED_NAME_JSON,
            DB_VIOLATED_CONSTRAINTS_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestAddWithInvalidPayload")
    void testAdd_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile)
            throws Exception {
        testAddingDataWithInvalidPayloadAndAuthorizedUser(
            ADD_NEW_PRODUCT,
            ADMIN,
            requestBodyFile,
            errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestAddWithInvalidPayload() {
        return Stream.of(
            Arguments.of(INVALID_NEW_PRODUCT_WITH_INVALID_PRICE_JSON, INVALID_PRICE_JSON),
            Arguments.of(INVALID_NEW_PRODUCT_WITH_INVALID_QUANTITY_JSON, INVALID_QUANTITY_JSON)
        );
    }

    @Test
    void testAdd_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testAddingDataWithValidPayloadAndUnauthorizedUser(
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
    void testEdit_WhenPayloadIsValid_ThenReturn204()
            throws Exception {
        testDataModificationWithValidPayloadAndAuthorizedUser(
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
    void testEdit_WhenProductNameIsDuplicated_ThenReturn400WithErrorMsg()
            throws Exception {
        testDataModificationWithInvalidPayloadAndAuthorizedUser(
            format(EDIT_PRODUCT, 2),
            ADMIN,
            INVALID_NEW_PRODUCT_WITH_DUPLICATED_NAME_JSON, DB_VIOLATED_CONSTRAINTS_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestEditWithInvalidPayload")
    void testEdit_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile)
            throws Exception {
        testDataModificationWithInvalidPayloadAndAuthorizedUser(
            format(EDIT_PRODUCT, 2),
            ADMIN,
            requestBodyFile,
            errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestEditWithInvalidPayload() {
        return Stream.of(
            Arguments.of(INVALID_NEW_PRODUCT_WITH_INVALID_PRICE_JSON, INVALID_PRICE_JSON),
            Arguments.of(INVALID_NEW_PRODUCT_WITH_INVALID_QUANTITY_JSON, INVALID_QUANTITY_JSON)
        );
    }

    @Test
    void testEdit_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataModificationWithValidPayloadAndUnauthorizedUser(
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
    @Test
    void testRemove_WhenDataFound_ThenRemoveItAndReturn204()
            throws Exception {
        testDataRemovalOfExistingDataUsingAuthorizedUser(
            format(DELETE_PRODUCT_BY_ID, 1),
            ADMIN);
    }

    @Test
    void testRemove_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataRemovalUsingUnauthorizedUser(
            format(DELETE_PRODUCT_BY_ID, 1),
            CUSTOMER,
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
    @MethodSource("provideArgsForTestGetByIdWhenDataFound")
    void testGetById_WhenDataFound_ThenReturn200WithData(Credentials credentials)
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(
            format(GET_PRODUCT_BY_ID, 1),
            credentials,
            PRODUCT_1_JSON);
    }

    private static Stream<Arguments> provideArgsForTestGetByIdWhenDataFound() {
        return Stream.of(
            Arguments.of(ADMIN),
            Arguments.of(CUSTOMER)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestGetByIdWhenDataNotFound")
    void testGetById_WhenDataNotFound_ThenReturn404WithErrorMsg(Credentials credentials)
            throws Exception {
        testDataRetrievalForNonExistedDataUsingAuthorizedUser(
            format(GET_PRODUCT_BY_ID, 404),
            credentials,
            NO_DATA_FOUND_JSON);
    }

    private static Stream<Arguments> provideArgsForTestGetByIdWhenDataNotFound() {
        return Stream.of(
            Arguments.of(ADMIN),
            Arguments.of(CUSTOMER)
        );
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
    void testGetAllItemsByPagination_WhenDataFound_ThenReturn200WithData()
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(
            format(GET_PRODUCTS_BY_PAGINATION, 0),
            ADMIN,
            PRODUCTS_WITH_PAGINATION_JSON);
    }

    @Test
    void testGetAllItemsByPagination_WhenDataNotFound_ThenReturn404WithErrorMsg()
            throws Exception {
        testDataRetrievalForNonExistedDataUsingAuthorizedUser(
            format(GET_PRODUCTS_BY_PAGINATION, 404),
            ADMIN,
            NO_DATA_FOUND_JSON);
    }

    @Test
    void testGetSortableFields_WhenDataFound_ThenReturn200WithData()
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(
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
    void testSearchByProductName_WhenDataFound_ThenReturn200WithData()
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(
            format(SEARCH_PRODUCTS_BY_KEYWORD, "duct", 0, "name", "ASC"),
            CUSTOMER,
            SEARCH_PRODUCTS_WITH_PAGINATION_JSON);
    }

    @Test
    void testSearchByProductName_WhenDataNotFound_ThenReturn404WithErrorMsg()
            throws Exception {
        testDataRetrievalForNonExistedDataUsingAuthorizedUser(
            format(SEARCH_PRODUCTS_BY_KEYWORD, "XXX", 0, "name", "ASC"),
            CUSTOMER,
            NO_DATA_FOUND_JSON);
    }

}
