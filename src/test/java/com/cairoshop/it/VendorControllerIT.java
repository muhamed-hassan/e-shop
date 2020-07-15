package com.cairoshop.it;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.cairoshop.it.helpers.Endpoints;
import com.cairoshop.it.helpers.Users;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class VendorControllerIT
            extends BaseProductClassificationControllerIT {

//    @Test
    public void testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation() throws Exception {
        testAddingDataWithValidPayloadAndAuthorizedUser(Endpoints.ADD_NEW_VENDOR, Users.ADMIN,"valid_new_vendor.json");
    }

//    @ParameterizedTest
//    @MethodSource("provideArgsForTestAddWithInvalidPayload")
    public void testAdd_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile) throws Exception {
        testAddingDataWithInvalidPayloadAndAuthorizedUser(Endpoints.ADD_NEW_VENDOR, Users.ADMIN, requestBodyFile, errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestAddWithInvalidPayload() {
        return Stream.of(
            Arguments.of("invalid_new_vendor_with_duplicated_name.json", "db_violated_constraints.json"),
            Arguments.of("invalid_new_vendor_with_empty_name_value.json", "name_is_required.json"),
            Arguments.of("invalid_new_vendor_with_empty_payload.json", "name_is_required.json")
        );
    }

//    @Test
    public void testAdd_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg() throws Exception {
        testAddingDataWithValidPayloadAndUnauthorizedUser(Endpoints.ADD_NEW_VENDOR, Users.CUSTOMER,"valid_new_vendor.json", "access_denied.json");
    }

//    @Test
    public void testEdit_WhenPayloadIsValid_ThenReturn204() throws Exception {
        testDataModification(Endpoints.EDIT_VENDOR, Users.ADMIN,"valid_new_vendor_for_update.json");
    }

//    @ParameterizedTest
//    @MethodSource("provideArgsForTestEditWithInvalidPayload")
    public void testEdit_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile) throws Exception {
        testAddingDataWithInvalidPayloadAndAuthorizedUser(Endpoints.EDIT_VENDOR, Users.ADMIN, requestBodyFile, errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestEditWithInvalidPayload() {
        return Stream.of(
            Arguments.of("invalid_new_vendor_with_duplicated_name.json", "db_violated_constraints.json"),
            Arguments.of("invalid_new_vendor_with_empty_name_value.json", "name_is_required.json"),
            Arguments.of("invalid_new_vendor_with_empty_payload.json", "name_is_required.json")
        );
    }

    //admin
    public void testRemove_WhenItemExists_ThenRemoveItAndReturn204() {
        testDataRemoval(Endpoints.DELETE_VENDOR_BY_ID,Users.ADMIN);
    }

    //admin or customer
    public void testGetById_WhenDataFound_ThenReturn200AndData() throws Exception {
        testDataRetrievalToReturnExistedData(Endpoints.GET_VENDOR_BY_ID, Users.ADMIN, null);
    }

    //admin or customer
    public void testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData() throws Exception {
        testDataRetrievalToReturnExistedData(MessageFormat.format(Endpoints.GET_VENDORS_BY_PAGINATION, 0), Users.ADMIN, null);
    }

    // admin or customer
    public void testGetAll_WhenDataExists_ThenReturn200WithData() throws Exception {
        testDataRetrievalToReturnExistedData(Endpoints.GET_ALL_VENDORS, Users.ADMIN, null);
    }

}
