package com.globant.training.eggshoopingv1.controller;

import com.globant.training.eggshoopingv1.entities.OrderLine;
import com.globant.training.eggshoopingv1.services.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderLineController {
    @Autowired
    private final OrderLineService service;


    // aca obtengo todas las lineas de ordenes
    @GetMapping(value = "/orderlines/all")
    ResponseEntity<List<OrderLine>> findAllOrderLines(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllOrderLines());
    }

    // aca obtengo una sola linea de orden
    @GetMapping(value = "/orderlines/id/", params = "id")
    ResponseEntity<OrderLine> findOneOrderLineById(@RequestParam(name = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getOneOrderLineById(id));
    }

    // aca obtengo todas las lineas de ordenes de una orden
    @GetMapping(value = "/orderlines/orders/", params = "id_order")
    ResponseEntity<List<OrderLine>> findAllOrderLinesOfOneOrderById(@RequestParam(name = "id_order") Long id_order){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllOrderLinesOfOneOrder(id_order));
    }

    // aca añado una linea de orden nueva
    @PostMapping(value = "/orderlines/new")
    ResponseEntity<OrderLine> createNewOrderLine(@RequestParam(value = "id_order") Long id_order,
                                                 @RequestParam(value = "id_product") Long id_product,
                                                 @RequestBody OrderLine newOrderLine){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createNewOrderLine(id_order, id_product, newOrderLine));
    }

    // aca modifico una orden que ya existia o creo uno nueva si no existe
    @PutMapping(value = "/orderlines/modify")
    ResponseEntity<OrderLine> modifyAnExistentOrderLineOrCreateANewOneIfItDoesntExist(@RequestBody OrderLine newOrderLine,
                                                                                      @RequestParam(value= "id_product") Long id_product,
                                                                                      @RequestParam(value = "id_order") Long id_order){
        return ResponseEntity.status(HttpStatus.OK).body(service.modifyAnExistentOrderLineOrCreateNewOneIfItDoesntExist(
                newOrderLine, id_product, id_order));
    }

    // aca elimino una linea de orden
    @DeleteMapping(value = "/orderlines/delete/", params = "id")
    ResponseEntity<?> deleteOrderLinesById(@RequestParam(name = "id") Long id){
        service.deleteOrderLineById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
