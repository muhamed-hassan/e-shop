package com.cairoshop.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.testcontainers.containers.MySQLContainer;

import com.cairoshop.configs.security.Constants;
import com.cairoshop.it.models.Credentials;
import com.cairoshop.it.models.HttpRequest;
import com.cairoshop.persistence.repositories.BaseCommonRepository;
import com.cairoshop.service.BaseCommonService;
import com.cairoshop.service.impl.BaseCommonServiceImpl;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BaseCommonControllerIT/*<SDDTO, SBDTO, T>*/ {

    protected static final String SEED_MAPPINGS_DIR = "seed/";
    protected static final String EXPECTED_MAPPINGS_DIR = "expected/";
    protected static final String ERRORS_MAPPINGS_DIR = "errors/";

    private static final String BASE_MAPPINGS_DIR = "__files/";

    private static MySQLContainer mySQLContainer = null;

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void init() {
        mySQLContainer = new MySQLContainer("mysql:8.0.20")
            .withDatabaseName("integration-tests-db")
            .withUsername("username")
            .withPassword("password");
        mySQLContainer.start();
        System.setProperty("DB_URL", mySQLContainer.getJdbcUrl());
        System.setProperty("DB_USER", mySQLContainer.getUsername());
        System.setProperty("DB_PASSWORD", mySQLContainer.getPassword());
    }

    @PostConstruct
    public void iniTestRestTemplate() {
        testRestTemplate = new TestRestTemplate(restTemplateBuilder.rootUri("http://localhost:" + port));
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    protected String readJsonFrom(String responseLocation) throws Exception {
        return Files.readAllLines(Paths.get(ClassLoader.getSystemResource(BASE_MAPPINGS_DIR + responseLocation).toURI()),
            Charset.forName(StandardCharsets.UTF_8.name()))
            .stream()
            .collect(Collectors.joining());
    }

    protected String authenticate(Credentials credentials) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, credentials.getUsername());
        formData.add(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, credentials.getPassword());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        ResponseEntity<Void> response = testRestTemplate.exchange("/authenticate", HttpMethod.POST, request, Void.class);

        return response.getHeaders().get(Constants.AUTHORIZATION_HEADER_KEY).get(0);
    }

    protected <T, R> ResponseEntity<R> doRequest(HttpRequest<T> httpRequest, Class<R> responseType) {
        HttpEntity<T> requestEntity = null;
        if (httpRequest.getRequestBody() != null) {
            requestEntity = new HttpEntity<>(httpRequest.getRequestBody(), httpRequest.getHeaders());
        } else {
            requestEntity = new HttpEntity<>(httpRequest.getHeaders());
        }

        return testRestTemplate.exchange(httpRequest.getUri(), httpRequest.getHttpMethod(), requestEntity, responseType);
    }

    ///////////////////////////////////////////////////////////////////

    protected void testGetById_WhenDataFound_ThenReturn200AndData(String uri, String expectedResponseFile, Credentials credentials) throws Exception {
        String expectedResponse = readJsonFrom(EXPECTED_MAPPINGS_DIR + expectedResponseFile);
        String jwtToken = authenticate(credentials);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add(Constants.AUTHORIZATION_HEADER_KEY, Constants.AUTHORIZATION_HEADER_VALUE_PREFIX + jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, HttpMethod.GET), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    //admin or customer
    protected void testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData(String uri, String expectedResponseFile, Credentials credentials) throws Exception {
        String expectedResponse = readJsonFrom(EXPECTED_MAPPINGS_DIR + expectedResponseFile);
        String jwtToken = authenticate(credentials);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add(Constants.AUTHORIZATION_HEADER_KEY, Constants.AUTHORIZATION_HEADER_VALUE_PREFIX + jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, HttpMethod.GET), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    // admin
    protected void testEdit_WhenPayloadIsValid_ThenReturn204(String uri, Credentials credentials, String requestBody) {
        String jwtToken = authenticate(credentials);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add(Constants.AUTHORIZATION_HEADER_KEY, Constants.AUTHORIZATION_HEADER_VALUE_PREFIX + jwtToken);

        ResponseEntity<Void> response = doRequest(HttpRequest.from(uri, headers, HttpMethod.PATCH, requestBody), Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }


    /*@PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Succeeded in fetching data"),
        @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Data not found"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SDDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(baseCommonService.getById(id));
    }*/

}
