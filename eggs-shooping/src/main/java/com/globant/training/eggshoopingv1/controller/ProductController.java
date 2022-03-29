package com.globant.training.eggshoopingv1.controller;

import com.globant.training.eggshoopingv1.entities.Product;
import com.globant.training.eggshoopingv1.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService service;


    // aca obtengo todos los productos
    @GetMapping(value = "/products/all")
    ResponseEntity<List<Product>> findAllProducts(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllProducts());
    }

    // aca obtengo un solo producto
    @GetMapping(value = "/products/id/", params = "id")
    ResponseEntity<Product> findOneProductById(@RequestParam(name = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getOneProductById(id));
    }

    // aca obtengo todos los productos que contengan un nombre
    // %20 y + son traducidos como espacios cuando los ingreso por query, aunque segun lei se recomienda %20
    @GetMapping(value = "/products/name/", params = "name")
    ResponseEntity<List<Product>> findAllProductByName(@RequestParam(name = "name") String name){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllProductsByName(name));
    }

    // aca añado un producto nuevo
    @PostMapping(value = "/products/new")
    ResponseEntity<Product> createNewProduct(@RequestBody Product newProduct){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNewProduct(newProduct));
    }

    // aca modifico un producto que ya existia o creo uno nuevo si no existe
    @PutMapping(value = "/products/modify/")
    ResponseEntity<Product> modifyAnExistentProductOrCreateANewOneIfItDoesntExist(@RequestBody Product newProduct){
        return ResponseEntity.status(HttpStatus.OK).body(service.modifyAnExistentProductOrCreateNewOneIfItDoesntExist(
                newProduct));
    }

    // aca elimino un producto
    @DeleteMapping(value = "/products/delete/", params = "id")
    ResponseEntity<?> deleteProductById(@RequestParam(name = "id") Long id){
        service.deleteProductById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
