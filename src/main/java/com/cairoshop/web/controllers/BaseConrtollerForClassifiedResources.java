package com.cairoshop.web.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cairoshop.service.BaseClassificationService;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class BaseConrtollerForClassifiedResources<NDTO, SDTO> extends BaseControllerForDeletingResource<NDTO, SDTO> {

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SDTO> getById(@PathVariable int id) {
        return ResponseEntity.ok((SDTO) getService().getById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SDTO>> getAll() {
        return ResponseEntity.ok(((BaseClassificationService) getService()).getAll());
    }

    @PatchMapping
    public ResponseEntity<Void> update(@Valid @RequestBody SDTO sdto) {
        ((BaseClassificationService) getService()).edit(sdto);
        return ResponseEntity.noContent().build();
    }

}
