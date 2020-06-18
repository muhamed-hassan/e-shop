package com.cairoshop.web.controllers;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cairoshop.service.ProductService;
import com.cairoshop.web.dtos.NewProductDTO;
import com.cairoshop.web.dtos.SavedBriefProductDTO;
import com.cairoshop.web.dtos.SavedDetailedProductDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addProduct(@RequestBody NewProductDTO newProductDTO) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                                                                    .path("/{id}")
                                                                    .build(productService.add(newProductDTO)))
                                .build();
    }

    @PostMapping(path = "{id}")
    public ResponseEntity<Void> uploadImageOfProduct(@PathVariable int id, @RequestParam("file") MultipartFile file) throws IOException {
        productService.edit(id, file.getBytes());
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> downloadImageOfProduct(@PathVariable int id) {
        return ResponseEntity.ok(productService.getImage(id));
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SavedDetailedProductDTO> getProductById(@PathVariable int id) {
        return ResponseEntity.ok(productService.getInDetailById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"start-position","sort-by", "sort-direction"})
    public ResponseEntity<List<SavedBriefProductDTO>> getProducts(@RequestParam("start-position") int startPosition,
                                                                    @RequestParam("sort-by") String sortBy,
                                                                    @RequestParam("sort-direction") String sortDirection) {
        return ResponseEntity.ok(productService.getAll(startPosition, sortBy, sortDirection));
    }

    @PatchMapping
    public ResponseEntity<Void> updateProduct(@RequestBody SavedDetailedProductDTO savedDetailedProductDTO) {
        productService.edit(savedDetailedProductDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.removeById(id);
        return ResponseEntity.noContent().build();
    }

}