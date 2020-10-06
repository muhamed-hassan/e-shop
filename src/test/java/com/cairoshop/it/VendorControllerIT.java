package com.cairoshop.it;

import static com.cairoshop.it.helpers.Endpoints.ADD_NEW_VENDOR;
import static com.cairoshop.it.helpers.Endpoints.DELETE_VENDOR_BY_ID;
import static com.cairoshop.it.helpers.Endpoints.EDIT_VENDOR;
import static com.cairoshop.it.helpers.Endpoints.GET_ALL_VENDORS;
import static com.cairoshop.it.helpers.Endpoints.GET_VENDORS_BY_PAGINATION;
import static com.cairoshop.it.helpers.Endpoints.GET_VENDOR_BY_ID;
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
class VendorControllerIT extends BaseControllerIT {

    private static final String VALID_NEW_VENDOR_JSON = "valid_new_vendor.json";
    private static final String INVALID_NEW_VENDOR_WITH_DUPLICATED_NAME_JSON = "invalid_new_vendor_with_duplicated_name.json";
    private static final String ALL_VENDORS_JSON = "all_vendors.json";
    private static final String INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_NAME_VALUE_JSON = "invalid_new_product_classification_with_empty_name_value.json";
    private static final String INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_PAYLOAD_JSON = "invalid_new_product_classification_with_empty_payload.json";
    private static final String VALID_NEW_VENDOR_FOR_UPDATE_JSON = "valid_new_vendor_for_update.json";
    private static final String SONY_VENDOR_JSON = "sony_vendor.json";
    private static final String VENDORS_WITH_PAGINATION_JSON = "vendors_with_pagination.json";

    @Sql(scripts = "classpath:db/scripts/reset_vendor_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation()
            throws Exception {
        testAddingDataWithValidPayloadAndAuthorizedUser(
            ADD_NEW_VENDOR,
            ADMIN,
            VALID_NEW_VENDOR_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/new_vendor.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_vendor_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void testAdd_WhenVendorNameIsDuplicated_ThenReturn400WithErrorMsg()
            throws Exception {
        testAddingDataWithInvalidPayloadAndAuthorizedUser(ADD_NEW_VENDOR, ADMIN,
            INVALID_NEW_VENDOR_WITH_DUPLICATED_NAME_JSON, DB_VIOLATED_CONSTRAINTS_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestAddWithInvalidPayload")
    void testAdd_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile)
            throws Exception {
        testAddingDataWithInvalidPayloadAndAuthorizedUser(
            ADD_NEW_VENDOR,
            ADMIN,
            requestBodyFile,
            errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestAddWithInvalidPayload() {
        return Stream.of(
            Arguments.of(INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_NAME_VALUE_JSON, NAME_IS_REQUIRED_JSON),
            Arguments.of(INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_PAYLOAD_JSON, NAME_IS_REQUIRED_JSON)
        );
    }

    @Test
    void testAdd_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testAddingDataWithValidPayloadAndUnauthorizedUser(
            ADD_NEW_VENDOR,
            CUSTOMER,
            VALID_NEW_VENDOR_JSON,
            ACCESS_DENIED_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/new_vendor.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_vendor_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void testEdit_WhenPayloadIsValid_ThenReturn204()
            throws Exception {
        testDataModificationWithValidPayloadAndAuthorizedUser(
            format(EDIT_VENDOR, 1),
            ADMIN,
            VALID_NEW_VENDOR_FOR_UPDATE_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/vendors.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_vendor_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void testEdit_WhenVendorNameIsDuplicated_ThenReturn400WithErrorMsg()
            throws Exception {
        testDataModificationWithInvalidPayloadAndAuthorizedUser(
            format(EDIT_VENDOR, 2),
            ADMIN,
            INVALID_NEW_VENDOR_WITH_DUPLICATED_NAME_JSON, DB_VIOLATED_CONSTRAINTS_JSON);
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestEditWithInvalidPayload")
    void testEdit_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile)
            throws Exception {
        testDataModificationWithInvalidPayloadAndAuthorizedUser(
            format(EDIT_VENDOR, 2),
            ADMIN,
            requestBodyFile,
            errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestEditWithInvalidPayload() {
        return Stream.of(
            Arguments.of(INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_NAME_VALUE_JSON, NAME_IS_REQUIRED_JSON),
            Arguments.of(INVALID_NEW_PRODUCT_CLASSIFICATION_WITH_EMPTY_PAYLOAD_JSON, NAME_IS_REQUIRED_JSON)
        );
    }

    @Test
    void testEdit_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataModificationWithValidPayloadAndUnauthorizedUser(
            format(EDIT_VENDOR, 2),
            CUSTOMER,
            VALID_NEW_VENDOR_JSON,
            ACCESS_DENIED_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/new_vendor.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_vendor_table.sql", executionPhase = AFTER_TEST_METHOD)
    @ParameterizedTest
    @MethodSource("provideArgsForTestGetByIdWhenDataFound")
    void testGetById_WhenDataFound_ThenReturn200AndData(Credentials credentials)
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(
            format(GET_VENDOR_BY_ID, 1),
            credentials,
            SONY_VENDOR_JSON);
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
            format(GET_VENDOR_BY_ID, 404),
            credentials,
            NO_DATA_FOUND_JSON);
    }

    private static Stream<Arguments> provideArgsForTestGetByIdWhenDataNotFound() {
        return Stream.of(
            Arguments.of(ADMIN),
            Arguments.of(CUSTOMER)
        );
    }

    @Sql(scripts = "classpath:db/scripts/vendors.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_vendor_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void testGetAllItemsByPagination_WhenDataFound_ThenReturn200WithData()
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(
            format(GET_VENDORS_BY_PAGINATION, 0),
            ADMIN,
            VENDORS_WITH_PAGINATION_JSON);
    }

    @Test
    void testGetAllItemsByPagination_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataRetrievalUsingUnauthorizedUser(
            format(GET_VENDORS_BY_PAGINATION, 0),
            CUSTOMER,
            ACCESS_DENIED_JSON);
    }

    @Test
    void testGetAllItemsByPagination_WhenDataNotFound_ThenReturn404WithErrorMsg()
            throws Exception {
        testDataRetrievalForNonExistedDataUsingAuthorizedUser(
            format(GET_VENDORS_BY_PAGINATION, 404),
            ADMIN,
            NO_DATA_FOUND_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/vendors.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_vendor_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void testGetAll_WhenDataFound_ThenReturn200WithData()
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(
            GET_ALL_VENDORS,
            ADMIN,
            ALL_VENDORS_JSON);
    }

    @Test
    void testGetAll_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataRetrievalUsingUnauthorizedUser(
            GET_ALL_VENDORS,
            CUSTOMER,
            ACCESS_DENIED_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/new_vendor.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/reset_vendor_table.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void testRemove_WhenDataFound_ThenRemoveItAndReturn204() throws Exception {
        testDataRemovalOfExistingDataUsingAuthorizedUser(
            format(DELETE_VENDOR_BY_ID, 1),
            ADMIN);
    }

    @Test
    void testRemove_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataRemovalUsingUnauthorizedUser(
            format(DELETE_VENDOR_BY_ID, 1),
            CUSTOMER,
            ACCESS_DENIED_JSON);
    }

    @Test
    void testRemove_WhenDataNotFound_ThenReturn404WithErrorMsg()
            throws Exception {
        testDataRemovalOfNonExistingDataUsingAuthorizedUser(
            format(DELETE_VENDOR_BY_ID, 404),
            ADMIN,
            NO_DATA_FOUND_JSON);
    }

}
