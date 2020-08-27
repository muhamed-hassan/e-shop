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

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.cairoshop.it.models.Credentials;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class CategoryControllerIT
            extends BaseControllerIT {

    private static final String VALID_NEW_CATEGORY_JSON = "valid_new_category.json";
    private static final String INVALID_NEW_CATEGORY_WITH_DUPLICATED_NAME_JSON = "invalid_new_category_with_duplicated_name.json";
    private static final String VALID_NEW_CATEGORY_FOR_UPDATE_JSON = "valid_new_category_for_update.json";
    private static final String INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_NAME_VALUE_JSON = "invalid_new_product_classification_with_empty_name_value.json";
    private static final String INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_PAYLOAD_JSON = "invalid_new_product_classification_with_empty_payload.json";
    private static final String LAPTOPS_CATEGORY_JSON = "laptops_category.json";
    private static final String ALL_CATEGORIES_JSON = "all_categories.json";
    private static final String CATEGORIES_WITH_PAGINATION_JSON = "categories_with_pagination.json";

    @Test
    protected void testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation() {
        testAddingDataWithValidPayloadAndAuthorizedUser(
            ADD_NEW_CATEGORY,
            ADMIN,
            VALID_NEW_CATEGORY_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestAddWithInvalidPayload")
    public void testAdd_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile)
            throws Exception {
        testAddingDataWithInvalidPayloadAndAuthorizedUser(ADD_NEW_CATEGORY, ADMIN, requestBodyFile, errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestAddWithInvalidPayload() {
        return Stream.of(
            Arguments.of(INVALID_NEW_CATEGORY_WITH_DUPLICATED_NAME_JSON, DB_VIOLATED_CONSTRAINTS_JSON),
            Arguments.of(INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_NAME_VALUE_JSON, NAME_IS_REQUIRED_JSON),
            Arguments.of(INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_PAYLOAD_JSON, NAME_IS_REQUIRED_JSON)
        );
    }

    @Test
    public void testAdd_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testAddingDataWithValidPayloadAndUnauthorizedUser(
            ADD_NEW_CATEGORY,
            CUSTOMER,
            VALID_NEW_CATEGORY_JSON,
            ACCESS_DENIED_JSON);
    }

    @Test
    public void testEdit_WhenPayloadIsValid_ThenReturn204() {
        testDataModificationWithValidPayloadAndAuthorizedUser(
            format(EDIT_CATEGORY, 1),
            ADMIN,
            VALID_NEW_CATEGORY_FOR_UPDATE_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestEditWithInvalidPayload")
    public void testEdit_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile)
            throws Exception {
        testDataModificationWithInvalidPayloadAndAuthorizedUser(
            format(EDIT_CATEGORY, 2),
            ADMIN,
            requestBodyFile,
            errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestEditWithInvalidPayload() {
        return Stream.of(
            Arguments.of(INVALID_NEW_CATEGORY_WITH_DUPLICATED_NAME_JSON, DB_VIOLATED_CONSTRAINTS_JSON),
            Arguments.of(INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_NAME_VALUE_JSON, NAME_IS_REQUIRED_JSON),
            Arguments.of(INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_PAYLOAD_JSON, NAME_IS_REQUIRED_JSON)
        );
    }

    @Test
    public void testEdit_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataModificationWithValidPayloadAndUnauthorizedUser(
            format(EDIT_CATEGORY, 2),
            CUSTOMER,
            VALID_NEW_CATEGORY_JSON,
            ACCESS_DENIED_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestGetByIdWhenDataFound")
    public void testGetById_WhenDataFound_ThenReturn200AndData(Credentials credentials)
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(
            format(GET_CATEGORY_BY_ID, 1),
            credentials,
            LAPTOPS_CATEGORY_JSON);
    }

    private static Stream<Arguments> provideArgsForTestGetByIdWhenDataFound() {
        return Stream.of(
            Arguments.of(ADMIN),
            Arguments.of(CUSTOMER)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestGetByIdWhenDataNotFound")
    public void testGetById_WhenDataNotFound_ThenReturn404WithErrorMsg(Credentials credentials)
            throws Exception {
        testDataRetrievalForNonExistedDataUsingAuthorizedUser(
            format(GET_CATEGORY_BY_ID, 404),
            credentials,
            NO_DATA_FOUND_JSON);
    }

    private static Stream<Arguments> provideArgsForTestGetByIdWhenDataNotFound() {
        return Stream.of(
            Arguments.of(ADMIN),
            Arguments.of(CUSTOMER)
        );
    }

    @Test
    public void testGetAllItemsByPagination_WhenDataFound_ThenReturn200WithData()
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(
            format(GET_CATEGORIES_BY_PAGINATION, 0),
            ADMIN,
            CATEGORIES_WITH_PAGINATION_JSON);
    }

    @Test
    public void testGetAllItemsByPagination_WhenDataNotFound_ThenReturn404WithErrorMsg()
            throws Exception {
        testDataRetrievalForNonExistedDataUsingAuthorizedUser(
            format(GET_CATEGORIES_BY_PAGINATION, 404),
            ADMIN,
            NO_DATA_FOUND_JSON);
    }

    @Test
    public void testGetAllItemsByPagination_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataRetrievalUsingUnauthorizedUser(
            format(GET_CATEGORIES_BY_PAGINATION, 0),
            CUSTOMER,
            ACCESS_DENIED_JSON);
    }

    @Test
    public void testGetAll_WhenDataFound_ThenReturn200WithData()
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(
            GET_ALL_CATEGORIES,
            ADMIN,
            ALL_CATEGORIES_JSON);
    }

    @Test
    public void testGetAll_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataRetrievalUsingUnauthorizedUser(
            GET_ALL_CATEGORIES,
            CUSTOMER,
            ACCESS_DENIED_JSON);
    }

    @Test
    public void testRemove_WhenDataFound_ThenRemoveItAndReturn204() {
        testDataRemovalOfExistingDataUsingAuthorizedUser(
            format(DELETE_CATEGORY_BY_ID, 5),
            ADMIN);
    }

    @Test
    public void testRemove_WhenUserIsUnauthorized_ThenRemoveItAndReturn403WithErrorMsg()
            throws Exception {
        testDataRemovalUsingUnauthorizedUser(
            format(DELETE_CATEGORY_BY_ID, 5),
            CUSTOMER,
            ACCESS_DENIED_JSON);
    }

    @Test
    public void testRemove_WhenDataNotFound_ThenRemoveItAndReturn404WithErrorMsg()
            throws Exception {
        testDataRemovalOfNonExistingDataUsingAuthorizedUser(
            format(DELETE_CATEGORY_BY_ID, 404),
            ADMIN,
            NO_DATA_FOUND_JSON);
    }

}
