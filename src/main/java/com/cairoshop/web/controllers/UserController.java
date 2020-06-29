package com.cairoshop.web.controllers;

import java.net.HttpURLConnection;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.service.UserService;
import com.cairoshop.web.dtos.NewCustomerStateDTO;
import com.cairoshop.web.dtos.SavedBriefCustomerDTO;
import com.cairoshop.web.dtos.SavedDetailedCustomerDTO;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@RestController
@RequestMapping("users")
@Validated
public class UserController extends BaseCommonController<SavedDetailedCustomerDTO, SavedBriefCustomerDTO, User> {
    
    @Autowired
    private UserService userService;

    @PostConstruct
    public void injectRefs() {
        setService(userService);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Succeeded in updating the resource"),
        @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid request"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @PatchMapping
    public ResponseEntity<Void> update(@Valid @RequestBody NewCustomerStateDTO newCustomerStateDTO) {
        userService.edit(newCustomerStateDTO);
        return ResponseEntity.noContent().build();
    }
    
}
