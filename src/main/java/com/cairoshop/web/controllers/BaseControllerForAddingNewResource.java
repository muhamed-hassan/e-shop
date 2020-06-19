package com.cairoshop.web.controllers;

import java.net.HttpURLConnection;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseControllerForAddingNewResource<NDTO, SDTO> extends BaseControllerForFetchingDataWithPagination<SDTO> {

    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "Succeeded in adding the resource"),
        @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid request"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> add(@Valid @RequestBody NDTO newResourceDTO) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                                                                    .path("/{id}")
                                                                    .build(getService().add(newResourceDTO)))
                                .build();
    }

}
