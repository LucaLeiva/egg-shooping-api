package com.globant.training.eggshoopingv1.services;

import com.globant.training.eggshoopingv1.entities.Order;
import com.globant.training.eggshoopingv1.entities.OrderLine;
import com.globant.training.eggshoopingv1.entities.Product;
import com.globant.training.eggshoopingv1.exception.*;
import com.globant.training.eggshoopingv1.repository.ClientRepository;
import com.globant.training.eggshoopingv1.repository.OrderLineRepository;
import com.globant.training.eggshoopingv1.repository.OrderRepository;
import com.globant.training.eggshoopingv1.repository.ProductRepository;
import com.globant.training.eggshoopingv1.util.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final ClientRepository clientRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final OrderLineRepository orderLineRepository;


    // obtener todas las ordenes
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    // obtener una orden por id
    public Order getOneOrderById(Long id){
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    // obtener todas las ordenes de un cliente
    public List<Order> getAllOrdersOfOneClient(Long id_client){
        if(clientRepository.existsById(id_client)){
            return clientRepository.findById(id_client).get().getOrders();
        } else{
            throw new ClientNotFoundException(id_client);
        }
    }

    // obtener todas las ordenes con un status indicado
    public List<Order> getAllOrdersWithAGivenStatus(String status){
        if(status.equalsIgnoreCase("in_progress")){
            return orderRepository.findAllByStatus(Status.IN_PROGRESS);
        }
        if(status.equalsIgnoreCase("completed")){
            return orderRepository.findAllByStatus(Status.COMPLETED);
        }
        if(status.equalsIgnoreCase("cancelled")){
            return orderRepository.findAllByStatus(Status.CANCELLED);
        }
        throw new IncorrectParameterException();
    }

    // crear una nueva orden
    public Order createNewOrder(Long id_client, Order newOrder){
        if(!(orderRepository.existsById(newOrder.getId()))){
            newOrder.setStatus(Status.IN_PROGRESS); // siempre que creo una orden se crea con el status in progress
            newOrder.setClient(
                    clientRepository.findById(id_client)
                            .orElseThrow(() -> new ClientNotFoundException(id_client)));
            return orderRepository.save(newOrder);
        } else{
            throw new OrderAlreadyExistsException(newOrder.getId());
        }

    }

    // modifico una orden existente o creo una nueva si no existe
    public Order modifyAnExistentOrderOrCreateNewOneIfItDoesntExist(Order newOrder, Long id_client){
        return (orderRepository.findById(newOrder.getId())
                .map(order -> {
                    // no pongo para cambiar el status porque eso se hace en otro metodo
                    if(!(newOrder.getDescription() == null)){
                        order.setDescription(newOrder.getDescription());
                    }
                    // no pongo para cambiar el cliente porque creo que no tiene sentido
                    if(!(newOrder.getOrderLines().equals(null))){
                        order.setOrderLines(newOrder.getOrderLines());
                    }
                    return orderRepository.save(order);
                })
                .orElseGet(() -> {
                    if(!(orderRepository.existsById(newOrder.getId()))){ // pregunto si el id ya existe
                        newOrder.setId(newOrder.getId());
                        newOrder.setStatus(Status.IN_PROGRESS);
                        newOrder.setClient(
                                clientRepository.findById(id_client)
                                        .orElseThrow(() -> new ClientNotFoundException(id_client))
                        );
                        return orderRepository.save(newOrder);
                    }else{
                        throw new OrderAlreadyExistsException(newOrder.getId()); // si ya existe un cliente con el mismo id
                    }
                })
        );
    }

    public Order cancelOrderById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if(order.getStatus().equals(Status.IN_PROGRESS)){
            order.setStatus(Status.CANCELLED);
        } else{
            throw new MethodNotPermitedException(order.getStatus());
        }

        List<OrderLine> orderLines = order.getOrderLines();

        // esto lo tengo que modularizar porque lo repito varias veces en otros servicios
        for(OrderLine orderLine : orderLines){
            Product product = productRepository.findById(orderLine.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException(orderLine.getProduct().getId()));
            product.setAmmountInStock(
                    product.getAmmountInStock() + orderLine.getAmmountOfProduct());
            orderLine.setAmmountOfProduct(0);
            orderLineRepository.save(orderLine);
            productRepository.save(product);
        }

        return orderRepository.save(order);
    }

    public Order completeOrderById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if(order.getStatus().equals(Status.IN_PROGRESS)){
            order.setStatus(Status.COMPLETED);
        } else{
            throw new MethodNotPermitedException(order.getStatus());
        }

        return orderRepository.save(order);
    }
}
