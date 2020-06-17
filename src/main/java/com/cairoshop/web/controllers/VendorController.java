package com.cairoshop.web.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cairoshop.service.VendorService;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.NewVendorDTO;
import com.cairoshop.web.dtos.SavedVendorDTO;

@RestController
@RequestMapping("vendors")
public class VendorController {
    
    @Autowired
    private VendorService vendorService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addVendor(@RequestBody NewVendorDTO newVendorDTO) {
        return ResponseEntity.created(ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .build(vendorService.add(newVendorDTO)))
            .build();
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SavedVendorDTO> getVendorById(@PathVariable int id) {
        return ResponseEntity.ok(vendorService.getById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SavedVendorDTO>> getVendors() {
        return ResponseEntity.ok(vendorService.getAll());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"start-position","sort-by", "sort-direction"})
    public ResponseEntity<List<SavedVendorDTO>> getVendors(@RequestParam("start-position") int startPosition,
        @RequestParam("sort-by") String sortBy, @RequestParam("sort-direction") String sortDirection) {
        return ResponseEntity.ok(vendorService.getAll(startPosition, sortBy, sortDirection));
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<Void> updateVendor(@PathVariable int id, @RequestBody Map<String, Object> fields) {
        fields.put("id", id);
        vendorService.edit(fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable int id) {
        vendorService.removeById(id);
        return ResponseEntity.noContent().build();
    }
    
}
