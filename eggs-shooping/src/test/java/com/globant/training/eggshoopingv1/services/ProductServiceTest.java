package com.globant.training.eggshoopingv1.services;

import com.globant.training.eggshoopingv1.entities.Product;
import com.globant.training.eggshoopingv1.exception.ProductAlreadyExistsException;
import com.globant.training.eggshoopingv1.exception.ProductNotFoundException;
import com.globant.training.eggshoopingv1.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp(){
        productService = new ProductService(productRepository);
    }

    @Test
    void whenGetAllProductsThenOk(){
        Product productMock1 = Mockito.mock(Product.class);
        Product productMock2 = Mockito.mock(Product.class);
        Product productMock3 = Mockito.mock(Product.class);

        List<Product> listMock = Arrays.asList(productMock1, productMock2, productMock3);

        when(productRepository.findAll()).thenReturn(listMock);

        assertEquals(listMock, productService.getAllProducts());
    }

    @Test
    void whenGetOneProductByIdThenOk(){
        Product productMock = Mockito.mock(Product.class);

        when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));

        assertEquals(productMock, productService.getOneProductById(1L));
    }

    @Test
    void whenGetOneProductByIdThenFail(){
        assertThrows(
                ProductNotFoundException.class,
                () -> {
                    productService.getOneProductById(1L);
                }
                );
    }

    @Test
    void whenGetAllProductsByNameThenOk(){
        // no sirve de nada setear los nombres asi que no si se tiene que hacer de otra forma
        Product productMock1 = Mockito.mock(Product.class);
        Product productMock2 = Mockito.mock(Product.class);
        Product productMock3 = Mockito.mock(Product.class);

        List<Product> listMock = Arrays.asList(productMock1, productMock2, productMock3);

        when(productRepository.findAllByNameContains("Coca Cola")).thenReturn(listMock);

        assertEquals(listMock, productService.getAllProductsByName("Coca Cola"));
    }

    @Test
    void whenCreateNewProductThenOk(){
        Product productMock = Mockito.mock(Product.class);

        when(productMock.getName()).thenReturn("Coca Cola");
        when(productMock.getId()).thenReturn(1L);

        when(productRepository.existsByName("Coca Cola")).thenReturn(false);
        when(productRepository.existsById(1L)).thenReturn(false);
        when(productRepository.save(productMock)).thenReturn(productMock);

        assertEquals(productMock, productService.createNewProduct(productMock));
    }

    @Test
    void whenCreateNewProductThenFailByNameAlreadyExists(){
        Product productMock = Mockito.mock(Product.class);

        when(productMock.getName()).thenReturn("Coca Cola");
        when(productMock.getId()).thenReturn(1L);

        when(productRepository.existsByName("Coca Cola")).thenReturn(true);
        when(productRepository.existsById(1L)).thenReturn(false);
        when(productRepository.save(productMock)).thenReturn(productMock);

        assertThrows(
                ProductAlreadyExistsException.class,
                () -> {
                    productService.createNewProduct(productMock);
                }
        );
    }

    @Test
    void whenCreateNewProductThenFailByIdAlreadyExists(){
        Product productMock = Mockito.mock(Product.class);

        when(productMock.getName()).thenReturn("Coca Cola");
        when(productMock.getId()).thenReturn(1L);

        when(productRepository.existsByName("Coca Cola")).thenReturn(false);
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.save(productMock)).thenReturn(productMock);

        assertThrows(
                ProductAlreadyExistsException.class,
                () -> {
                    productService.createNewProduct(productMock);
                }
        );
    }

    @Test
    void whenCreateNewProductThenFailByEmptyField(){
        // ?
    }

    @Test
    void whenModifyAnExistentProductOrCreateNewOneIfItDoesntExistThenModify(){
        Product productExistent = Mockito.mock(Product.class);
        Product productModify = Mockito.mock(Product.class);

        when(productModify.getName()).thenReturn("Coca Cola de 2,5L");
        when(productModify.getDescription()).thenReturn("Descripcion");
        when(productModify.getPrice()).thenReturn(25.0f);
        when(productModify.getAmmountInStock()).thenReturn(24);

        when(productRepository.findById(1L)).thenReturn(Optional.of(productExistent));
        when(productRepository.save(productExistent)).thenReturn(productExistent);

        assertEquals(productExistent, productService.modifyAnExistentProductOrCreateNewOneIfItDoesntExist(productModify));
    }

    @Test
    void whenModifyAnExistentProductOrCreateNewOneIfItDoesntExistThenCreate(){
        Product productModify = Mockito.mock(Product.class);

        when(productRepository.save(productModify)).thenReturn(productModify);

        assertEquals(productModify, productService.modifyAnExistentProductOrCreateNewOneIfItDoesntExist(productModify));
    }

    @Test
    void whenModifyAnExistentProductOrCreateNewOneIfItDoesntExistThenFailCreateByEmptyField(){
        // ?
    }

    @Test
    void whenDeleteByIdThenOk(){
        // ?
    }

    @Test
    void whenDeleteByIdThenFailById(){
        // ?
    }
}