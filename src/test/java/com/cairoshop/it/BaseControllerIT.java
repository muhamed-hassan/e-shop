package com.cairoshop.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.MySQLContainer;

import com.cairoshop.configs.security.Constants;
import com.cairoshop.it.configs.TestRestTemplateConfig;
import com.cairoshop.it.models.Credentials;
import com.cairoshop.it.models.HttpRequest;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseControllerIT {

    private static final String SEED_MAPPINGS_DIR = "seed/";
    private static final String EXPECTED_MAPPINGS_DIR = "expected/";
    private static final String ERRORS_MAPPINGS_DIR = "errors/";
    private static final String BASE_MAPPINGS_DIR = "__files/";

    private static final String UPDATE_SCRIPTS_DIR = "db/scripts/";
    private static final String NEW_DATA_SQL = "new_data.sql";
    private static final String RESET_DATA_SQL = "reset_data.sql";

    private static final String AUTHENTICATE_ENDPOINT = "/authenticate";

    private static MySQLContainer mySQLContainer;

    @Autowired
    private TestRestTemplateConfig testRestTemplateConfig;

    @BeforeAll
    public static void initTestDB() {
        if (mySQLContainer == null) {
            mySQLContainer = new MySQLContainer("mysql:8.0.20")
                .withDatabaseName("integration-tests-db")
                .withUsername("username")
                .withPassword("password");
            mySQLContainer.start();
            System.setProperty("DB_URL", mySQLContainer.getJdbcUrl());
            System.setProperty("DB_USER", mySQLContainer.getUsername());
            System.setProperty("DB_PASSWORD", mySQLContainer.getPassword());
        }
    }

    @BeforeEach
    public void populateNewData() {
        updateTestDB(NEW_DATA_SQL);
    }

    @AfterEach
    public void resetData() {
        updateTestDB(RESET_DATA_SQL);
    }

    private void updateTestDB(String script) {
        try {
            String[] scriptLines = Files.readAllLines(pathFrom(UPDATE_SCRIPTS_DIR + script))
                                        .stream()
                                        .collect(Collectors.joining())
                                        .split(";");
            Connection connection = mySQLContainer.createConnection("");
            Statement statement = connection.createStatement();
            for (int cursor = 0; cursor < scriptLines.length; cursor++) {
                statement.addBatch(scriptLines[cursor]);
                if (cursor % 50 == 0) {
                    statement.executeBatch();
                }
            }
            statement.executeBatch();
        } catch (URISyntaxException| SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String readJsonFrom(String responseLocation) {
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

    protected String authenticate(Credentials credentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, credentials.getUsername());
        formData.add(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, credentials.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        ResponseEntity<Void> response = testRestTemplateConfig.getTestRestTemplate()
                                                .exchange(AUTHENTICATE_ENDPOINT, POST, request, Void.class);

        return response.getHeaders().get(Constants.AUTHORIZATION_HEADER_KEY).get(0);
    }

    protected <T, R> ResponseEntity<R> doRequest(HttpRequest<T> httpRequest, Class<R> responseType) {
        HttpEntity<T> requestEntity;
        if (httpRequest.getRequestBody() != null) {
            requestEntity = new HttpEntity<>(httpRequest.getRequestBody(), httpRequest.getHeaders());
        } else {
            requestEntity = new HttpEntity<>(httpRequest.getHeaders());
        }
        return testRestTemplateConfig.getTestRestTemplate()
                        .exchange(httpRequest.getUri(), httpRequest.getHttpMethod(), requestEntity, responseType);
    }

    protected void testAddingDataWithValidPayloadAndAuthorizedUser(String uri, Credentials credentials, String requestBodyFile) {
        String jwtToken = authenticate(credentials);
        String requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        ResponseEntity<Void> response = doRequest(HttpRequest.from(uri, headers, POST, requestBody), Void.class);

        assertEquals(CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertTrue(response.getHeaders().getLocation().getPath().matches("^\\/[a-z]+\\/[1-9][0-9]*$"));
    }

    protected void testAddingDataWithInvalidPayloadAndAuthorizedUser(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        String jwtToken = authenticate(credentials);
        String requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        String expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, POST, requestBody), String.class);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        JSONAssert.assertEquals(expectedErrorMsg, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    protected void testAddingDataWithValidPayloadAndUnauthorizedUser(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        String jwtToken = authenticate(credentials);
        String requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        String expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, POST, requestBody), String.class);

        assertEquals(FORBIDDEN, response.getStatusCode());
        JSONAssert.assertEquals(expectedErrorMsg, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    protected void testDataModificationWithValidPayloadAndAuthorizedUser(String uri, Credentials credentials, String requestBodyFile) {
        String jwtToken = authenticate(credentials);
        String requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        ResponseEntity<Void> response = doRequest(HttpRequest.from(uri, headers, PATCH, requestBody), Void.class);

        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    protected void testDataModificationWithInvalidPayloadAndAuthorizedUser(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        String jwtToken = authenticate(credentials);
        String requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        String expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, PATCH, requestBody), String.class);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        JSONAssert.assertEquals(expectedErrorMsg, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    protected void testDataModificationWithValidPayloadAndUnauthorizedUser(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile)
            throws Exception {
        String jwtToken = authenticate(credentials);
        String requestBody = readJsonFrom(SEED_MAPPINGS_DIR + requestBodyFile);
        String expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, PATCH, requestBody), String.class);

        assertEquals(FORBIDDEN, response.getStatusCode());
        JSONAssert.assertEquals(expectedErrorMsg, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    protected void testDataRetrievalToReturnExistedDataUsingAuthorizedUser(String uri, Credentials credentials, String expectedResponseFile)
            throws Exception {
        String jwtToken = authenticate(credentials);
        String expectedResponse = readJsonFrom(EXPECTED_MAPPINGS_DIR + expectedResponseFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, GET), String.class);

        assertEquals(OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    protected void testDataRetrievalUsingUnauthorizedUser(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        String jwtToken = authenticate(credentials);
        String expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, GET), String.class);

        assertEquals(FORBIDDEN, response.getStatusCode());
        JSONAssert.assertEquals(expectedErrorMsg, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    protected void testDataRetrievalForNonExistedDataUsingAuthorizedUser(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        String jwtToken = authenticate(credentials);
        String expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, GET), String.class);

        assertEquals(NOT_FOUND, response.getStatusCode());
        JSONAssert.assertEquals(expectedErrorMsg, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    protected void testDataRemovalOfExistingDataUsingAuthorizedUser(String uri, Credentials credentials) {
        String jwtToken = authenticate(credentials);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        ResponseEntity<Void> response = doRequest(HttpRequest.from(uri, headers, DELETE), Void.class);

        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    protected void testDataRemovalUsingUnauthorizedUser(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        String jwtToken = authenticate(credentials);
        String expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, DELETE), String.class);

        assertEquals(FORBIDDEN, response.getStatusCode());
        JSONAssert.assertEquals(expectedErrorMsg, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    protected void testDataRemovalOfNonExistingDataUsingAuthorizedUser(String uri, Credentials credentials, String errorMsgFile)
            throws Exception {
        String jwtToken = authenticate(credentials);
        String expectedErrorMsg = readJsonFrom(ERRORS_MAPPINGS_DIR + errorMsgFile);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, DELETE), String.class);

        assertEquals(NOT_FOUND, response.getStatusCode());
        JSONAssert.assertEquals(expectedErrorMsg, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

}
