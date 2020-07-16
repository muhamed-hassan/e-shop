package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.entities.ProductSortableFields;
import com.cairoshop.persistence.repositories.ProductRepository;
import com.cairoshop.persistence.repositories.ProductSortableFieldsRepository;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.DataNotUpdatedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.service.impl.ProductServiceImpl;
import com.cairoshop.web.dtos.ProductInBriefDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
public class ProductServiceTest
           /* extends BaseServiceTest<Product, ProductInDetailDTO, ProductInBriefDTO>*/ {

    /*@Mock
    private ProductRepository productRepository;

    @Mock
    private ProductSortableFieldsRepository productSortableFieldsRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    protected ProductServiceTest() {
        super(Product.class, ProductInDetailDTO.class, ProductInBriefDTO.class);
    }

    @BeforeEach
    public void injectRefs() {
        injectRefs(productRepository, productService);
    }

    @Test
    public void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId() throws Exception {
        ProductInDetailDTO productInDetailDTO = new ProductInDetailDTO(0.5, 20, "description", 1, 1, false, "name");
        int expectedIdOfCreatedProduct = 1;
//        when(productRepository.save(any(Product.class)))
//            .thenReturn(expectedIdOfCreatedProduct);

        int actualIdOfCreatedProduct = productService.add(productInDetailDTO);

        assertEquals(expectedIdOfCreatedProduct, actualIdOfCreatedProduct);
    }

    @Test
    public void testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() throws Exception {
        ProductInDetailDTO productInDetailDTO = new ProductInDetailDTO(0.5, 20, "description", 1, 1, false, "name");
        doThrow(DataIntegrityViolationException.class)
            .when(productRepository).save(any(Product.class));

        assertThrows(DataIntegrityViolatedException.class,
            () -> productService.add(productInDetailDTO));
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() throws Exception {
        int id = 1;
        ProductInDetailDTO productInDetailDTO = new ProductInDetailDTO(0.5, 20, "description", 1, 1, false, "name");
        int affectedRows = 1;
//        when(productRepository.update(id, productInDetailDTO))
//            .thenReturn(affectedRows);

        productService.edit(id, productInDetailDTO);

//        verify(productRepository).update(id, productInDetailDTO);
    }

    @Test
    public void testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() throws Exception {
        int id = 1;
        ProductInDetailDTO productInDetailDTO = new ProductInDetailDTO(0.5, 20, "description", 1, 1, false, "name");
//        doThrow(DataIntegrityViolationException.class)
//            .when(productRepository).update(any(int.class), any(getDetailedDtoClass()));

        assertThrows(DataIntegrityViolatedException.class,
            () -> productService.edit(id, productInDetailDTO));
    }

    @Test
    public void testEdit_WhenRecordNotUpdated_ThenThrowDataNotUpdatedException() throws Exception {
        int id = 1;
        ProductInDetailDTO productInDetailDTO = new ProductInDetailDTO(0.5, 20, "description", 1, 1, false, "name");
        int affectedRows = 0;
//        when(productRepository.update(any(int.class), any(getDetailedDtoClass())))
//            .thenReturn(affectedRows);

        assertThrows(DataNotUpdatedException.class,
            () -> productService.edit(id, productInDetailDTO));
    }

    @Test
    public void testEditImage_WhenProductFound_ThenUpdateIt() throws Exception {
        byte[] uploadedImageStream = new byte[2048];
        int idOfSavedProduct = 1;
        int affectedRows = 1;
//        when(productRepository.update(idOfSavedProduct, uploadedImageStream))
//            .thenReturn(affectedRows);

        productService.edit(idOfSavedProduct, uploadedImageStream);

//        verify(productRepository).update(idOfSavedProduct, uploadedImageStream);
    }

    @Test
    public void testEditImage_WhenRecordNotUpdated_ThenUpdateIt() {
        byte[] uploadedImageStream = new byte[2048];
        int idOfSavedProduct = 1;
        int affectedRows = 0;
//        when(productRepository.update(any(int.class), any(byte[].class)))
//            .thenReturn(affectedRows);

        assertThrows(DataNotUpdatedException.class,
            () -> productService.edit(idOfSavedProduct, uploadedImageStream));
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturnIt() throws Exception {
        ProductInDetailDTO productInDetailDTO = new ProductInDetailDTO(0.5, 20, "description", 1, 1, false, "name");
//        testGetById_WhenDataFound_ThenReturnIt(1, productInDetailDTO,
//            List.of("getName", "getPrice", "getQuantity", "getDescription", "getCategoryId", "getVendorId", "isImageUploaded"));
    }

    @Test
    public void testGetById_WhenDataNotFound_ThenThrowNoResultException() {
        testGetById_WhenDataNotFound_ThenThrowNoResultException(404);
    }

    @Test
    public void testGetImage_WhenProductFound_ThenReturnItsImage() {
        byte[] expectedImageStream = new byte[2048];
        Optional<byte[]> imageStreamOfSavedProduct = Optional.of(expectedImageStream);
        int idOfSavedProduct = 1;
//        when(productRepository.findImageByProductId(idOfSavedProduct))
//            .thenReturn(imageStreamOfSavedProduct);

        byte[] actualImageStream = productService.getImage(idOfSavedProduct);

        assertArrayEquals(expectedImageStream, actualImageStream);
    }

    @Test
    public void testGetImage_WhenImageIsNotUploadedYet_ThenThrowNoResultException() {
        int idOfSavedProduct = 1;
//        doThrow(EmptyResultDataAccessException.class)
//            .when(productRepository).findImageByProductId(any(int.class));

        assertThrows(NoResultException.class,
            () -> productService.getImage(idOfSavedProduct));
    }

    @Test
    public void testGetAllByPage_WhenDataFound_ThenReturnIt() {
        ProductInBriefDTO productInBriefDTO = new ProductInBriefDTO(1, "IPhone X");
        testGetAllByPage_WhenDataFound_ThenReturnIt(productInBriefDTO);
    }

    @Test
    public void testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException() {
        super.testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException();
    }

    @Test
    public void testSearchByProductName_WhenDataFound_ThenReturnIt() {
        ProductInBriefDTO productInBriefDTO = new ProductInBriefDTO(1, "IPhone X");
        List<ProductInBriefDTO> page = List.of(productInBriefDTO);
//        when(productRepository.search(anyString(), any(int.class), any(int.class), anyString(), anyString()))
//            .thenReturn(page);
//        when(productRepository.countAllByCriteria(anyString()))
//            .thenReturn(1);
        SavedItemsDTO<ProductInBriefDTO> expectedResult = new SavedItemsDTO<>(page, 1);

        SavedItemsDTO<ProductInBriefDTO> actualResult = productService.searchByProductName("IPhone", 0, "name", "ASC");

        assertEquals(expectedResult.getAllSavedItemsCount(), actualResult.getAllSavedItemsCount());
        assertIterableEquals(expectedResult.getItems(), actualResult.getItems());
    }

    @Test
    public void testSearchByProductName_WhenDataNotFound_ThenThrowNoResultException() {
        List<ProductInBriefDTO> page = Collections.emptyList();
//        when(productRepository.search(anyString(), any(int.class), any(int.class), anyString(), anyString()))
//            .thenReturn(page);

        assertThrows(NoResultException.class,
            () -> productService.searchByProductName("IPhone", 0, "name", "ASC"));
    }

    @Test
    public void testGetSortableFields_WhenDataFound_ThenReturnIt() {
        List<String> expectedSortableList = List.of("name", "price", "quantity");
        when(productSortableFieldsRepository.findAll())
            .thenReturn(List.of(new ProductSortableFields("name"),
                new ProductSortableFields("price"),
                new ProductSortableFields("quantity")));

        List<String> actualSortableList = productService.getSortableFields();

        assertIterableEquals(expectedSortableList, actualSortableList);
    }

    @Test
    public void testGetSortableFields_WhenDataNotFound_ThenThrowNoResultException() {
        List<ProductSortableFields> expectedSortableList = Collections.emptyList();
        when(productSortableFieldsRepository.findAll())
            .thenReturn(expectedSortableList);

        assertThrows(NoResultException.class,
            () -> productService.getSortableFields());
    }

    @Test
    public void testRemoveById_WhenDataFound_ThenReturnIt() {
        super.testRemoveById_WhenDataFound_ThenRemoveIt(1);
    }

    @Test
    public void testRemoveById_WhenDataNotFound_ThenThrowDataNotDeletedException() {
        super.testRemoveById_WhenDataNotFound_ThenThrowDataNotDeletedException(404);
    }*/

}
