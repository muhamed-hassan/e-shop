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
public class CategoryControllerIT
            extends BaseProductClassificationControllerIT {

    @Test
    protected void testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation() throws Exception {
        testAddingDataWithValidPayloadAndAuthorizedUser(Endpoints.ADD_NEW_CATEGORY, Users.ADMIN,"valid_new_category.json");
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestAddWithInvalidPayload")
    public void testAdd_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile) throws Exception {
        testAddingDataWithInvalidPayloadAndAuthorizedUser(Endpoints.ADD_NEW_CATEGORY, Users.ADMIN, requestBodyFile, errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestAddWithInvalidPayload() {
        return Stream.of(
            Arguments.of("invalid_new_category_with_duplicated_name.json", "db_violated_constraints.json"),
            Arguments.of("invalid_new_product_classification_with_empty_name_value.json", "name_is_required.json"),
            Arguments.of("invalid_new_product_classification_with_empty_payload.json", "name_is_required.json")
        );
    }

    @Test
    public void testAdd_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg() throws Exception {
        testAddingDataWithValidPayloadAndUnauthorizedUser(Endpoints.ADD_NEW_CATEGORY, Users.CUSTOMER,"valid_new_category.json", "access_denied.json");
    }

    @Test
    public void testEdit_WhenPayloadIsValid_ThenReturn204() throws Exception {
        testDataModificationWithValidPayloadAndAuthorizedUser(MessageFormat.format(Endpoints.EDIT_CATEGORY, 1), Users.ADMIN,"valid_new_category_for_update.json");
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestEditWithInvalidPayload")
    public void testEdit_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile) throws Exception {
        testDataModificationWithInvalidPayloadAndAuthorizedUser(MessageFormat.format(Endpoints.EDIT_CATEGORY, 2), Users.ADMIN, requestBodyFile, errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestEditWithInvalidPayload() {
        return Stream.of(
            Arguments.of("invalid_new_category_with_duplicated_name.json", "db_violated_constraints.json"),
            Arguments.of("invalid_new_product_classification_with_empty_name_value.json", "name_is_required.json"),
            Arguments.of("invalid_new_product_classification_with_empty_payload.json", "name_is_required.json")
        );
    }

    @Test
    public void testEdit_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg() throws Exception {
        testDataModificationWithValidPayloadAndUnauthorizedUser(MessageFormat.format(Endpoints.EDIT_VENDOR, 2), Users.CUSTOMER,"valid_new_vendor.json", "access_denied.json");
    }

    //admin or customer
    protected void testGetById_WhenDataFound_ThenReturn200AndData() throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(null, null, null);
    }

    //admin or customer
    protected void testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData() throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(null, null, null);
    }

    // admin or customer
    public void testGetAll_WhenDataExists_ThenReturn200WithData() throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(null, null, null);
    }

    //admin
    protected void testRemove_WhenItemExists_ThenRemoveItAndReturn204() {
        testDataRemoval(null,null);
    }

}
