package com.cairoshop.services;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cairoshop.persistence.entities.Category;
import com.cairoshop.persistence.entities.Product;
import com.cairoshop.persistence.entities.ProductSortableFields;
import com.cairoshop.persistence.entities.Vendor;
import com.cairoshop.persistence.repositories.CategoryRepository;
import com.cairoshop.persistence.repositories.ProductRepository;
import com.cairoshop.persistence.repositories.ProductSortableFieldsRepository;
import com.cairoshop.persistence.repositories.VendorRepository;
import com.cairoshop.service.ProductService;
import com.cairoshop.service.exceptions.DataIntegrityViolatedException;
import com.cairoshop.service.exceptions.NoResultException;
import com.cairoshop.service.impl.ProductServiceImpl;
import com.cairoshop.web.dtos.ProductInBriefDTO;
import com.cairoshop.web.dtos.ProductInDetailDTO;

/* **************************************************************************
 * Developed by : Muhamed Hassan                                            *
 * LinkedIn     : https://www.linkedin.com/in/muhamed-hassan/               *
 * GitHub       : https://github.com/muhamed-hassan                         *
 * ************************************************************************ */
class ProductServiceTest
        extends BaseServiceTest<Product, ProductInDetailDTO, ProductInBriefDTO> {

    private VendorRepository vendorRepository;

    private CategoryRepository categoryRepository;

    private ProductSortableFieldsRepository productSortableFieldsRepository;

    ProductServiceTest() {
        super(Product.class);
    }

    @BeforeEach
    void injectRefs() {
        vendorRepository = mock(VendorRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        productSortableFieldsRepository = mock(ProductSortableFieldsRepository.class);
        var productService = new ProductServiceImpl(mock(ProductRepository.class),
                                                    vendorRepository,
                                                    categoryRepository,
                                                    productSortableFieldsRepository);
        injectRefs(productService);
    }


    @Test
    void testAdd_WhenDataIsValid_ThenSaveAndReturnNewId()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var productEntity = mock(Product.class);
        when(getRepository().save(any(Product.class)))
            .thenReturn(productEntity);
        var expectedIdOfCreatedProduct = 1;
        when(productEntity.getId())
            .thenReturn(expectedIdOfCreatedProduct);
        var productInDetailDTO = ProductInDetailDTO.builder()
                                                                        .name("some name")
                                                                        .price(0.5)
                                                                        .quantity(20)
                                                                        .description("some description")
                                                                        .vendorId(1)
                                                                        .categoryId(1)
                                                                        .imageUploaded(false)
                                                                    .build();

        var actualIdOfCreatedProduct = getService().add(productInDetailDTO);

        assertEquals(expectedIdOfCreatedProduct, actualIdOfCreatedProduct);
    }

    @Test
    void testAdd_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() {
        var productInDetailDTO = ProductInDetailDTO.builder()
                                                                        .name("some duplicated name")
                                                                        .price(0.5)
                                                                        .quantity(20)
                                                                        .description("some description")
                                                                        .vendorId(1)
                                                                        .categoryId(1)
                                                                        .imageUploaded(false)
                                                                    .build();
        doThrow(DataIntegrityViolationException.class)
            .when(getRepository()).save(any(Product.class));

        assertThrows(DataIntegrityViolatedException.class,
            () -> getService().add(productInDetailDTO));
    }

    @Test
    void testEdit_WhenDataIsValid_ThenSave() {
        var id = 1;
        var productInDetailDTO = ProductInDetailDTO.builder()
                                                                        .name("some name")
                                                                        .price(0.5)
                                                                        .quantity(20)
                                                                        .description("some description")
                                                                        .vendorId(1)
                                                                        .categoryId(1)
                                                                        .imageUploaded(false)
                                                                    .build();
        var productEntity = mock(Product.class);
        when(getRepository().getOne(id))
            .thenReturn(productEntity);
        var vendorEntity = mock(Vendor.class);
        when(vendorRepository.getOne(productInDetailDTO.getVendorId()))
            .thenReturn(vendorEntity);
        var categoryEntity = mock(Category.class);
        when(categoryRepository.getOne(productInDetailDTO.getCategoryId()))
            .thenReturn(categoryEntity);
        when(getRepository().saveAndFlush(productEntity))
            .thenReturn(productEntity);

        ((ProductService) getService()).edit(id, productInDetailDTO);

        verify(getRepository()).getOne(id);
        verify(vendorRepository).getOne(productInDetailDTO.getVendorId());
        verify(categoryRepository).getOne(productInDetailDTO.getCategoryId());
        verify(getRepository()).saveAndFlush(productEntity);
    }

    @Test
    void testEdit_WhenDbConstraintViolated_ThenThrowDataIntegrityViolatedException() {
        var id = 1;
        var productInDetailDTO = ProductInDetailDTO.builder()
                                                                       .name("some duplicated name")
                                                                       .price(0.5)
                                                                       .quantity(20)
                                                                       .description("some description")
                                                                       .vendorId(1)
                                                                       .categoryId(1)
                                                                       .imageUploaded(false)
                                                                   .build();
        var productEntity = mock(Product.class);
        when(getRepository().getOne(id))
            .thenReturn(productEntity);
        var vendorEntity = mock(Vendor.class);
        when(vendorRepository.getOne(productInDetailDTO.getVendorId()))
            .thenReturn(vendorEntity);
        var categoryEntity = mock(Category.class);
        when(categoryRepository.getOne(productInDetailDTO.getCategoryId()))
            .thenReturn(categoryEntity);
        when(getRepository().saveAndFlush(any(Product.class)))
            .thenThrow(DataIntegrityViolationException.class);

        assertThrows(DataIntegrityViolatedException.class,
            () -> ((ProductService) getService()).edit(id, productInDetailDTO));
    }

    @Test
    void testEditImage_WhenProductFound_ThenUpdateIt() {
        var uploadedImageStream = new byte[2048];
        var idOfSavedProduct = 1;
        var productEntity = mock(Product.class);
        when(getRepository().getOne(idOfSavedProduct))
            .thenReturn(productEntity);
        when(getRepository().save(productEntity))
            .thenReturn(productEntity);

        ((ProductService) getService()).edit(idOfSavedProduct, uploadedImageStream);

        verify(getRepository()).getOne(idOfSavedProduct);
        verify(getRepository()).save(productEntity);
    }


    @Test
    void testGetById_WhenDataFound_ThenReturnIt()
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        var productInDetailDTO = Optional.of(ProductInDetailDTO.builder()
                                                                                            .name("some name")
                                                                                            .price(0.5)
                                                                                            .quantity(20)
                                                                                            .description("some description")
                                                                                            .vendorId(1)
                                                                                            .categoryId(1)
                                                                                            .imageUploaded(false)
                                                                                        .build());
        var getters = List.of("getName", "getPrice", "getQuantity", "getDescription", "getCategoryId", "getVendorId", "isImageUploaded");
        testGetById_WhenDataFound_ThenReturnIt(1, productInDetailDTO, getters);
    }

    @Test
    void testGetImage_WhenProductFound_ThenReturnItsImage() {
        var expectedImageStream = new byte[2048];
        var idOfSavedProduct = 1;
        when(((ProductRepository) getRepository()).findImageByProductId(idOfSavedProduct))
            .thenReturn(expectedImageStream);

        var actualImageStream = ((ProductService) getService()).getImage(idOfSavedProduct);

        assertArrayEquals(expectedImageStream, actualImageStream);
    }

    @Test
    void testGetImage_WhenImageIsNotUploadedYet_ThenThrowNoResultException() {
        byte[] expectedImageStream = null;
        var idOfSavedProduct = 1;
        when(((ProductRepository) getRepository()).findImageByProductId(any(int.class)))
            .thenReturn(expectedImageStream);

        assertThrows(NoResultException.class,
            () -> ((ProductService) getService()).getImage(idOfSavedProduct));
    }

    @Test
    void testGetAllByPage_WhenDataFound_ThenReturnIt() {
        var productInBriefDTO = new ProductInBriefDTO(1, "IPhone X");
        testGetAllByPage_WhenDataFound_ThenReturnIt(productInBriefDTO);
    }

    @Test
    void testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException() {
        super.testGetAllByPage_WhenDataNotFound_ThenThrowNoResultException();
    }

    @Test
    void testSearchByProductName_WhenDataFound_ThenReturnIt() {
        var productInBriefDTO = new ProductInBriefDTO(1, "IPhone X");
        var page = mock(Page.class);
        when(page.isEmpty())
            .thenReturn(false);
        when(page.getContent())
            .thenReturn(List.of(productInBriefDTO));
        when(page.getTotalElements())
            .thenReturn(1L);
        when(((ProductRepository) getRepository()).findAllByActiveAndNameLike(any(boolean.class), anyString(), any(Pageable.class), any(Class.class)))
            .thenReturn(page);

        var actualResult =
            ((ProductService) getService()).searchByProductName("IPhone", 0, "name", "ASC");

        assertEquals(Long.valueOf(page.getTotalElements()).intValue(), actualResult.getAllSavedItemsCount());
        assertIterableEquals(page.getContent(), actualResult.getItems());
    }

    @Test
    void testSearchByProductName_WhenDataNotFound_ThenThrowNoResultException() {
        var page = mock(Page.class);
        when(page.isEmpty())
            .thenReturn(true);
        when(((ProductRepository) getRepository()).findAllByActiveAndNameLike(any(boolean.class), anyString(), any(Pageable.class), any(Class.class)))
            .thenReturn(page);

        assertThrows(NoResultException.class,
                     () -> ((ProductService) getService()).searchByProductName("XXX", 0, "name", "ASC"));
    }

    @Test
    void testGetSortableFields_WhenDataFound_ThenReturnIt() {
        var expectedSortableList = List.of("name", "price", "quantity");
        when(productSortableFieldsRepository.findAll())
            .thenReturn(List.of(new ProductSortableFields("name"),
                                    new ProductSortableFields("price"),
                                    new ProductSortableFields("quantity")));

        var actualSortableList = ((ProductService) getService()).getSortableFields();

        assertIterableEquals(expectedSortableList, actualSortableList);
    }

    @Test
    void testGetSortableFields_WhenDataNotFound_ThenThrowNoResultException() {
        List<ProductSortableFields> expectedSortableList = Collections.emptyList();
        when(productSortableFieldsRepository.findAll())
            .thenReturn(expectedSortableList);

        assertThrows(NoResultException.class,
            () -> ((ProductService) getService()).getSortableFields());
    }

    @Test
    void testRemoveById_WhenDataFound_ThenReturnIt() {
        super.testRemoveById_WhenDataFound_ThenRemoveIt(1);
    }

}
