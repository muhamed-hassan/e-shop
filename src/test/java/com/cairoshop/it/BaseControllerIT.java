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
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var formData = new LinkedMultiValueMap<String, String>();
        formData.add(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, credentials.getUsername());
        formData.add(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, credentials.getPassword());

        var resultActions = mockMvc.perform(post(AUTHENTICATE_ENDPOINT)
                                                            .params(formData)
                                                            .headers(headers));

        return resultActions.andReturn()
                                .getResponse()
                                .getHeader(Constants.AUTHORIZATION_HEADER_KEY)
                                .replace("Bearer ", "");
    }

    void testAddingDataWithValidPayloadAndAuthorizedUser(String uri, Credentials credentials, String requestBodyFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(post(uri)
                                                            .content(requestBody)
                                                            .headers(headers));

        resultActions.andExpect(status().isCreated())
                        .andExpect(header().exists("Location"));
    }

    void testAddingDataWithInvalidPayloadAndAuthorizedUser(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(post(uri)
                                                            .content(requestBody)
                                                            .headers(headers));

        resultActions.andExpect(status().isBadRequest())
                        .andExpect(content().json(expectedErrorMsg));
    }

    void testAddingDataWithValidPayloadAndUnauthorizedUser(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(post(uri)
                                                            .content(requestBody)
                                                            .headers(headers));

        resultActions.andExpect(status().isForbidden())
                        .andExpect(content().json(expectedErrorMsg));
    }

    void testDataModificationWithValidPayloadAndAuthorizedUser(String uri, Credentials credentials, String requestBodyFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(patch(uri)
                                                            .content(requestBody)
                                                            .headers(headers));

        resultActions.andExpect(status().isNoContent());
    }

    void testDataModificationWithInvalidPayloadAndAuthorizedUser(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(patch(uri)
                                                            .content(requestBody)
                                                            .headers(headers));

        resultActions.andExpect(status().isBadRequest())
                        .andExpect(content().json(expectedErrorMsg));
    }

    void testDataModificationWithValidPayloadAndUnauthorizedUser(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(patch(uri)
                                                            .content(requestBody)
                                                            .headers(headers));

        resultActions.andExpect(status().isForbidden())
                        .andExpect(content().json(expectedErrorMsg));
    }

    void testDataRetrievalToReturnExistedDataUsingAuthorizedUser(String uri, Credentials credentials, String expectedResponseFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var expectedResponse = readJsonFrom(EXPECTED_MAPPINGS_DIR + expectedResponseFile);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(get(uri).headers(headers));

        resultActions.andExpect(status().isOk())
                        .andExpect(content().json(expectedResponse));
    }

    void testDataRetrievalUsingUnauthorizedUser(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(get(uri).headers(headers));

        resultActions.andExpect(status().isForbidden())
                        .andExpect(content().json(expectedErrorMsg));
    }

    void testDataRetrievalForNonExistedDataUsingAuthorizedUser(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(get(uri).headers(headers));

        resultActions.andExpect(status().isNotFound())
                        .andExpect(content().json(expectedErrorMsg));
    }

    void testDataRemovalOfExistingDataUsingAuthorizedUser(String uri, Credentials credentials)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(delete(uri).headers(headers));

        resultActions.andExpect(status().isNoContent());
    }

    void testDataRemovalUsingUnauthorizedUser(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        var headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(delete(uri).headers(headers));

        resultActions.andExpect(status().isForbidden())
                        .andExpect(content().json(expectedErrorMsg));
    }

    void testDataRemovalOfNonExistingDataUsingAuthorizedUser(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        var jwtToken = authenticate(credentials);
        var expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        var headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        var resultActions = mockMvc.perform(delete(uri).headers(headers));

        resultActions.andExpect(status().isNotFound())
                        .andExpect(content().json(expectedErrorMsg));
    }

}
