package com.cairoshop.web.controllers;

import java.net.HttpURLConnection;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cairoshop.service.UserService;
import com.cairoshop.web.dtos.SavedItemsDTO;
import com.cairoshop.web.dtos.UserInBriefDTO;
import com.cairoshop.web.dtos.UserInDetailDTO;
import com.cairoshop.web.dtos.UserStatusDTO;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@RestController
@RequestMapping("users")
@Validated
public class UserController
            extends BaseCommonController<UserInDetailDTO> {
    
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
    @PatchMapping(path = "{id}")
    public ResponseEntity<Void> changeStatus(@PathVariable int id, @RequestBody UserStatusDTO userStatusDTO) {
        userService.edit(id, userStatusDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Succeeded in fetching data"),
        @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Data not found"),
        @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid request"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"start-position", "sort-by", "sort-direction"})
    public ResponseEntity<SavedItemsDTO<UserInBriefDTO>> getAllCustomersByPagination(
        @RequestParam("start-position") @Min(value = 0, message = "min start-position is 0") int startPosition,
        @RequestParam("sort-by") @NotBlank(message = "sort-by field is required") String sortBy,
        @RequestParam("sort-direction") @Pattern(regexp = "^(ASC|DESC)$", message = "allowed values for sort-direction are DESC or ASC") String sortDirection) {
        return ResponseEntity.ok(userService.getAllCustomers(startPosition, sortBy, sortDirection));
    }
    
}
