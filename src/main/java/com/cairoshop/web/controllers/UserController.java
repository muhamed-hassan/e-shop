package com.cairoshop.web.controllers;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cairoshop.service.UserService;
import com.cairoshop.web.dtos.SavedCustomerDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@RestController
@RequestMapping("users")
@Validated
public class UserController extends BaseControllerForFetchingDataWithPagination<SavedCustomerDTO> {
    
    @Autowired
    private UserService userService;

    @PostConstruct
    public void injectRefs() {
        setService(userService);
    }

    @PatchMapping
    public ResponseEntity<Void> changeCustomerState(@Valid @RequestBody SavedCustomerDTO savedCustomerDTO) {
        userService.edit(savedCustomerDTO);
        return ResponseEntity.noContent().build();
    }
    
}
