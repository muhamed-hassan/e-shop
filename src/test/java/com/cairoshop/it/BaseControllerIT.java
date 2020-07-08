package com.cairoshop.it;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.cairoshop.configs.security.Constants;
import com.cairoshop.it.helpers.KeysOfHttpHeaders;
import com.cairoshop.it.models.Credentials;
import com.cairoshop.it.models.HttpRequest;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseControllerIT extends BaseCommonControllerIT {

    protected void testAddingDataWithValidPayloadAndAuthorizedUser(String uri, Credentials credentials, String requestBodyFile) throws Exception {
        String requestBody = readJsonFrom(getSeedMappingsDir() + requestBodyFile);
        String jwtToken = authenticate(credentials);
        HttpHeaders headers = new HttpHeaders();
        headers.add(KeysOfHttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(Constants.AUTHORIZATION_HEADER_KEY, Constants.AUTHORIZATION_HEADER_VALUE_PREFIX + jwtToken);

        ResponseEntity<Void> response = doRequest(HttpRequest.from(uri, headers, HttpMethod.POST, requestBody), Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertTrue(response.getHeaders().getLocation().getPath().matches("^\\/[a-z]+\\/[1-9][0-9]*$"));
    }

    protected void testAddingDataWithInvalidPayloadAndAuthorizedUser(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile) throws Exception {
        String requestBody = readJsonFrom(getSeedMappingsDir() + requestBodyFile);
        String expectedErrorMsg = readJsonFrom(getErrorsMappingsDir() + errorMsgFile);
        String jwtToken = authenticate(credentials);
        HttpHeaders headers = new HttpHeaders();
        headers.add(KeysOfHttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(Constants.AUTHORIZATION_HEADER_KEY, Constants.AUTHORIZATION_HEADER_VALUE_PREFIX + jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, HttpMethod.POST, requestBody), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JSONAssert.assertEquals(expectedErrorMsg, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    protected void testAddingDataWithValidPayloadAndUnauthorizedUser(String uri, Credentials credentials, String requestBodyFile, String errorMsgFile) throws Exception {
        String requestBody = readJsonFrom(getSeedMappingsDir() + requestBodyFile);
        String expectedErrorMsg = readJsonFrom(getErrorsMappingsDir() + errorMsgFile);
        String jwtToken = authenticate(credentials);
        HttpHeaders headers = new HttpHeaders();
        headers.add(KeysOfHttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(Constants.AUTHORIZATION_HEADER_KEY, Constants.AUTHORIZATION_HEADER_VALUE_PREFIX + jwtToken);

        ResponseEntity<String> response = doRequest(HttpRequest.from(uri, headers, HttpMethod.POST, requestBody), String.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        JSONAssert.assertEquals(expectedErrorMsg, response.getBody(), JSONCompareMode.NON_EXTENSIBLE);
    }

    protected void testDataRemoval(String uri, Credentials credentials) {
        String jwtToken = authenticate(credentials);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.AUTHORIZATION_HEADER_KEY, Constants.AUTHORIZATION_HEADER_VALUE_PREFIX + jwtToken);

        ResponseEntity<Void> response = doRequest(HttpRequest.from(uri, headers, HttpMethod.DELETE), Void.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
