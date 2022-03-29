package com.globant.training.eggshoopingv1.services;

import com.globant.training.eggshoopingv1.entities.Product;
import com.globant.training.eggshoopingv1.exception.ProductAlreadyExistsException;
import com.globant.training.eggshoopingv1.exception.ProductNotFoundException;
import com.globant.training.eggshoopingv1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepository repository;


    // obtener todos los productos
    public List<Product> getAllProducts(){
        return repository.findAll();
    }

    // obtener un producto por id
    public Product getOneProductById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    // obtener un producto por nombre
    public List<Product> getAllProductsByName(String name){
        return repository.findAllByNameContains(name);
    }

    // crear un nuevo producto
    public Product createNewProduct(Product newProduct){
        if(!(repository.existsByName(newProduct.getName()))){ // pregunto si el username ya existe
            if(!(repository.existsById(newProduct.getId()))){ // pregunto si el id ya existe
                return repository.save(newProduct);
            }else{
                throw new ProductAlreadyExistsException(newProduct.getId()); // si ya existe un cliente con el mismo id
            }
        }else{
            throw new ProductAlreadyExistsException(newProduct.getName()); // si ya existe un cliente con el mismo username
        }
    }

    // modifico un producto existente o creo uno nuevo si no existe
    public Product modifyAnExistentProductOrCreateNewOneIfItDoesntExist(Product newProduct){
        return (repository.findById(newProduct.getId())
                .map(product -> {
                    if(!(newProduct.getName() == null)){
                        product.setName(newProduct.getName());
                    }
                    if(!(newProduct.getDescription() == null)){
                        product.setDescription(newProduct.getDescription());
                    }
                    if(!(newProduct.getPrice() < 0.0f)){
                        product.setPrice(newProduct.getPrice());
                    }
                    if(!(newProduct.getAmmountInStock() < 0)){
                        product.setAmmountInStock(newProduct.getAmmountInStock());
                    }
                    return repository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setId(newProduct.getId());
                    if(!(repository.existsByName(newProduct.getName()))){ // pregunto si el username ya existe
                        return repository.save(newProduct);
                    }else{
                        throw new ProductAlreadyExistsException(newProduct.getName()); // si ya existe un cliente con el mismo username
                    }
                })
        );
    }

    public void deleteProductById(Long id){
        /*
        if(repository.existsById(id)){

            repository.deleteById(id);
        } else{
            throw new ProductNotFoundException(id);
        }
        */
        try{
            repository.deleteById(id);
        } catch (Exception ex){
            throw new ProductNotFoundException(id);
        }
        // por alguna razon el codigo de arriba no funciona aca, pero en todos los otros eso mismo si funciona
    }
}