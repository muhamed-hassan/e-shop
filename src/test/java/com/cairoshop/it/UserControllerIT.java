package com.cairoshop.it;

import static com.cairoshop.it.helpers.Endpoints.EDIT_USER;
import static com.cairoshop.it.helpers.Endpoints.GET_USER_BY_ID;
import static com.cairoshop.it.helpers.Users.ADMIN;
import static com.cairoshop.it.helpers.Users.CUSTOMER;

import java.text.MessageFormat;

import org.junit.jupiter.api.Test;

import com.cairoshop.it.helpers.Endpoints;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class UserControllerIT
            extends BaseControllerIT {

    @Test
    public void testEdit_WhenPayloadIsValid_ThenReturn204()
            throws Exception {
        testDataModificationWithValidPayloadAndAuthorizedUser(MessageFormat.format(EDIT_USER, 3),
                                                                ADMIN,
                                                  "valid_new_status_of_user.json");
    }

    @Test
    public void testEdit_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataModificationWithValidPayloadAndUnauthorizedUser(MessageFormat.format(EDIT_USER, 3),
                                                                  CUSTOMER,
                                                     "valid_new_status_of_user.json",
                                                       "access_denied.json");
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturn200AndData()
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(MessageFormat.format(GET_USER_BY_ID, 3),
                                                                  ADMIN,
                                                "admin_user.json");
    }

    @Test
    public void testGetById_WhenUserIsAuthorizedAndDataNotFound_ThenReturn404WithErrorMsg()
            throws Exception {
        testDataRetrievalForNonExistedDataUsingAuthorizedUser(MessageFormat.format(Endpoints.GET_USER_BY_ID, 404),
                                                                ADMIN,
                                                     "no_data_found.json");
    }

    @Test
    public void testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData()
            throws Exception {
        testDataRetrievalToReturnExistedDataUsingAuthorizedUser(MessageFormat.format(Endpoints.GET_USERS_BY_PAGINATION, 0),
                                                                  ADMIN,
                                                "all_users.json");
    }

    @Test
    public void testGetAllItemsByPagination_WhenUserIsUnauthorized_ThenReturn403WithErrorMsg()
            throws Exception {
        testDataRetrievalUsingUnauthorizedUser(MessageFormat.format(Endpoints.GET_USERS_BY_PAGINATION, 0),
                                                 CUSTOMER,
                                      "access_denied.json");
    }

}
