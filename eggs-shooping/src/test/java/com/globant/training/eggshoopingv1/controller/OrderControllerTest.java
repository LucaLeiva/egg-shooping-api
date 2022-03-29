package com.globant.training.eggshoopingv1.controller;

import com.globant.training.eggshoopingv1.entities.Order;
import com.globant.training.eggshoopingv1.services.OrderService;
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
class OrderControllerTest {
    public static final Logger log = LoggerFactory.getLogger(OrderControllerTest.class);

    @MockBean
    private OrderService orderService;
    private OrderController orderController;

    @BeforeEach
    void setUp(){
        orderController = new OrderController(orderService);
    }

    @Test
    void whenFindAllOrdersThenOk(){
        Order orderMock1 = Mockito.mock(Order.class);
        Order orderMock2 = Mockito.mock(Order.class);
        Order orderMock3 = Mockito.mock(Order.class);

        List<Order> listMock = Arrays.asList(orderMock1, orderMock2, orderMock3);

        when(orderService.getAllOrders()).thenReturn(listMock);

        ResponseEntity<List<Order>> expected = ResponseEntity.status(HttpStatus.OK).body(listMock);

        assertEquals(expected, orderController.findAllOrders());
    }

    @Test
    void whenFindOneOrderByIdThenOk(){
        Order orderMock = Mockito.mock(Order.class);

        when(orderService.getOneOrderById(1L)).thenReturn(orderMock);

        ResponseEntity<Order> expected = ResponseEntity.status(HttpStatus.OK).body(orderMock);

        assertEquals(expected, orderController.findOneOrderById(1L));
    }

    @Test
    void whenFindAllOrdersOfOneClientThenOk(){
        Order orderMock1 = Mockito.mock(Order.class);
        Order orderMock2 = Mockito.mock(Order.class);
        Order orderMock3 = Mockito.mock(Order.class);

        List<Order> listMock = Arrays.asList(orderMock1, orderMock2, orderMock3);

        when(orderService.getAllOrdersOfOneClient(1L)).thenReturn(listMock);

        ResponseEntity<List<Order>> expected = ResponseEntity.status(HttpStatus.OK).body(listMock);

        assertEquals(expected, orderController.findAllOrdersOfOneClient(1L));
    }

    @Test
    void whenFindAllOrdersWithAGivenStatusThenOk(){
        Order orderMock1 = Mockito.mock(Order.class);
        Order orderMock2 = Mockito.mock(Order.class);
        Order orderMock3 = Mockito.mock(Order.class);

        List<Order> listMock = Arrays.asList(orderMock1, orderMock2, orderMock3);

        when(orderService.getAllOrdersWithAGivenStatus("in_progress")).thenReturn(listMock);

        ResponseEntity<List<Order>> expected = ResponseEntity.status(HttpStatus.OK).body(listMock);

        assertEquals(expected, orderController.findAllOrdersWithAGivenStatus("in_progress"));
    }

    @Test
    void whenCreateNewOrderThenOk(){
        Order orderMock = Mockito.mock(Order.class);

        when(orderService.createNewOrder(1L, orderMock)).thenReturn(orderMock);

        ResponseEntity<Order> expected = ResponseEntity.status(HttpStatus.CREATED).body(orderMock);

        assertEquals(expected, orderController.createNewOrder(1L, orderMock));
    }

    @Test
    void whenModifyAnExistentOrderOrCreateANewOneIfItDoesntExistThenOk(){
        Order orderMock = Mockito.mock(Order.class);

        when(orderService.modifyAnExistentOrderOrCreateNewOneIfItDoesntExist(orderMock, 1L)).thenReturn(orderMock);

        ResponseEntity<Order> expected = ResponseEntity.status(HttpStatus.OK).body(orderMock);

        assertEquals(expected, orderController.modifyAnExistentOrderOrCreateANewOneIfItDoesntExist(orderMock, 1L));
    }

    @Test
    void whenCancelOrderByIdThenOk(){
        Order orderMock = Mockito.mock(Order.class);

        when(orderService.cancelOrderById(1L)).thenReturn(orderMock);

        ResponseEntity<Order> expected = ResponseEntity.status(HttpStatus.OK).body(orderMock);

        assertEquals(expected, orderController.cancelOrderById(1L));
    }

    @Test
    void whenCompleteOrderByIdThenOk(){
        Order orderMock = Mockito.mock(Order.class);

        when(orderService.completeOrderById(1L)).thenReturn(orderMock);

        ResponseEntity<Order> expected = ResponseEntity.status(HttpStatus.OK).body(orderMock);

        assertEquals(expected, orderController.completeOrderById(1L));
    }
}