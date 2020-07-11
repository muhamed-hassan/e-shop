package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.repositories.ProductRepository;
import com.cairoshop.service.impl.ProductServiceImpl;
import com.cairoshop.web.dtos.ProductInBriefDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest
                extends BaseServiceTest<ProductInDetailDTO, ProductInBriefDTO, Product> {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    protected ProductServiceTest() {
        super(Product.class, ProductInDetailDTO.class);
    }

    @BeforeEach
    public void injectRefs() {
        injectRefs(productRepository, productService);
    }

    @Test
    public void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId() {
        ProductInDetailDTO productInDetailDTO = null;//new ProductInDetailDTO("name", 50.5, 20, "description",1, 1, false);
        Product product = mock(Product.class);
        int expectedIdOfCreatedProduct = 1;
        when(product.getId())
            .thenReturn(expectedIdOfCreatedProduct);
//        when(productRepository.save(any(Product.class)))
//            .thenReturn(product);

        int actualIdOfCreatedProduct = productService.add(productInDetailDTO);

        assertEquals(expectedIdOfCreatedProduct, actualIdOfCreatedProduct);
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() throws Exception {
        int id = 1;
        ProductInDetailDTO productInDetailDTO = null;//new ProductInDetailDTO("name", 55.55, 20, "description",1, 1, false);
        int affectedRows = 1;
//        when(productRepository.update(id, productInDetailDTO.getName(),
//                                        productInDetailDTO.getPrice(), productInDetailDTO.getQuantity(),
//                                        productInDetailDTO.getCategoryId(), productInDetailDTO.getVendorId()))
//            .thenReturn(affectedRows);

        productService.edit(id, productInDetailDTO);

//        verify(productRepository, times(1)).update(id, productInDetailDTO.getName(),
//                                                                            productInDetailDTO.getPrice(), productInDetailDTO.getQuantity(),
//                                                                            productInDetailDTO.getCategoryId(), productInDetailDTO.getVendorId());
    }

    @Test
    public void testEditImage_WhenProductFound_ThenUpdateIt() throws Exception {
        byte[] uploadedImageStream = new byte[2048];
        int idOfSavedProduct = 1;
        int affectedRows = 1;
        when(productRepository.update(idOfSavedProduct, uploadedImageStream))
            .thenReturn(affectedRows);

        productService.edit(idOfSavedProduct, uploadedImageStream);

        verify(productRepository, times(1)).update(idOfSavedProduct, uploadedImageStream);
    }

    @Test
    public void testGetById_WhenDataFound_ThenReturnIt() throws Exception {
        ProductInDetailDTO productInDetailDTO = null;//new ProductInDetailDTO("name", 55.55, 20,"description",1, 1,false);
        testGetById_WhenDataFound_ThenReturnIt(productInDetailDTO, List.of("getName", "getPrice", "getQuantity", "getDescription", "getCategoryId", "getVendorId", "isImageUploaded"));
    }

    @Test
    public void testGetImage_WhenProductFound_ThenReturnItsImage() {
        byte[] expectedImageStream = new byte[2048];
        //Optional<SavedImageStream> imageStreamOfSavedProduct = Optional.of(new SavedImageStream(expectedImageStream));
        int idOfSavedProduct = 1;
//        when(productRepository.findImageById(idOfSavedProduct, SavedImageStream.class))
//            .thenReturn(imageStreamOfSavedProduct);

        byte[] actualImageStream = productService.getImage(idOfSavedProduct);

        assertArrayEquals(expectedImageStream, actualImageStream);
    }

    @Test
    public void testGetAllByPage_WhenDataFound_ThenReturnIt() {
        ProductInBriefDTO productInBriefDTO = new ProductInBriefDTO(1, "IPhone X");
        testGetAllByPage_WhenDataFound_ThenReturnIt(productInBriefDTO);
    }

    @Test
    public void testSearchByProductName_WhenDataFound_ThenReturnIt() {
        ProductInBriefDTO productInBriefDTO = new ProductInBriefDTO(1, "IPhone X");
        List<ProductInBriefDTO> page = List.of(productInBriefDTO);
        when(productRepository.search(anyString(), any(int.class), any(int.class), anyString(), anyString()))
            .thenReturn(page);
        when(productRepository.countAllByCriteria(anyString()))
            .thenReturn(1);
        SavedItemsDTO<ProductInBriefDTO> expectedResult = new SavedItemsDTO<>();
        expectedResult.setItems(page);
        expectedResult.setAllSavedItemsCount(1);

        SavedItemsDTO<ProductInBriefDTO> actualResult = productService.searchByProductName("IPhone", 0, "name", "ASC");

        assertEquals(expectedResult.getAllSavedItemsCount(), actualResult.getAllSavedItemsCount());
        assertIterableEquals(expectedResult.getItems(), actualResult.getItems());
    }

    @Test
    public void testGetSortableFields_WhenDataFound_ThenReturnIt() {

        List<String> sortableList = productService.getSortableFields();

        assertIterableEquals(Product.SORTABLE_FIELDS, sortableList);
    }

    @Test
    public void testRemoveById_WhenDataFound_ThenReturnIt() {
        super.testRemoveById_WhenDataFound_ThenRemoveIt();
    }

}
