package com.globant.training.eggshoopingv1.services;

import com.globant.training.eggshoopingv1.LoadDatabase;
import com.globant.training.eggshoopingv1.entities.Client;
import com.globant.training.eggshoopingv1.entities.Order;
import com.globant.training.eggshoopingv1.exception.*;
import com.globant.training.eggshoopingv1.repository.ClientRepository;
import com.globant.training.eggshoopingv1.repository.OrderLineRepository;
import com.globant.training.eggshoopingv1.repository.OrderRepository;
import com.globant.training.eggshoopingv1.repository.ProductRepository;
import com.globant.training.eggshoopingv1.util.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OrderServiceTest {
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private ClientRepository clientRepository;
    private OrderService orderService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private OrderLineRepository orderLineRepository;

    public static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);


    @BeforeEach
    void setUp(){
        orderService = new OrderService(orderRepository, clientRepository, productRepository, orderLineRepository);
    }

    @Test
    void whenGetAllOrdersThenOk(){
        Order orderMock1 = Mockito.mock(Order.class);
        Order orderMock2 = Mockito.mock(Order.class);
        Order orderMock3 = Mockito.mock(Order.class);

        List<Order> listMock = Arrays.asList(orderMock1, orderMock2, orderMock3);

        when(orderRepository.findAll()).thenReturn(listMock);

        assertEquals(listMock, orderService.getAllOrders());
    }

    @Test
    void whenGetOneClientByIdThenOk(){
        Order order = Mockito.mock(Order.class);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertEquals(order, orderService.getOneOrderById(1L));
    }

    @Test
    void whenGetOneClientByIdThenFail(){
        assertThrows(
                OrderNotFoundException.class,
                () -> {
                    orderService.getOneOrderById(99L);
                }
        );
    }

    @Test
    void whenGetAllOrdersOfOneClientThenOk(){
        Client client = Mockito.mock(Client.class);

        List<Order> listMock = client.getOrders();

        when(clientRepository.existsById(1L)).thenReturn(true);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        assertEquals(listMock, orderService.getAllOrdersOfOneClient(1L));
    }

    @Test
    void whenGetAllOrdersOfOneClientThenFail(){
        when(clientRepository.existsById(1L)).thenReturn(false);

        assertThrows(
                ClientNotFoundException.class,
                () -> {
                    orderService.getAllOrdersOfOneClient(1L);
                }
        );
    }

    @Test
    void whenGetAllOrdersWithStatusThenOk(){
        Order orderInProgress1 = Mockito.mock(Order.class);
        //orderInProgress1.setStatus(Status.IN_PROGRESS);
        Order orderInProgress2 = Mockito.mock(Order.class);
        //orderInProgress2.setStatus(Status.IN_PROGRESS);
        Order orderInProgress3 = Mockito.mock(Order.class);
        //orderInProgress3.setStatus(Status.IN_PROGRESS);
        Order orderInProgress4 = Mockito.mock(Order.class);
        //orderInProgress4.setStatus(Status.IN_PROGRESS);

        List<Order> listInProgress = Arrays.asList(orderInProgress1, orderInProgress2, orderInProgress3, orderInProgress4);


        Order orderCompleted1 = Mockito.mock(Order.class);
        //orderCompleted1.setStatus(Status.COMPLETED);
        Order orderCompleted2 = Mockito.mock(Order.class);
        //orderCompleted2.setStatus(Status.COMPLETED);
        Order orderCompleted3 = Mockito.mock(Order.class);
        //orderCompleted3.setStatus(Status.COMPLETED);

        List<Order> listCompleted = Arrays.asList(orderCompleted1, orderCompleted2, orderCompleted3);


        Order orderCancelled1 = Mockito.mock(Order.class);
        //orderCancelled1.setStatus(Status.CANCELLED);
        Order orderCancelled2 = Mockito.mock(Order.class);
        //orderCancelled2.setStatus(Status.CANCELLED);

        List<Order> listCancelled = Arrays.asList(orderCancelled1, orderCancelled2);


        when(orderRepository.findAllByStatus(Status.IN_PROGRESS)).thenReturn(listInProgress);
        when(orderRepository.findAllByStatus(Status.COMPLETED)).thenReturn(listCompleted);
        when(orderRepository.findAllByStatus(Status.CANCELLED)).thenReturn(listCancelled);

        assertAll(() -> {
            assertEquals(listInProgress, orderService.getAllOrdersWithAGivenStatus("in_progress"));
            assertEquals(listCompleted, orderService.getAllOrdersWithAGivenStatus("completed"));
            assertEquals(listCancelled, orderService.getAllOrdersWithAGivenStatus("cancelled"));
        });
    }

    @Test
    void whenGetAllOrdersWithStatusThenFail(){
        assertThrows(
                IncorrectParameterException.class,
                () -> {
                    orderService.getAllOrdersWithAGivenStatus("algo");
                }
                );
    }

    @Test
    void whenCreateNewOrderThenOk(){
        Order orderMock = Mockito.mock(Order.class);
        Client clientMock = Mockito.mock(Client.class);

        when(orderMock.getId()).thenReturn(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));
        when(orderRepository.existsById(1L)).thenReturn(false);
        when(orderRepository.save(orderMock)).thenReturn(orderMock);

        assertEquals(orderMock, orderService.createNewOrder(1L, orderMock));
    }

    @Test
    void whenCreateNewOrderThenFailByClient(){
        Order orderMock = Mockito.mock(Order.class);
        //Client clientMock = Mockito.mock(Client.class);

        //when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));
        when(orderRepository.existsById(1L)).thenReturn(false);
        when(orderRepository.save(orderMock)).thenReturn(orderMock);

        assertThrows(
                ClientNotFoundException.class,
                () -> {
                    orderService.createNewOrder(1L, orderMock);
                }
        );
    }

    @Test
    void whenCreateNewOrderThenFailByOrderAlreadyExists(){
        Order orderMock = Mockito.mock(Order.class);
        Client clientMock = Mockito.mock(Client.class);

        when(orderMock.getId()).thenReturn(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(orderRepository.save(orderMock)).thenReturn(orderMock);

        assertThrows(
                OrderAlreadyExistsException.class,
                () -> {
                    orderService.createNewOrder(1L, orderMock);
                }
        );
    }

    @Test
    void whenCreateNewOrderThenFailByEmptyField(){
        // ?
    }

    @Test
    void whenModifyAnExistentOrderOrCreateNewOneIfItDoesntExistThenModify(){
        Order orderExistent = Mockito.mock(Order.class);
        Order orderModify = Mockito.mock(Order.class);

        when(orderModify.getDescription()).thenReturn("Descripcion");

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderExistent));
        when(orderRepository.save(orderExistent)).thenReturn(orderExistent);

        assertEquals(orderExistent, orderService.modifyAnExistentOrderOrCreateNewOneIfItDoesntExist(orderModify, 1L));
    }

    @Test
    void whenModifyAnExistentOrderOrCreateNewOneIfItDoesntExistThenCreate(){
        Order orderModify = Mockito.mock(Order.class);

        Client clientMock = Mockito.mock(Client.class);

        when(orderModify.getId()).thenReturn(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));
        when((orderRepository.existsById(1L))).thenReturn(false);
        when(orderRepository.save(orderModify)).thenReturn(orderModify);

        assertEquals(orderModify, orderService.modifyAnExistentOrderOrCreateNewOneIfItDoesntExist(orderModify, 1L));
    }

    @Test
    void whenModifyAnExistentOrderOrCreateNewOneIfItDoesntExistThenFailCreateByClientNotFound(){
        Order orderModify = Mockito.mock(Order.class);

        //Client clientMock = Mockito.mock(Client.class);

        when(orderModify.getId()).thenReturn(1L);

        //when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));
        when((orderRepository.existsById(1L))).thenReturn(false);
        when(orderRepository.save(orderModify)).thenReturn(orderModify);

        assertThrows(
                ClientNotFoundException.class,
                () -> {
                    orderService.modifyAnExistentOrderOrCreateNewOneIfItDoesntExist(orderModify, 1L);
                }
        );
    }

    @Test
    void whenModifyAnExistentOrderOrCreateNewOneIfItDoesntExistThenFailCreateByOrderAlreadyExists(){
        Order orderModify = Mockito.mock(Order.class);

        Client clientMock = Mockito.mock(Client.class);

        when(orderModify.getId()).thenReturn(1L);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));
        when((orderRepository.existsById(1L))).thenReturn(true);
        when(orderRepository.save(orderModify)).thenReturn(orderModify);

        assertThrows(
                OrderAlreadyExistsException.class,
                () -> {
                    orderService.modifyAnExistentOrderOrCreateNewOneIfItDoesntExist(orderModify, 1L);
                }
        );
    }

    @Test
    void whenModifyAnExistentOrderOrCreateNewOneIfItDoesntExistThenFailCreateByEmptyField(){
        // ?
    }

    @Test
    void whenCancelOrderByIdThenOk(){
        Order orderMock = Mockito.mock(Order.class);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        when(orderMock.getStatus()).thenReturn(Status.IN_PROGRESS);
        when(orderRepository.save(orderMock)).thenReturn(orderMock);

        assertEquals(orderMock, orderService.cancelOrderById(1L));
    }

    @Test
    void whenCancelOrderByIdThenFailByOrderNotFound(){
        Order orderMock = Mockito.mock(Order.class);

        //when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        when(orderMock.getStatus()).thenReturn(Status.IN_PROGRESS);
        when(orderRepository.save(orderMock)).thenReturn(orderMock);

        assertThrows(
                OrderNotFoundException.class,
                () -> {
                    orderService.cancelOrderById(1L);
                }
        );
    }

    @Test
    void whenCancelOrderByIdThenFailByMethodNotAllowed(){
        Order orderMock = Mockito.mock(Order.class);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        when(orderMock.getStatus()).thenReturn(Status.COMPLETED);
        when(orderRepository.save(orderMock)).thenReturn(orderMock);

        assertThrows(
                MethodNotPermitedException.class,
                () -> {
                    orderService.cancelOrderById(1L);
                }
        );
    }

    @Test
    void whenCompleteOrderByIdThenOk(){
        Order orderMock = Mockito.mock(Order.class);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        when(orderMock.getStatus()).thenReturn(Status.IN_PROGRESS);
        when(orderRepository.save(orderMock)).thenReturn(orderMock);

        assertEquals(orderMock, orderService.cancelOrderById(1L));
    }

    @Test
    void whenCompleteOrderByIdThenFailByOrder(){
        Order orderMock = Mockito.mock(Order.class);

        //when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        when(orderMock.getStatus()).thenReturn(Status.IN_PROGRESS);
        when(orderRepository.save(orderMock)).thenReturn(orderMock);

        assertThrows(
                OrderNotFoundException.class,
                () -> {
                    orderService.cancelOrderById(1L);
                }
        );    }

    @Test
    void whenCompleteOrderByIdThenFailByMethodNotAllowed(){
        Order orderMock = Mockito.mock(Order.class);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderMock));
        when(orderMock.getStatus()).thenReturn(Status.COMPLETED);
        when(orderRepository.save(orderMock)).thenReturn(orderMock);

        assertThrows(
                MethodNotPermitedException.class,
                () -> {
                    orderService.cancelOrderById(1L);
                }
        );    }
}