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
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.repositories.ProductRepository;
import com.cairoshop.service.impl.ProductServiceImpl;
import com.cairoshop.web.dtos.NewProductDTO;
import com.cairoshop.web.dtos.SavedBriefProductDTO;
import com.cairoshop.web.dtos.SavedDetailedProductDTO;
import com.cairoshop.web.dtos.SavedImageStream;
import com.cairoshop.web.dtos.SavedItemsDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan	                                        *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest
                extends BaseServiceTest<SavedDetailedProductDTO, SavedBriefProductDTO, Product> {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    protected ProductServiceTest() {
        super(Product.class, SavedDetailedProductDTO.class);
    }

    @BeforeEach
    public void injectRefs() {
        injectRefs(productRepository, productService);
    }

    @Test
    public void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId() {
        NewProductDTO newProductDTO = new NewProductDTO();
        newProductDTO.setCategoryId(1);
        newProductDTO.setVendorId(1);
        newProductDTO.setDescription("description");
        newProductDTO.setName("name");
        newProductDTO.setPrice(50.5);
        newProductDTO.setQuantity(20);
        Product product = mock(Product.class);
        int expectedIdOfCreatedProduct = 1;
        when(product.getId())
            .thenReturn(expectedIdOfCreatedProduct);
        when(productRepository.save(any(Product.class)))
            .thenReturn(product);

        int actualIdOfCreatedProduct = productService.add(newProductDTO);

        assertEquals(expectedIdOfCreatedProduct, actualIdOfCreatedProduct);
    }

    @Test
    public void testEdit_WhenDataIsValid_ThenSave() throws Exception {
        SavedDetailedProductDTO savedDetailedProductDTO = new SavedDetailedProductDTO(1, "name", 55.55, 20, "description",1, 1, true, false);
        int affectedRows = 1;
        when(productRepository.update(savedDetailedProductDTO.getId(), savedDetailedProductDTO.getName(),
                                        savedDetailedProductDTO.getPrice(), savedDetailedProductDTO.getQuantity(),
                                        savedDetailedProductDTO.getCategoryId(), savedDetailedProductDTO.getVendorId()))
            .thenReturn(affectedRows);

        productService.edit(savedDetailedProductDTO);

        verify(productRepository, times(1)).update(savedDetailedProductDTO.getId(), savedDetailedProductDTO.getName(),
                                                                            savedDetailedProductDTO.getPrice(), savedDetailedProductDTO.getQuantity(),
                                                                            savedDetailedProductDTO.getCategoryId(), savedDetailedProductDTO.getVendorId());
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
        SavedDetailedProductDTO savedDetailedProductDTO = new SavedDetailedProductDTO(1, "name", 55.55, 20,
                                                                                "description",1, 1,
                                                                                    true, false);
        testGetById_WhenDataFound_ThenReturnIt(savedDetailedProductDTO, List.of("getId", "getName", "getPrice", "getQuantity", "getDescription", "getCategoryId", "getVendorId", "isActive", "isImageUploaded"));
    }

    @Test
    public void testGetImage_WhenProductFound_ThenReturnItsImage() {
        byte[] expectedImageStream = new byte[2048];
        Optional<SavedImageStream> imageStreamOfSavedProduct = Optional.of(new SavedImageStream(expectedImageStream));
        int idOfSavedProduct = 1;
        when(productRepository.findImageById(idOfSavedProduct, SavedImageStream.class))
            .thenReturn(imageStreamOfSavedProduct);

        byte[] actualImageStream = productService.getImage(idOfSavedProduct);

        assertArrayEquals(expectedImageStream, actualImageStream);
    }

    @Test
    public void testGetAllByPage_WhenDataFound_ThenReturnIt() {
        SavedBriefProductDTO savedBriefProductDTO = new SavedBriefProductDTO(1, "IPhone X", true);
        testGetAllByPage_WhenDataFound_ThenReturnIt(savedBriefProductDTO);
    }

    @Test
    public void testSearchByProductName_WhenDataFound_ThenReturnIt() {
        SavedBriefProductDTO savedBriefProductDTO = new SavedBriefProductDTO(1, "IPhone X", true);
        List<SavedBriefProductDTO> page = List.of(savedBriefProductDTO);
        when(productRepository.search(anyString(), any(int.class), any(int.class), anyString(), anyString()))
            .thenReturn(page);
        when(productRepository.countAllByCriteria(anyString()))
            .thenReturn(1);
        SavedItemsDTO<SavedBriefProductDTO> expectedResult = new SavedItemsDTO<>();
        expectedResult.setItems(page);
        expectedResult.setAllSavedItemsCount(1);

        SavedItemsDTO<SavedBriefProductDTO> actualResult = productService.searchByProductName("IPhone", 0, "name", "ASC");

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
