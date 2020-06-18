package com.cairoshop.web.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class UserController {
    
    @Autowired
    private UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"start-position","sort-by", "sort-direction"})
    public ResponseEntity<List<SavedCustomerDTO>> getCustomers(@RequestParam("start-position") int startPosition,
                                                                @RequestParam("sort-by") String sortBy,
                                                                @RequestParam("sort-direction") String sortDirection) {
        return ResponseEntity.ok(userService.getAll(startPosition, sortBy, sortDirection));
    }

    @PatchMapping
    public ResponseEntity<Void> changeCustomerState(@RequestBody Map<String, Object> fields) {
        userService.edit(fields);
        return ResponseEntity.noContent().build();
    }
    
}
