package com.cairoshop.it;

import java.net.HttpURLConnection;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cairoshop.it.models.Credentials;
import com.cairoshop.persistence.entities.Category;
import com.cairoshop.service.BaseService;
import com.cairoshop.service.CategoryService;
import com.cairoshop.web.dtos.NewCategoryDTO;
import com.cairoshop.web.dtos.SavedBriefCategoryDTO;
import com.cairoshop.web.dtos.SavedDetailedCategoryDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
//@RestController
//@RequestMapping("categories")
//@Validated
public class CategoryControllerIT extends BaseProductClassificationControllerIT /*extends BaseProductClassificationControllerIT<NewCategoryDTO, SavedDetailedCategoryDTO, SavedBriefCategoryDTO, Category>*/ {

//    @Autowired
//    private CategoryService categoryService;
//
//    @PostConstruct
//    public void injectRefs() {
//        setService(categoryService);
//    }

    // admin or customer
    public void testGetAll_WhenDataExists_ThenReturn200WithData() {
        testGetAll_WhenDataExists_ThenReturn200WithData(null,null,null);
    }

    // admin
    public void testEdit_WhenPayloadIsValid_ThenReturn204() {
        testEdit_WhenPayloadIsValid_ThenReturn204(null,null,null);
    }

    //admin
    protected void testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation() {
        testAdd_WhenPayloadIsValid_ThenSaveItAndReturn201WithItsLocation(null,null,null,null);
    }

    //admin
    protected void testRemove_WhenItemExists_ThenRemoveItAndReturn204() {
        testRemove_WhenItemExists_ThenRemoveItAndReturn204(null,null);
    }

    //admin or customer
    protected void testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData() throws Exception {
        testGetAllItemsByPagination_WhenDataExists_ThenReturn200WithData(null,null,null);
    }

    //admin or customer
    protected void testGetById_WhenDataFound_ThenReturn200AndData() throws Exception {
        testGetById_WhenDataFound_ThenReturn200AndData(null,null,null);
    }


}
