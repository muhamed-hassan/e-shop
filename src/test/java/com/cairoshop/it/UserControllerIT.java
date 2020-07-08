package com.cairoshop.it;

import java.net.HttpURLConnection;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cairoshop.persistence.entities.User;
import com.cairoshop.service.UserService;
import com.cairoshop.web.dtos.SavedBriefCustomerDTO;
import com.cairoshop.web.dtos.SavedDetailedCustomerDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
//@RestController
//@RequestMapping("users")
//@Validated
public class UserControllerIT extends BaseCommonControllerIT /*extends BaseCommonControllerIT<SavedDetailedCustomerDTO, SavedBriefCustomerDTO, User>*/ {
//
//    @Autowired
//    private UserService userService;
//
//    @PostConstruct
//    public void injectRefs() {
//        setService(userService);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @ApiResponses(value = {
//        @ApiResponse(code = HttpURLConnection.HTTP_NO_CONTENT, message = "Succeeded in updating the resource"),
//        @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid request"),
//        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
//        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
//    })
//    @PatchMapping(path = "{id}")
//    public ResponseEntity<Void> changeStatus(@PathVariable int id, @RequestBody Map<String, String> newStatus) {
//        userService.edit(id, newStatus);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @ApiResponses(value = {
//        @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Succeeded in fetching data"),
//        @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Data not found"),
//        @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Invalid request"),
//        @ApiResponse(code = HttpURLConnection.HTTP_INTERNAL_ERROR, message = "Internal server error"),
//        @ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized")
//    })
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = {"start-position", "sort-by", "sort-direction"})
//    public ResponseEntity<SavedItemsDTO<SavedBriefCustomerDTO>> getAllCustomersByPagination(
//        @RequestParam("start-position") @Min(value = 0, message = "min start-position is 0") int startPosition,
//        @RequestParam("sort-by") @NotBlank(message = "sort-by field is required") String sortBy,
//        @RequestParam("sort-direction") @Pattern(regexp = "^(ASC|DESC)$", message = "allowed values for sort-direction are DESC or ASC") String sortDirection) {
//        return ResponseEntity.ok(userService.getAllCustomers(startPosition, sortBy, sortDirection));
//    }

    // admin
    public void testEdit_WhenPayloadIsValid_ThenReturn204() {
        testEdit_WhenPayloadIsValid_ThenReturn204(null,null,null);
    }

    //admin or customer
    protected void testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData() throws Exception {
        testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData(null,null,null);
    }

    //admin
    protected void testGetById_WhenDataFound_ThenReturn200AndData() throws Exception {
        testGetById_WhenDataFound_ThenReturn200AndData(null,null,null);
    }

}
