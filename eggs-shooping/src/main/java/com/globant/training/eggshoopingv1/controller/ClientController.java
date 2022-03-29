package com.globant.training.eggshoopingv1.controller;

import com.globant.training.eggshoopingv1.entities.Client;
import com.globant.training.eggshoopingv1.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {
    @Autowired
    private final ClientService service;

    // buscar como indicarle a Spring que tiene que instanciar

    // aca obtengo todos los clientes
    @GetMapping(value = "/clients/all")
    ResponseEntity<List<Client>> findAllClients(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllClients());
    }

    // aca obtengo un solo cliente
    @GetMapping(value = "/clients/id/", params = "id")
    ResponseEntity<Client> findOneClientById(@RequestParam(name = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getOneClientById(id));
    }

    // aca obtengo todos los usuarios por nombre
    @GetMapping(value = "/clients/firstname/", params = "firstname")
    ResponseEntity<List<Client>> findAllClientsByFirstName(@RequestParam(name = "firstname") String firstname){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllClientsByName(firstname));
    }

    // aca obtengo todos los usuarios por username
    @GetMapping(value = "/clients/username/", params = "username")
    ResponseEntity<Client> findOneClientByUserName(@RequestParam(name = "username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(service.getOneClientByUserName(username));
    }

    // aca añado un cliente nuevo
    // por alguna razon Post lo tengo que ejecutar desde Postman porque si no interpreta que le estoy enviando un tipo
    // x-www-form-urlencoded, y no un json, parece ser un problema con RequestBody pero ninguna solucion me funciona de
    // momento
    @PostMapping(value = "/clients/new")
    ResponseEntity<Client> createNewClient(@RequestBody Client newClient){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNewClient(newClient));
    }

    // aca modifico un cliente ya existente o creo uno nuevo si no existe
    @PutMapping(value = "/clients/modify")
    ResponseEntity<Client> modifyAnExistentClientOrCreateANewOneIfItDoesntExist(@RequestBody Client newClient){
        return ResponseEntity.status(HttpStatus.OK).body(service.modifyAnExistentClientOrCreateNewOneIfItDoesntExist
                (newClient));
    }

    // aca elimino un cliente
    @DeleteMapping(value = "/clients/delete/", params = "id")
    ResponseEntity<?> deleteClientById(@RequestParam(name = "id") Long id) {
        service.deleteClientById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}