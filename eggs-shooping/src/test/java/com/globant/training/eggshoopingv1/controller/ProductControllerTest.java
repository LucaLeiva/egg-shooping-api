package com.globant.training.eggshoopingv1.controller;

import com.globant.training.eggshoopingv1.entities.Product;
import com.globant.training.eggshoopingv1.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductControllerTest {
    public static final Logger log = LoggerFactory.getLogger(ProductControllerTest.class);

    @MockBean
    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp(){
        productController = new ProductController(productService);
    }

    @Test
    void whenFindAllProductsThenOk(){
        Product productMock1 = Mockito.mock(Product.class);
        Product productMock2 = Mockito.mock(Product.class);
        Product productMock3 = Mockito.mock(Product.class);

        List<Product> listMock = Arrays.asList(productMock1, productMock2, productMock3);

        when(productService.getAllProducts()).thenReturn(listMock);

        ResponseEntity<List<Product>> expected = ResponseEntity.status(HttpStatus.OK).body(listMock);

        assertEquals(expected, productController.findAllProducts());
    }

    @Test
    void whenFindOneProductByIdThenOk(){
        Product productMock = Mockito.mock(Product.class);

        when(productService.getOneProductById(1L)).thenReturn(productMock);

        ResponseEntity<Product> expected = ResponseEntity.status(HttpStatus.OK).body(productMock);

        assertEquals(expected, productController.findOneProductById(1L));
    }

    @Test
    void whenFindOneProductByNameThenOk(){
        Product productMock1 = Mockito.mock(Product.class);
        Product productMock2 = Mockito.mock(Product.class);
        Product productMock3 = Mockito.mock(Product.class);

        List<Product> listMock = Arrays.asList(productMock1, productMock2, productMock3);

        when(productService.getAllProductsByName("Coca Cola")).thenReturn(listMock);

        ResponseEntity<List<Product>> expected = ResponseEntity.status(HttpStatus.OK).body(listMock);

        assertEquals(expected, productController.findAllProductByName("Coca Cola"));
    }

    @Test
    void whenCreateNewProductThenOk(){
        Product productMock = Mockito.mock(Product.class);

        when(productService.createNewProduct(productMock)).thenReturn(productMock);

        ResponseEntity<Product> expected = ResponseEntity.status(HttpStatus.CREATED).body(productMock);

        assertEquals(expected, productController.createNewProduct(productMock));
    }

    @Test
    void whenModifyAnExistentProductOrCreateANewOneIfItDoesntExist(){
        Product productMock = Mockito.mock(Product.class);

        when(productService.modifyAnExistentProductOrCreateNewOneIfItDoesntExist(productMock)).thenReturn(productMock);

        ResponseEntity<Product> expected = ResponseEntity.status(HttpStatus.OK).body(productMock);

        assertEquals(expected, productController.modifyAnExistentProductOrCreateANewOneIfItDoesntExist(productMock));
    }

    @Test
    void whenDeleteProductById(){
        ResponseEntity<Product> expected = ResponseEntity.status(HttpStatus.OK).build();

        assertEquals(expected, productController.deleteProductById(1L));
    }
}