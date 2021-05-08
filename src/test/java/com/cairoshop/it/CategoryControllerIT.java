package com.cairoshop.it;

import static com.cairoshop.it.helpers.Endpoints.ADD_NEW_CATEGORY;
import static com.cairoshop.it.helpers.Endpoints.DELETE_CATEGORY_BY_ID;
import static com.cairoshop.it.helpers.Endpoints.EDIT_CATEGORY;
import static com.cairoshop.it.helpers.Endpoints.GET_ALL_CATEGORIES;
import static com.cairoshop.it.helpers.Endpoints.GET_CATEGORIES_BY_PAGINATION;
import static com.cairoshop.it.helpers.Endpoints.GET_CATEGORY_BY_ID;
import static com.cairoshop.it.helpers.Errors.ACCESS_DENIED_JSON;
import static com.cairoshop.it.helpers.Errors.DB_VIOLATED_CONSTRAINTS_JSON;
import static com.cairoshop.it.helpers.Errors.NAME_IS_REQUIRED_JSON;
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
class CategoryControllerIT
        extends BaseControllerIT {

    private static final String VALID_NEW_CATEGORY_JSON = "valid_new_category.json";
    private static final String INVALID_NEW_CATEGORY_WITH_DUPLICATED_NAME_JSON = "invalid_new_category_with_duplicated_name.json";
    private static final String VALID_NEW_CATEGORY_FOR_UPDATE_JSON = "valid_new_category_for_update.json";
    private static final String INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_NAME_VALUE_JSON = "invalid_new_product_classification_with_empty_name_value.json";
    private static final String INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_PAYLOAD_JSON = "invalid_new_product_classification_with_empty_payload.json";
    private static final String LAPTOPS_CATEGORY_JSON = "laptops_category.json";
    private static final String ALL_CATEGORIES_JSON = "all_categories.json";
    private static final String CATEGORIES_WITH_PAGINATION_JSON = "categories_with_pagination.json";

    @Sql(scripts = "classpath:db/scripts/reset_category_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldAddWhenCategoryPayloadIsValid() throws Exception {
        shouldAddAndReturnStatus201AndLocationOfCreatedItemWhenPayloadIsValidAndUserIsAuthorized(
            ADD_NEW_CATEGORY,
            ADMIN,
            VALID_NEW_CATEGORY_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/new_category.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_category_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldFailAddWhenCategoryNameIsDuplicated() throws Exception {
        shouldFailAddAndReturnStatus400WithErrMsgWhenPayloadIsInvalidAndUserIsAuthorized(ADD_NEW_CATEGORY, ADMIN,
            INVALID_NEW_CATEGORY_WITH_DUPLICATED_NAME_JSON, DB_VIOLATED_CONSTRAINTS_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsWhenCategoryPayloadIsInvalid")
    void shouldFailAddWhenCategoryPayloadIsInvalid(String requestBodyFile, String errorMsgFile) throws Exception {
        shouldFailAddAndReturnStatus400WithErrMsgWhenPayloadIsInvalidAndUserIsAuthorized(ADD_NEW_CATEGORY, ADMIN, requestBodyFile, errorMsgFile);
    }

    @Test
    void shouldFailAddWhenUserIsUnauthorized() throws Exception {
        shouldFailAddAndReturnStatus403WithErrMsgWhenPayloadIsValidAndUserIsUnauthorized(
            ADD_NEW_CATEGORY,
            CUSTOMER,
            VALID_NEW_CATEGORY_JSON,
            ACCESS_DENIED_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/categories.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_category_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldEditWhenCategoryPayloadIsValid() throws Exception {
        shouldEditAndReturnStatus204WhenPayloadIsValidAndUserIsAuthorized(
            format(EDIT_CATEGORY, 1),
            ADMIN,
            VALID_NEW_CATEGORY_FOR_UPDATE_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/categories.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_category_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldFailEditWhenCategoryNameIsDuplicated() throws Exception {
        shouldFailEditAndReturnStatus400WithErrMsgWhenPayloadIsInvalidAndUserIsAuthorized(
            format(EDIT_CATEGORY, 2),
            ADMIN,
            INVALID_NEW_CATEGORY_WITH_DUPLICATED_NAME_JSON, DB_VIOLATED_CONSTRAINTS_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsWhenCategoryPayloadIsInvalid")
    void shouldFailEditWhenCategoryPayloadIsInvalid(String requestBodyFile, String errorMsgFile) throws Exception {
        shouldFailEditAndReturnStatus400WithErrMsgWhenPayloadIsInvalidAndUserIsAuthorized(
            format(EDIT_CATEGORY, 2),
            ADMIN,
            requestBodyFile,
            errorMsgFile);
    }

    @Test
    void shouldFailEditWhenUserIsUnauthorized() throws Exception {
        shouldFailEditAndReturnStatus402WithErrMsgWhenPayloadIsValidAndUserIsUnauthorized(
            format(EDIT_CATEGORY, 2),
            CUSTOMER,
            VALID_NEW_CATEGORY_JSON,
            ACCESS_DENIED_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/categories.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_category_table.sql", executionPhase = AFTER_TEST_METHOD)
    @ParameterizedTest
    @MethodSource("provideArgsOfUsers")
    void shouldReturnCategoryQueriedByIdWhenDataFound(Credentials credentials) throws Exception {
        shouldReturnStatus200WithDataWhenItsFoundAndUserIsAuthorized(
            format(GET_CATEGORY_BY_ID, 1),
            credentials,
            LAPTOPS_CATEGORY_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsOfUsers")
    void shouldFailRetrievalWhenCategoryQueriedByIdAndDataNotFound(Credentials credentials) throws Exception {
        shouldReturnStatus404WithErrMsgWhenDataNotFoundAndUserIsAuthorized(
            format(GET_CATEGORY_BY_ID, 404),
            credentials,
            NO_DATA_FOUND_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/categories.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_category_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldReturnCategoriesQueriedByPageWhenDataFound() throws Exception {
        shouldReturnStatus200WithDataWhenItsFoundAndUserIsAuthorized(
            format(GET_CATEGORIES_BY_PAGINATION, 0),
            ADMIN,
            CATEGORIES_WITH_PAGINATION_JSON);
    }

    @Test
    void shouldFailRetrievalWhenCategoriesQueriedByPageAndDataNotFound() throws Exception {
        shouldReturnStatus404WithErrMsgWhenDataNotFoundAndUserIsAuthorized(
            format(GET_CATEGORIES_BY_PAGINATION, 404),
            ADMIN,
            NO_DATA_FOUND_JSON);
    }

    @Test
    void shouldFailRetrievalWhenCategoriesQueriedByPageAndUserIsUnauthorized() throws Exception {
        shouldReturnStatus403WithErrMsgWhenUserIsUnauthorized(
            format(GET_CATEGORIES_BY_PAGINATION, 0),
            CUSTOMER,
            ACCESS_DENIED_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/categories.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_category_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldReturnCategoriesQueriedWithNoCriteriaWhenDataFound() throws Exception {
        shouldReturnStatus200WithDataWhenItsFoundAndUserIsAuthorized(
            GET_ALL_CATEGORIES,
            ADMIN,
            ALL_CATEGORIES_JSON);
    }

    @Test
    void shouldFailRetrievalWhenCategoriesQueriedWithNoCriteriaAndUserIsUnauthorized() throws Exception {
        shouldReturnStatus403WithErrMsgWhenUserIsUnauthorized(
            GET_ALL_CATEGORIES,
            CUSTOMER,
            ACCESS_DENIED_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/new_category.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_category_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldRemoveWhenDataFound() throws Exception {
        shouldRemoveItemAndReturnStatus204WhenDataFoundAndUserIsAuthorized(
            format(DELETE_CATEGORY_BY_ID, 1),
            ADMIN);
    }

    @Test
    void shouldFailRemoveWhenUserIsUnauthorized() throws Exception {
        shouldFailDataRemovalAndReturnStatus403WithErrMsgWhenUserIsUnauthorized(
            format(DELETE_CATEGORY_BY_ID, 1),
            CUSTOMER,
            ACCESS_DENIED_JSON);
    }

    @Test
    void shouldFailRemoveWhenDataNotFound() throws Exception {
        shouldFailDataRemovalAndReturnStatus404WithErrMsgWhenDataNotFoundAndUserIsAuthorized(
            format(DELETE_CATEGORY_BY_ID, 404),
            ADMIN,
            NO_DATA_FOUND_JSON);
    }

    private static Stream<Arguments> provideArgsWhenCategoryPayloadIsInvalid() {
        return Stream.of(
            Arguments.of(INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_NAME_VALUE_JSON, NAME_IS_REQUIRED_JSON),
            Arguments.of(INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_PAYLOAD_JSON, NAME_IS_REQUIRED_JSON)
        );
    }

    private static Stream<Arguments> provideArgsOfUsers() {
        return Stream.of(
            Arguments.of(ADMIN),
            Arguments.of(CUSTOMER)
        );
    }

}
