package com.cairoshop.web.controllers;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseControllerForAddingNewResource<NDTO, SDTO> extends BaseControllerForFetchingDataWithPagination<SDTO> {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> add(@Valid @RequestBody NDTO newResourceDTO) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                                                                    .path("/{id}")
                                                                    .build(getService().add(newResourceDTO)))
                                .build();
    }

}
