package com.cairoshop.it;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.cairoshop.it.helpers.Endpoints;
import com.cairoshop.it.helpers.Users;
import com.cairoshop.it.models.Credentials;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class VendorControllerIT
            extends BaseProductClassificationControllerIT {

    @Test
    public void testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation()
            throws Exception {
        testAddingDataWithValidPayloadAndAuthorizedUser(Endpoints.ADD_NEW_VENDOR,
                                                            Users.ADMIN,
                                                            "valid_new_vendor.json");
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestAddWithInvalidPayload")
    public void testAdd_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile)
            throws Exception {
        testAddingDataWithInvalidPayloadAndAuthorizedUser(Endpoints.ADD_NEW_VENDOR,
                                                            Users.ADMIN,
                                                            requestBodyFile,
                                                            errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestAddWithInvalidPayload() {
        return Stream.of(
            Arguments.of("invalid_new_vendor_with_duplicated_name.json",
                            "db_violated_constraints.json"),
            Arguments.of("invalid_new_product_classification_with_empty_name_value.json",
                            "name_is_required.json"),
            Arguments.of("invalid_new_product_classification_with_empty_payload.json",
                            "name_is_required.json")
        );
    }

    @Test
    public void testAdd_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testAddingDataWithValidPayloadAndUnauthorizedUser(Endpoints.ADD_NEW_VENDOR,
                                                            Users.CUSTOMER,
                                                            "valid_new_vendor.json",
                                                            "access_denied.json");
    }

    @Test
    public void testEdit_WhenPayloadIsValid_ThenReturn204()
            throws Exception {
        testDataModificationWithValidPayloadAndAuthorizedUser(MessageFormat.format(Endpoints.EDIT_VENDOR, 1),
                                                                Users.ADMIN,
                                                                "valid_new_vendor_for_update.json");
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestEditWithInvalidPayload")
    public void testEdit_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile)
            throws Exception {
        testDataModificationWithInvalidPayloadAndAuthorizedUser(MessageFormat.format(Endpoints.EDIT_VENDOR, 2),
                                                                    Users.ADMIN,
                                                                    requestBodyFile,
                                                                    errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestEditWithInvalidPayload() {
        return Stream.of(
            Arguments.of("invalid_new_vendor_with_duplicated_name.json",
                            "db_violated_constraints.json"),
            Arguments.of("invalid_new_product_classification_with_empty_name_value.json",
                            "name_is_required.json"),
            Arguments.of("invalid_new_product_classification_with_empty_payload.json",
                            "name_is_required.json")
        );
    }

    @Test
    public void testEdit_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataModificationWithValidPayloadAndUnauthorizedUser(MessageFormat.format(Endpoints.EDIT_VENDOR, 2),
                                                                    Users.CUSTOMER,
                                                                    "valid_new_vendor.json",
                                                                    "access_denied.json");
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestGetByIdWhenUserIsAuthorizedAndDataFound")
    public void testGetById_WhenUserIsAuthorizedAndDataFound_ThenReturn200AndData(Credentials credentials)
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(MessageFormat.format(Endpoints.GET_VENDOR_BY_ID, 4),
                                                                    credentials,
                                                                    "hp_vendor.json");
    }

    private static Stream<Arguments> provideArgsForTestGetByIdWhenUserIsAuthorizedAndDataFound() {
        return Stream.of(
            Arguments.of(Users.ADMIN),
            Arguments.of(Users.CUSTOMER)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestGetByIdWhenUserIsAuthorizedAndDataNotFound")
    public void testGetById_WhenUserIsAuthorizedAndDataNotFound_ThenReturn404WithErrorMsg(Credentials credentials)
            throws Exception {
        testDataRetrievalForNonExistedDataUsingAuthorizedUser(MessageFormat.format(Endpoints.GET_VENDOR_BY_ID, 404),
                                                                credentials,
                                                                "no_data_found.json");
    }

    private static Stream<Arguments> provideArgsForTestGetByIdWhenUserIsAuthorizedAndDataNotFound() {
        return Stream.of(
            Arguments.of(Users.ADMIN),
            Arguments.of(Users.CUSTOMER)
        );
    }

    @Test
    public void testGetAllItemsByPagination_WhenUserIsAuthorizedAndDataFound_ThenReturn200WithData()
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(MessageFormat.format(Endpoints.GET_VENDORS_BY_PAGINATION, 0),
                                                                    Users.ADMIN,
                                                                    "vendors_with_pagination.json");
    }

    @Test
    public void testGetAllItemsByPagination_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataRetrievalUsingUnauthorizedUser(MessageFormat.format(Endpoints.GET_VENDORS_BY_PAGINATION, 0),
                                                Users.CUSTOMER,
                                                "access_denied.json");
    }

    @Test
    public void testGetAllItemsByPagination_WhenUserIsAuthorizedAndDataNotFound_ThenReturn404WithErrorMsg()
            throws Exception {
        testDataRetrievalForNonExistedDataUsingAuthorizedUser(MessageFormat.format(Endpoints.GET_VENDORS_BY_PAGINATION, 404),
                                                                Users.ADMIN,
                                                                "no_data_found.json");
    }

    @Test
    public void testGetAll_WhenUserIsAuthorizedAndDataFound_ThenReturn200WithData()
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(Endpoints.GET_ALL_VENDORS,
                                                                    Users.ADMIN,
                                                                    "all_vendors.json");
    }

    @Test
    public void testGetAll_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataRetrievalUsingUnauthorizedUser(Endpoints.GET_ALL_VENDORS,
                                                Users.CUSTOMER,
                                                "access_denied.json");
    }

    @Test
    public void testRemove_WhenItemExists_ThenRemoveItAndReturn204() {
        testDataRemovalOfExistingDataUsingAuthorizedUser(MessageFormat.format(Endpoints.DELETE_VENDOR_BY_ID, 5),
                                                            Users.ADMIN);
    }

    @Test
    public void testRemove_WhenUserIsUnauthorized_ThenRemoveItAndReturn403WithErrorMsg()
            throws Exception {
        testDataRemovalUsingUnauthorizedUser(MessageFormat.format(Endpoints.DELETE_VENDOR_BY_ID, 5),
                                                Users.CUSTOMER,
                                                "access_denied.json");
    }

    @Test
    public void testRemove_WhenUserIsAuthorizedAndDataNotFound_ThenRemoveItAndReturn404WithErrorMsg()
            throws Exception {
        testDataRemovalOfNonExistingDataUsingAuthorizedUser(MessageFormat.format(Endpoints.DELETE_VENDOR_BY_ID, 404),
                                                                Users.ADMIN,
                                                                "no_data_found.json");
    }

}
