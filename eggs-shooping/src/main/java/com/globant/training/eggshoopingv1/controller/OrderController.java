package com.globant.training.eggshoopingv1.controller;

import com.globant.training.eggshoopingv1.entities.Order;
import com.globant.training.eggshoopingv1.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // por alguna razon esta linea es obligatoria si no, Swagger no detecta los controladores
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private final OrderService service;


    // aca obtengo todas las ordenes
    @GetMapping(value = "/orders/all")
    ResponseEntity<List<Order>> findAllOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllOrders());
    }

    // aca obtengo una sola orden
    @GetMapping(value = "/orders/id/", params = "id")
    ResponseEntity<Order> findOneOrderById(@RequestParam(name = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getOneOrderById(id));
    }

    // aca obtengo todas las ordenes de un cliente
    @GetMapping(value = "/orders/client/", params = "id_client")
    ResponseEntity<List<Order>> findAllOrdersOfOneClient(@RequestParam(name = "id_client") Long id_client){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllOrdersOfOneClient(id_client));
    }

    // aca obtengo todas las ordenes con el status indicado
    @GetMapping(value = "/orders/status/", params = "status")
    ResponseEntity<List<Order>> findAllOrdersWithAGivenStatus(@RequestParam(name = "status") String status){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllOrdersWithAGivenStatus(status));
    }

    // aca añado una orden nueva
    @PostMapping(value = "/orders/new/", params = "id_client")
    ResponseEntity<Order> createNewOrder(@RequestParam(name = "id_client") Long id_client, @RequestBody Order newOrder){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNewOrder(id_client, newOrder));
    }

    // aca modifico una orden que ya existia o creo uno nuevo si no existe
    @PutMapping(value = "/orders/modify/")
    ResponseEntity<Order> modifyAnExistentOrderOrCreateANewOneIfItDoesntExist(@RequestBody Order newOrder,
                                                                              @RequestParam(value = "id_client") Long id_client){
        return ResponseEntity.status(HttpStatus.OK).body(service.modifyAnExistentOrderOrCreateNewOneIfItDoesntExist(
                newOrder, id_client));
    }

    // aca cancelo una orden
    @DeleteMapping(value = "/orders/cancel/", params = "id")
    ResponseEntity<Order> cancelOrderById(@RequestParam(name = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.cancelOrderById(id));
    }

    // aca completo una orden
    @PutMapping(value = "/orders/complete/", params = "id")
    ResponseEntity<Order> completeOrderById(@RequestParam(name = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.completeOrderById(id));
    }
}
