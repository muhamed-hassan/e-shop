package com.cairoshop.it;

import static com.cairoshop.it.helpers.Endpoints.EDIT_USER;
import static com.cairoshop.it.helpers.Endpoints.GET_USERS_BY_PAGINATION;
import static com.cairoshop.it.helpers.Endpoints.GET_USER_BY_ID;
import static com.cairoshop.it.helpers.Errors.ACCESS_DENIED_JSON;
import static com.cairoshop.it.helpers.Errors.NO_DATA_FOUND_JSON;
import static com.cairoshop.it.helpers.Users.ADMIN;
import static com.cairoshop.it.helpers.Users.CUSTOMER;
import static java.text.MessageFormat.format;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
class UserControllerIT
        extends BaseControllerIT {

    private static final String VALID_NEW_STATUS_OF_USER_JSON = "valid_new_status_of_user.json";
    private static final String CUSTOMER_USER_JSON = "customer_user.json";
    private static final String ALL_USERS_JSON = "all_users.json";

    @Sql(scripts = "classpath:db/scripts/new_user.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/remove_test_user.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldEditWhenUserPayloadIsValid() throws Exception {
        shouldEditAndReturnStatus204WhenPayloadIsValidAndUserIsAuthorized(
            format(EDIT_USER, 3),
            ADMIN,
            VALID_NEW_STATUS_OF_USER_JSON);
    }

    @Test
    void shouldFailEditWhenUserIsUnauthorized() throws Exception {
        shouldFailEditAndReturnStatus402WithErrMsgWhenPayloadIsValidAndUserIsUnauthorized(
            format(EDIT_USER, 3),
            CUSTOMER,
            VALID_NEW_STATUS_OF_USER_JSON,
            ACCESS_DENIED_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/new_user.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/remove_test_user.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldReturnUserQueriedByIdWhenDataFound() throws Exception {
        shouldReturnStatus200WithDataWhenItsFoundAndUserIsAuthorized(
            format(GET_USER_BY_ID, 3),
            ADMIN,
            CUSTOMER_USER_JSON);
    }

    @Test
    void shouldFailRetrievalWhenUserQueriedByIdAndDataNotFound() throws Exception {
        shouldReturnStatus404WithErrMsgWhenDataNotFoundAndUserIsAuthorized(
            format(GET_USER_BY_ID, 404),
            ADMIN,
            NO_DATA_FOUND_JSON);
    }

    @Sql(scripts = "classpath:db/scripts/users.sql", executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:db/scripts/remove_test_user.sql", executionPhase = AFTER_TEST_METHOD)
    @Test
    void shouldReturnUsersQueriedByPageWhenDataFound() throws Exception {
        shouldReturnStatus200WithDataWhenItsFoundAndUserIsAuthorized(
            format(GET_USERS_BY_PAGINATION, 0),
            ADMIN,
            ALL_USERS_JSON);
    }

    @Test
    void shouldFailRetrievalWhenUsersQueriedByPageAndUserIsUnauthorized() throws Exception {
        shouldReturnStatus403WithErrMsgWhenUserIsUnauthorized(
            format(GET_USERS_BY_PAGINATION, 0),
            CUSTOMER,
            ACCESS_DENIED_JSON);
    }

}
