package com.cairoshop.web.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.cairoshop.web.dtos.ProductInBriefDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@RestController
@RequestMapping("products")
public class ProductController
            extends BaseController<ProductInDetailDTO, ProductInBriefDTO> {

    @Autowired
    public ProductController(ProductService productService) {
        super(productService);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Succeeded in updating the resource"),
        @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid request"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @PostMapping("{id}/images")
    public ResponseEntity<Void> uploadImageOfProduct(@PathVariable int id, @NotNull @RequestParam("file") MultipartFile file)
            throws IOException {
        ((ProductService) getService()).edit(id, file.getBytes());
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Succeeded in image downloading"),
        @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Image not found"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @GetMapping(path = "{id}/images", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> downloadImageOfProduct(@PathVariable int id) {
        return ResponseEntity.ok(((ProductService) getService()).getImage(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Succeeded in updating the resource"),
        @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid request"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @PatchMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @Valid @RequestBody ProductInDetailDTO productInDetailDTO) {
        ((ProductService) getService()).edit(id, productInDetailDTO);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Succeeded in fetching data"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @GetMapping(path = "sortable-fields", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getSortableFields() {
        return ResponseEntity.ok(((ProductService) getService()).getSortableFields());
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @ApiResponses(value = {
        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Succeeded in fetching data"),
        @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Data not found"),
        @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid request"),
        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"name", "start-position", "sort-by", "sort-direction"})
    public ResponseEntity<SavedItemsDTO<ProductInBriefDTO>> searchByName(
        @RequestParam("name") @NotBlank(message = "name is required") String name,
        @RequestParam("start-position") @Min(value = 0, message = "min start-position is 0") int startPosition,
        @RequestParam("sort-by") @NotBlank(message = "sort-by field is required")
            @Pattern(regexp = "^(name|price|quantity)$", message = "allowed values for sort-by are name, price and quantity")
            String sortBy,
        @RequestParam("sort-direction") @Pattern(regexp = "^(ASC|DESC)$", message = "allowed values for sort-direction are DESC or ASC")
            String sortDirection) {
        return ResponseEntity.ok(((ProductService) getService()).searchByProductName(name, startPosition, sortBy, sortDirection));
    }

}
