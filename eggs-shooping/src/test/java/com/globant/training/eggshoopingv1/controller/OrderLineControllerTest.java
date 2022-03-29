package com.globant.training.eggshoopingv1.controller;

import com.globant.training.eggshoopingv1.entities.OrderLine;
import com.globant.training.eggshoopingv1.services.OrderLineService;
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
class OrderLineControllerTest {
    public static final Logger log = LoggerFactory.getLogger(OrderLineControllerTest.class);

    @MockBean
    private OrderLineService orderLineService;
    private OrderLineController orderLineController;

    @BeforeEach
    void setUp() {
        orderLineController = new OrderLineController(orderLineService);
    }

    @Test
    void whenFindAllOrderLinesThenOk() {
        OrderLine orderLineMock1 = Mockito.mock(OrderLine.class);
        OrderLine orderLineMock2 = Mockito.mock(OrderLine.class);
        OrderLine orderLineMock3 = Mockito.mock(OrderLine.class);

        List<OrderLine> listMock = Arrays.asList(orderLineMock1, orderLineMock2, orderLineMock3);

        when(orderLineService.getAllOrderLines()).thenReturn(listMock);

        ResponseEntity<List<OrderLine>> expected = ResponseEntity.status(HttpStatus.OK).body(listMock);

        assertEquals(expected, orderLineController.findAllOrderLines());
    }

    @Test
    void whenFindOneOrderLineThenOk(){
        OrderLine orderLineMock = Mockito.mock(OrderLine.class);

        when(orderLineService.getOneOrderLineById(1L)).thenReturn(orderLineMock);

        ResponseEntity<OrderLine> expected = ResponseEntity.status(HttpStatus.OK).body(orderLineMock);

        assertEquals(expected, orderLineController.findOneOrderLineById(1L));
    }

    @Test
    void whenFindAllOrderLinesOfOneOrderByIdThenOk(){
        OrderLine orderLineMock1 = Mockito.mock(OrderLine.class);
        OrderLine orderLineMock2 = Mockito.mock(OrderLine.class);
        OrderLine orderLineMock3 = Mockito.mock(OrderLine.class);

        List<OrderLine> listMock = Arrays.asList(orderLineMock1, orderLineMock2, orderLineMock3);

        when(orderLineService.getAllOrderLinesOfOneOrder(1L)).thenReturn(listMock);

        ResponseEntity<List<OrderLine>> expected = ResponseEntity.status(HttpStatus.OK).body(listMock);

        assertEquals(expected, orderLineController.findAllOrderLinesOfOneOrderById(1L));
    }

    @Test
    void whenCreateNewOrderLineThenOk(){
        OrderLine orderLineMock = Mockito.mock(OrderLine.class);

        when(orderLineService.createNewOrderLine(1L, 1L, orderLineMock)).thenReturn(orderLineMock);

        ResponseEntity<OrderLine> expected = ResponseEntity.status(HttpStatus.CREATED).body(orderLineMock);

        assertEquals(expected, orderLineController.createNewOrderLine(1L, 1L, orderLineMock));
    }

    @Test
    void whenModifyAnExistentOrderLineOrCreateANewOneIfItDoesntExist(){
        OrderLine orderLineMock = Mockito.mock(OrderLine.class);

        when(orderLineService.modifyAnExistentOrderLineOrCreateNewOneIfItDoesntExist(orderLineMock, 1L, 1L)).thenReturn(orderLineMock);

        ResponseEntity<OrderLine> expected = ResponseEntity.status(HttpStatus.OK).body(orderLineMock);

        assertEquals(expected, orderLineController.modifyAnExistentOrderLineOrCreateANewOneIfItDoesntExist(orderLineMock, 1L, 1L));
    }

    @Test
    void whenDeleteOrderLineById(){
        ResponseEntity<?> expected = ResponseEntity.status(HttpStatus.OK).build();

        assertEquals(expected, orderLineController.deleteOrderLinesById(1L));
    }
}