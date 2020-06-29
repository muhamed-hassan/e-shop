package com.cairoshop.web.controllers;

import java.net.HttpURLConnection;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cairoshop.service.BaseCommonService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseCommonController<SDDTO, SBDTO, T> {

    private BaseCommonService<SDDTO, SBDTO, T> baseCommonService;

    protected void setService(BaseCommonService<SDDTO, SBDTO, T> baseCommonService) {
        this.baseCommonService = baseCommonService;
    }

    protected BaseCommonService<SDDTO, SBDTO, T> getService() {
        return baseCommonService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Succeeded in fetching data"),
        @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Data not found"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SDDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok(baseCommonService.getById(id));
    }

}
