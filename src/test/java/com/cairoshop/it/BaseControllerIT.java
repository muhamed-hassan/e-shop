package com.cairoshop.it;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.testcontainers.containers.MySQLContainer;

import com.cairoshop.configs.security.Constants;
import com.cairoshop.it.models.Credentials;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BaseControllerIT {

    private static final String SEED_MAPPINGS_DIR = "seed/";
    private static final String EXPECTED_MAPPINGS_DIR = "expected/";
    private static final String ERRORS_MAPPINGS_DIR = "errors/";
    private static final String BASE_MAPPINGS_DIR = "__files/";

    private static final String AUTHENTICATE_ENDPOINT = "/authenticate";

    private static MySQLContainer mySQLContainer;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void initTestDB() {
        mySQLContainer = new MySQLContainer("mysql:8.0.20")
            .withDatabaseName("integration-tests-db")
            .withUsername("username")
            .withPassword("password");
        mySQLContainer.start();
        System.setProperty("DB_URL", mySQLContainer.getJdbcUrl());
        System.setProperty("DB_USER", mySQLContainer.getUsername());
        System.setProperty("DB_PASSWORD", mySQLContainer.getPassword());
    }

    void shouldAddAndReturnStatus201AndLocationOfCreatedItemWhenPayloadIsValidAndUserIsAuthorized(String uri, Credentials credentials, String requestBodyFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);

        var resultActions = mockMvc.perform(post(uri)
                                                            .content(requestBody)
                                                            .headers(prepareHeaders(MediaType.APPLICATION_JSON, jwtToken)));

        expect(resultActions, status().isCreated(), header().exists("Location"));
    }

    void shouldFailAddAndReturnStatus400WithErrMsgWhenPayloadIsInvalidAndUserIsAuthorized(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);

        var resultActions = mockMvc.perform(post(uri)
                                                            .content(requestBody)
                                                            .headers(prepareHeaders(MediaType.APPLICATION_JSON, jwtToken)));

        expect(resultActions, status().isBadRequest(), content().json(expectedErrorMsg));
    }

    void shouldFailAddAndReturnStatus403WithErrMsgWhenPayloadIsValidAndUserIsUnauthorized(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);

        var resultActions = mockMvc.perform(post(uri)
                                                            .content(requestBody)
                                                            .headers(prepareHeaders(MediaType.APPLICATION_JSON, jwtToken)));

        expect(resultActions, status().isForbidden(), content().json(expectedErrorMsg));
    }

    void shouldEditAndReturnStatus204WhenPayloadIsValidAndUserIsAuthorized(String uri, Credentials credentials, String requestBodyFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);

        var resultActions = mockMvc.perform(patch(uri)
                                                            .content(requestBody)
                                                            .headers(prepareHeaders(MediaType.APPLICATION_JSON, jwtToken)));

        expect(resultActions, status().isNoContent());
    }

    void shouldFailEditAndReturnStatus400WithErrMsgWhenPayloadIsInvalidAndUserIsAuthorized(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);

        var resultActions = mockMvc.perform(patch(uri)
                                                            .content(requestBody)
                                                            .headers(prepareHeaders(MediaType.APPLICATION_JSON, jwtToken)));

        expect(resultActions, status().isBadRequest(), content().json(expectedErrorMsg));
    }

    void shouldFailEditAndReturnStatus402WithErrMsgWhenPayloadIsValidAndUserIsUnauthorized(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);

        var resultActions = mockMvc.perform(patch(uri)
                                                            .content(requestBody)
                                                            .headers(prepareHeaders(MediaType.APPLICATION_JSON, jwtToken)));

        expect(resultActions, status().isForbidden(), content().json(expectedErrorMsg));
    }

    void shouldReturnStatus200WithDataWhenItsFoundAndUserIsAuthorized(String uri, Credentials credentials, String expectedResponseFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var expectedResponse = readJsonFrom(EXPECTED_MAPPINGS_DIR + expectedResponseFile);

        var resultActions = mockMvc.perform(get(uri).headers(prepareHeaders(MediaType.APPLICATION_JSON, jwtToken)));

        expect(resultActions, status().isOk(), content().json(expectedResponse));
    }

    void shouldReturnStatus403WithErrMsgWhenUserIsUnauthorized(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);

        var resultActions = mockMvc.perform(get(uri).headers(prepareHeaders(MediaType.APPLICATION_JSON, jwtToken)));

        expect(resultActions, status().isForbidden(), content().json(expectedErrorMsg));
    }

    void shouldReturnStatus404WithErrMsgWhenDataNotFoundAndUserIsAuthorized(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);

        var resultActions = mockMvc.perform(get(uri).headers(prepareHeaders(MediaType.APPLICATION_JSON, jwtToken)));

        expect(resultActions, status().isNotFound(), content().json(expectedErrorMsg));
    }

    void shouldRemoveItemAndReturnStatus204WhenDataFoundAndUserIsAuthorized(String uri, Credentials credentials)
            throws Exception {
        var jwtToken = authenticate(credentials);

        var resultActions = mockMvc.perform(delete(uri).headers(prepareHeaders(null, jwtToken)));

        expect(resultActions, status().isNoContent());
    }

    void shouldFailDataRemovalAndReturnStatus403WithErrMsgWhenUserIsUnauthorized(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);

        var resultActions = mockMvc.perform(delete(uri).headers(prepareHeaders(null, jwtToken)));

        expect(resultActions, status().isForbidden(), content().json(expectedErrorMsg));
    }

    void shouldFailDataRemovalAndReturnStatus404WithErrMsgWhenDataNotFoundAndUserIsAuthorized(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);

        var resultActions = mockMvc.perform(delete(uri).headers(prepareHeaders(null, jwtToken)));

        expect(resultActions, status().isNotFound(), content().json(expectedErrorMsg));
    }

    private String readJsonFrom(String responseLocation) {
        try {
            return Files.readAllLines(pathFrom(BASE_MAPPINGS_DIR + responseLocation),
                Charset.forName(StandardCharsets.UTF_8.name()))
                .stream()
                .collect(Collectors.joining());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private Path pathFrom(String location)
            throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(location).toURI());
    }

    private String authenticate(Credentials credentials) throws Exception {

        var formData = new LinkedMultiValueMap<String, String>();
        formData.add(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, credentials.getUsername());
        formData.add(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, credentials.getPassword());

        var resultActions = mockMvc.perform(post(AUTHENTICATE_ENDPOINT)
                                                        .params(formData)
                                                        .headers(prepareHeaders(MediaType.APPLICATION_FORM_URLENCODED, null)));

        return resultActions.andReturn()
            .getResponse()
            .getHeader(Constants.AUTHORIZATION_HEADER_KEY)
            .replace("Bearer ", "");
    }

    private void expect(ResultActions resultActions, ResultMatcher... resultMatchers) throws Exception {
        for (var resultMatcher : resultMatchers) {
            resultActions.andExpect(resultMatcher);
        }
    }

    private HttpHeaders prepareHeaders(MediaType contentType, String bearerAuth) {
        var headers = new HttpHeaders();
        headers.setContentType(contentType);
        headers.setBearerAuth(bearerAuth);
        return headers;
    }

}
