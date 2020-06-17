package com.cairoshop.web.controllers;

import java.net.URI;
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

import com.cairoshop.service.CategoryService;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.SavedCategoryDTO;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addCategory(@RequestBody NewCategoryDTO newCategoryDTO) {
        return ResponseEntity.created(ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .build(categoryService.add(newCategoryDTO)))
            .build();
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SavedCategoryDTO> getCategoryById(@PathVariable int id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SavedCategoryDTO>> getCategories() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"start-position","sort-by", "sort-direction"})
    public ResponseEntity<List<SavedCategoryDTO>> getCategories(@RequestParam("start-position") int startPosition,
        @RequestParam("sort-by") String sortBy, @RequestParam("sort-direction") String sortDirection) {
        return ResponseEntity.ok(categoryService.getAll(startPosition, sortBy, sortDirection));
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable int id, @RequestBody Map<String, Object> fields) {
        fields.put("id", id);
        categoryService.edit(fields);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        categoryService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}
