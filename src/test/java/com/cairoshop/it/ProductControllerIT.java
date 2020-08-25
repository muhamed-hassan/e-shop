package com.cairoshop.it;

import static com.cairoshop.it.helpers.Endpoints.ADD_NEW_PRODUCT;
import static com.cairoshop.it.helpers.Endpoints.DELETE_PRODUCT_BY_ID;
import static com.cairoshop.it.helpers.Endpoints.EDIT_CATEGORY;
import static com.cairoshop.it.helpers.Endpoints.EDIT_PRODUCT;
import static com.cairoshop.it.helpers.Endpoints.GET_CATEGORIES_BY_PAGINATION;
import static com.cairoshop.it.helpers.Endpoints.GET_CATEGORY_BY_ID;
import static com.cairoshop.it.helpers.Endpoints.GET_PRODUCTS_BY_PAGINATION;
import static com.cairoshop.it.helpers.Endpoints.GET_PRODUCT_BY_ID;
import static com.cairoshop.it.helpers.Endpoints.GET_SORTABLE_FIELDS_OF_PRODUCT;
import static com.cairoshop.it.helpers.Users.ADMIN;
import static com.cairoshop.it.helpers.Users.CUSTOMER;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.cairoshop.it.helpers.Endpoints;
import com.cairoshop.it.models.Credentials;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class ProductControllerIT
            extends BaseControllerIT {

    //admin
    @Test
    public void testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation()
            throws Exception {
        testAddingDataWithValidPayloadAndAuthorizedUser(ADD_NEW_PRODUCT,
                                                          ADMIN,
                                            "valid_new_product.json");
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestAddWithInvalidPayload")
    public void testAdd_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile)
        throws Exception {
        testAddingDataWithInvalidPayloadAndAuthorizedUser(ADD_NEW_PRODUCT,
            ADMIN,
            requestBodyFile,
            errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestAddWithInvalidPayload() {
        return Stream.of(
            Arguments.of("invalid_new_product_with_duplicated_name.json",
                "db_violated_constraints.json"),
            Arguments.of("invalid_new_product_with_invalid_price.json",
                "invalid_price.json"),
            Arguments.of("invalid_new_product_with_invalid_quantity.json",
                "invalid_quantity.json")
        );
    }

    @Test
    public void testAdd_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
        throws Exception {
        testAddingDataWithValidPayloadAndUnauthorizedUser(ADD_NEW_PRODUCT,
            CUSTOMER,
            "valid_new_product.json",
            "access_denied.json");
    }

    @Test
    // admin
    public void testEdit_WhenPayloadIsValid_ThenReturn204() throws Exception {
        testDataModificationWithValidPayloadAndAuthorizedUser(MessageFormat.format(EDIT_PRODUCT, 1),
            ADMIN,
            "valid_new_product_for_update.json");
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestEditWithInvalidPayload")
    public void testEdit_WhenPayloadIsInvalid_ThenReturn400WithErrorMsg(String requestBodyFile, String errorMsgFile)
        throws Exception {
        testDataModificationWithInvalidPayloadAndAuthorizedUser(MessageFormat.format(EDIT_PRODUCT, 2),
            ADMIN,
            requestBodyFile,
            errorMsgFile);
    }

    private static Stream<Arguments> provideArgsForTestEditWithInvalidPayload() {
        return Stream.of(
            Arguments.of("invalid_new_product_with_duplicated_name.json",
                "db_violated_constraints.json"),
            Arguments.of("invalid_new_product_with_invalid_price.json",
                "invalid_price.json"),
            Arguments.of("invalid_new_product_with_invalid_quantity.json",
                "invalid_quantity.json")
        );
    }

    @Test
    public void testEdit_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
        throws Exception {
        testDataModificationWithValidPayloadAndUnauthorizedUser(MessageFormat.format(EDIT_PRODUCT, 2),
            CUSTOMER,
            "valid_new_product_for_update.json",
            "access_denied.json");
    }





    @Test
    public void testRemove_WhenItemExists_ThenRemoveItAndReturn204() {
        testDataRemovalOfExistingDataUsingAuthorizedUser(MessageFormat.format(DELETE_PRODUCT_BY_ID, 3),ADMIN);
    }

    @Test
    public void testRemove_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg() throws Exception {
        testDataRemovalUsingUnauthorizedUser(MessageFormat.format(DELETE_PRODUCT_BY_ID, 3),CUSTOMER,"access_denied.json");
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestGetByIdWhenUserIsAuthorizedAndDataFound")
    public void testGetById_WhenUserIsAuthorizedAndDataFound_ThenReturn200AndData(Credentials credentials)
        throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(MessageFormat.format(GET_PRODUCT_BY_ID, 1),
            credentials,
            "product_1.json");
    }

    private static Stream<Arguments> provideArgsForTestGetByIdWhenUserIsAuthorizedAndDataFound() {
        return Stream.of(
            Arguments.of(ADMIN),
            Arguments.of(CUSTOMER)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgsForTestGetByIdWhenUserIsAuthorizedAndDataNotFound")
    public void testGetById_WhenUserIsAuthorizedAndDataNotFound_ThenReturn404WithErrorMsg(Credentials credentials)
        throws Exception {
        testDataRetrievalForNonExistedDataUsingAuthorizedUser(MessageFormat.format(GET_PRODUCT_BY_ID, 404),
            credentials,
            "no_data_found.json");
    }

    private static Stream<Arguments> provideArgsForTestGetByIdWhenUserIsAuthorizedAndDataNotFound() {
        return Stream.of(
            Arguments.of(ADMIN),
            Arguments.of(CUSTOMER)
        );
    }

    @Test
    public void testGetAllItemsByPagination_WhenUserIsAuthorizedAndDataFound_ThenReturn200WithData()
        throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(MessageFormat.format(GET_PRODUCTS_BY_PAGINATION, 0),
            ADMIN,
            "products_with_pagination.json");
    }

    @Test
    public void testGetAllItemsByPagination_WhenUserIsAuthorizedAndDataNotFound_ThenReturn404WithErrorMsg()
        throws Exception {
        testDataRetrievalForNonExistedDataUsingAuthorizedUser(MessageFormat.format(GET_PRODUCTS_BY_PAGINATION, 404),
            ADMIN,
            "no_data_found.json");
    }

    @Test
    public void testGetSortableFields_WhenDataExists_ThenReturnDataWith200() throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(GET_SORTABLE_FIELDS_OF_PRODUCT,
            CUSTOMER,
            "sortable_fields.json");
    }

    //customer
    public void testSearchByProductName_WhenDataExists_ThenReturnDataWith200() {

    }

    public void testUploadImage_WhenProductExist_ThenUpdateItAndReturn200() {

    }

    public void testDownloadImage_WhenProductExist_ThenReturnImageStreamWith200() {

    }

}
