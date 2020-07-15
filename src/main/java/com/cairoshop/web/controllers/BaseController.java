package com.cairoshop.web.controllers;

import java.net.HttpURLConnection;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cairoshop.service.BaseService;
import com.cairoshop.web.dtos.SavedItemsDTO;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseController<D, B>
            extends BaseCommonController<D> {

    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_CREATED, message = "Succeeded in adding the resource"),
        @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid request"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> add(@Valid @RequestBody D detailedDto) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .build(((BaseService) getService()).add(detailedDto)))
                                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Succeeded in deleting the resource"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        ((BaseService) getService()).removeById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Succeeded in fetching data"),
        @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Data not found"),
        @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid request"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"start-position", "sort-by", "sort-direction"})
    public SavedItemsDTO<B> getAllByPage(
        @RequestParam("start-position") @Min(value = 0, message = "min start-position is 0") int startPosition,
        @RequestParam("sort-by") @NotBlank(message = "sort-by field is required")
        @Pattern(regexp = "^(id|name|price|quantity)$", message = "allowed values for sort-by are id, name, price and quantity")
        String sortBy,
        @RequestParam("sort-direction") @Pattern(regexp = "^(ASC|DESC)$", message = "allowed values for sort-direction are DESC or ASC")
        String sortDirection) {
        return ((BaseService) getService()).getAll(startPosition, sortBy, sortDirection);
    }

}
