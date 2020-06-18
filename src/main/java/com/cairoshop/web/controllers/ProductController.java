package com.cairoshop.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
@Validated
public class ProductController extends BaseControllerForDeletingResource<NewProductDTO, SavedBriefProductDTO> {

    @Autowired
    private ProductService productService;
    @PostConstruct
    public void injectRefs() {
        setService(productService);
    }

    @PostMapping(path = "{id}")
    public ResponseEntity<Void> uploadImageOfProduct(@PathVariable int id, @NotNull @RequestParam("file") MultipartFile file) throws IOException {
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

    @PatchMapping
    public ResponseEntity<Void> updateProduct(@Valid @RequestBody SavedDetailedProductDTO savedDetailedProductDTO) {
        productService.edit(savedDetailedProductDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "sortable-fields", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getSortableFields() {
        return ResponseEntity.ok(productService.getSortableFields());
    }

}
