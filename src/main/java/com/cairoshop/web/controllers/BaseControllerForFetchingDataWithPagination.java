package com.cairoshop.web.controllers;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cairoshop.service.BaseService;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseControllerForFetchingDataWithPagination<SDTO> {

    private BaseService baseService;

    protected void setService(BaseService baseService) {
        this.baseService = baseService;
    }

    protected BaseService getService() {
        return baseService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"start-position", "sort-by", "sort-direction"})
    public ResponseEntity<List<SDTO>> getAllByPagination(
                @RequestParam("start-position") @Min(value = 0, message = "min start-position is 0") int startPosition,
                @RequestParam("sort-by") @NotBlank(message = "sort-by field is required") String sortBy,
                @RequestParam("sort-direction") @Pattern(regexp = "^(ASC|DESC)$", message = "allowed values for sort-direction are DESC or ASC") String sortDirection) {
        return ResponseEntity.ok(baseService.getAll(startPosition, sortBy, sortDirection));
    }

}
