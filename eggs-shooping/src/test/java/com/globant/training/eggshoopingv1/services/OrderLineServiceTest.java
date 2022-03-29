package com.globant.training.eggshoopingv1.services;

import com.globant.training.eggshoopingv1.entities.Order;
import com.globant.training.eggshoopingv1.entities.OrderLine;
import com.globant.training.eggshoopingv1.entities.Product;
import com.globant.training.eggshoopingv1.exception.OrderLineNotFoundException;
import com.globant.training.eggshoopingv1.exception.OrderNotFoundException;
import com.globant.training.eggshoopingv1.exception.ProductNotFoundException;
import com.globant.training.eggshoopingv1.repository.OrderLineRepository;
import com.globant.training.eggshoopingv1.repository.OrderRepository;
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
class OrderLineServiceTest {
    @MockBean
    private OrderLineRepository orderLineRepository;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private ProductRepository productRepository;

    private OrderLineService orderLineService;

    @BeforeEach
    void setUp(){
        orderLineService = new OrderLineService(orderLineRepository, orderRepository, productRepository);
    }

    @Test
    void whenGetAllOrderLinesThenOk(){
        OrderLine orderLineMock1 = Mockito.mock(OrderLine.class);
        OrderLine orderLineMock2 = Mockito.mock(OrderLine.class);
        OrderLine orderLineMock3 = Mockito.mock(OrderLine.class);

        List<OrderLine> listMock = Arrays.asList(orderLineMock1, orderLineMock2, orderLineMock3);

        when(orderLineRepository.findAll()).thenReturn(listMock);

        assertEquals(listMock, orderLineService.getAllOrderLines());
    }

    @Test
    void whenGetOneOrderLineByIdThenOk(){
        OrderLine orderLineMock = Mockito.mock(OrderLine.class);

        when(orderLineRepository.findById(1L)).thenReturn(Optional.of(orderLineMock));

        assertEquals(orderLineMock, orderLineService.getOneOrderLineById(1L));
    }

    @Test
    void whenGetOneOrderLineByIdThenFail(){
        assertThrows(
                OrderLineNotFoundException.class,
                () -> {
                    orderLineService.getOneOrderLineById(99L);
                }
        );
    }

    @Test
    void whenGetAllOrderLinesOfOneOrderThenOk(){
        Order orderMock = Mockito.mock(Order.class);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));

        assertEquals(orderMock.getOrderLines(), orderLineService.getAllOrderLinesOfOneOrder(1L));
    }

    @Test
    void whenGetAllOrderLinesOfOneOrderThenFail(){
        //when(orderRepository.findById(99L)).thenThrow(OrderNotFoundException.class);

        assertThrows(
                OrderNotFoundException.class,
                () -> {
                    orderLineService.getAllOrderLinesOfOneOrder(99L);
                }
        );
    }

    @Test
    void whenCreateNewOrderLineThenOk(){
        OrderLine orderLineMock = Mockito.mock(OrderLine.class);
        Order orderMock = Mockito.mock(Order.class);
        Product productMock = Mockito.mock(Product.class);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));
        when(orderLineRepository.save(orderLineMock)).thenReturn(orderLineMock);

        assertEquals(orderLineMock, orderLineService.createNewOrderLine(1L, 1L, orderLineMock));
    }

    @Test
    void whenCreateNewOrderLineThenFailByOrderNotFound(){
        OrderLine orderLineMock = Mockito.mock(OrderLine.class);
        //Order orderMock = Mockito.mock(Order.class);
        Product productMock = Mockito.mock(Product.class);

        //when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));
        when(orderLineRepository.save(orderLineMock)).thenReturn(orderLineMock);

        assertThrows(OrderNotFoundException.class,
                () -> {
                    orderLineService.createNewOrderLine(99L, 1L, orderLineMock);
                }
                );
    }

    @Test
    void whenCreateNewOrderLineThenFailByProductNotFound(){
        OrderLine orderLineMock = Mockito.mock(OrderLine.class);
        Order orderMock = Mockito.mock(Order.class);
        //Product productMock = Mockito.mock(Product.class);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        //when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));
        when(orderLineRepository.save(orderLineMock)).thenReturn(orderLineMock);

        assertThrows(ProductNotFoundException.class,
                () -> {
                    orderLineService.createNewOrderLine(1L, 99L, orderLineMock);
                }
        );
    }

    @Test
    void whenCreateNewOrderLineThenFailByEmptyField(){
        // ?
    }

    @Test
    void whenModifyAnExistentOrderLineOrCreateNewOneIfItDoesntExistThenModify(){
        OrderLine orderLineExistent = Mockito.mock(OrderLine.class);
        OrderLine orderLineModify = Mockito.mock(OrderLine.class);
        Product productMock = Mockito.mock(Product.class);

        when(orderLineModify.getAmmountOfProduct()).thenReturn(24);

        when(orderLineRepository.findById(1L)).thenReturn(Optional.of(orderLineExistent));
        when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));
        when(orderLineRepository.save(orderLineExistent)).thenReturn(orderLineExistent);

        assertEquals(orderLineExistent, orderLineService.modifyAnExistentOrderLineOrCreateNewOneIfItDoesntExist(orderLineModify, 1L, 1L));
    }

    @Test
    void whenModifyAnExistentOrderLineOrCreateNewOneIfItDoesntExistThenFailModifyByProductNotFound(){
        OrderLine orderLineExistent = Mockito.mock(OrderLine.class);
        OrderLine orderLineModify = Mockito.mock(OrderLine.class);
        //Product productMock = Mockito.mock(Product.class);

        when(orderLineModify.getAmmountOfProduct()).thenReturn(24);

        when(orderLineRepository.findById(1L)).thenReturn(Optional.of(orderLineExistent));
        //when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));
        when(orderLineRepository.save(orderLineExistent)).thenReturn(orderLineExistent);

        assertThrows(
                ProductNotFoundException.class,
                () ->{
                    orderLineService.modifyAnExistentOrderLineOrCreateNewOneIfItDoesntExist(orderLineModify, 1L, 1L);
                }
                );
    }

    @Test
    void whenModifyAnExistentOrderLineOrCreateNewOneIfItDoesntExistThenFailModifyByEmptyField(){
        // ?
    }

    @Test
    void whenModifyAnExistentOrderLineOrCreateNewOneIfItDoesntExistThenCreate(){
        OrderLine orderLineModify = Mockito.mock(OrderLine.class);

        Order orderMock = Mockito.mock(Order.class);
        Product productMock = Mockito.mock(Product.class);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));
        when(orderLineRepository.save(orderLineModify)).thenReturn(orderLineModify);

        assertEquals(orderLineModify, orderLineService.modifyAnExistentOrderLineOrCreateNewOneIfItDoesntExist(orderLineModify, 1L, 1L));
    }

    @Test
    void whenModifyAnExistentOrderLineOrCreateNewOneIfItDoesntExistThenFailCreateByOrderNotFound(){
        OrderLine orderLineModify = Mockito.mock(OrderLine.class);

        //Order orderMock = Mockito.mock(Order.class);
        Product productMock = Mockito.mock(Product.class);

        //when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));
        when(orderLineRepository.save(orderLineModify)).thenReturn(orderLineModify);

        assertThrows(
                OrderNotFoundException.class,
                () -> {
                    orderLineService.modifyAnExistentOrderLineOrCreateNewOneIfItDoesntExist(orderLineModify, 1L, 1L);
                }
        );
    }

    @Test
    void whenModifyAnExistentOrderLineOrCreateNewOneIfItDoesntExistThenFailCreateByProductNotFound(){
        OrderLine orderLineModify = Mockito.mock(OrderLine.class);

        Order orderMock = Mockito.mock(Order.class);
        //Product productMock = Mockito.mock(Product.class);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        //when(productRepository.findById(1L)).thenReturn(Optional.of(productMock));
        when(orderLineRepository.save(orderLineModify)).thenReturn(orderLineModify);

        assertThrows(
                ProductNotFoundException.class,
                () -> {
                    orderLineService.modifyAnExistentOrderLineOrCreateNewOneIfItDoesntExist(orderLineModify, 1L, 1L);
                }
        );
    }

    @Test
    void whenModifyAnExistentOrderLineOrCreateNewOneIfItDoesntExistThenFailCreateByEmptyField(){
        // ?
    }

    @Test
    void whenDeleteOrderLineByIdThenOk(){
        // ?
    }

    @Test
    void whenDeleteOrderLineByIdThenFail(){
        when(orderLineRepository.existsById(1L)).thenReturn(false);

        assertThrows(
                OrderLineNotFoundException.class,
                () -> {
                    orderLineService.deleteOrderLineById(1L);
                }
                );
    }
}