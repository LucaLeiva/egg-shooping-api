package com.globant.training.eggshoopingv1.controller;

import com.globant.training.eggshoopingv1.entities.Client;
import com.globant.training.eggshoopingv1.services.ClientService;
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
class ClientControllerTest {
    public static final Logger log = LoggerFactory.getLogger(ClientControllerTest.class);

    @MockBean
    private ClientService clientService;
    private ClientController clientController;

    @BeforeEach
    void setUp() {
        clientController = new ClientController(clientService);
    }

    @Test
    void whenFindAllClientsThenOk() {
        Client clientMock1 = Mockito.mock(Client.class);
        Client clientMock2 = Mockito.mock(Client.class);
        Client clientMock3 = Mockito.mock(Client.class);

        List<Client> listMock = Arrays.asList(clientMock1, clientMock2, clientMock3);

        when(clientService.getAllClients()).thenReturn(listMock);

        ResponseEntity<List<Client>> expected = ResponseEntity.status(HttpStatus.OK).body(listMock);

        assertEquals(expected, clientController.findAllClients());
    }

    @Test
    void whenFindOneClientByIdThenOk() {
        Client clientMock = Mockito.mock(Client.class);

        when(clientService.getOneClientById(1L)).thenReturn(clientMock);

        ResponseEntity<Client> expected = ResponseEntity.status(HttpStatus.OK).body(clientMock);

        assertEquals(expected, clientController.findOneClientById(1L));
    }

    @Test
    void whenFindAllClientsByFirstNameThenOk() {
        Client clientMock1 = Mockito.mock(Client.class);
        Client clientMock2 = Mockito.mock(Client.class);
        Client clientMock3 = Mockito.mock(Client.class);

        List<Client> listMock = Arrays.asList(clientMock1, clientMock2, clientMock3);

        when(clientService.getAllClientsByName("Juan")).thenReturn(listMock);

        ResponseEntity<List<Client>> expected = ResponseEntity.status(HttpStatus.OK).body(listMock);

        assertEquals(expected, clientController.findAllClientsByFirstName("Juan"));
    }

    @Test
    void whenFindOneClientByUserName() {
        Client clientMock = Mockito.mock(Client.class);

        when(clientService.getOneClientByUserName("tony654")).thenReturn(clientMock);

        ResponseEntity<Client> expected = ResponseEntity.status(HttpStatus.OK).body(clientMock);

        assertEquals(expected, clientController.findOneClientByUserName("tony654"));
    }

    @Test
    void whenCreateNewClientThenOk(){
        Client clientMock = Mockito.mock(Client.class);

        when(clientService.createNewClient(clientMock)).thenReturn(clientMock);

        ResponseEntity<Client> expected = ResponseEntity.status(HttpStatus.CREATED).body(clientMock);

        assertEquals(expected, clientController.createNewClient(clientMock));
    }

    @Test
    void whenModifyAnExistentClientOrCreateANewOneIfItDoesntExistThenOk(){
        Client clientMock = Mockito.mock(Client.class);

        when(clientService.modifyAnExistentClientOrCreateNewOneIfItDoesntExist(clientMock)).thenReturn(clientMock);

        ResponseEntity<Client> expected = ResponseEntity.status(HttpStatus.OK).body(clientMock);

        assertEquals(expected, clientController.modifyAnExistentClientOrCreateANewOneIfItDoesntExist(clientMock));
    }

    @Test
    void whenDeleteClientByIdThenOk(){
        ResponseEntity<?> expected = ResponseEntity.status(HttpStatus.OK).build();

        assertEquals(expected, clientController.deleteClientById(1L));
    }
}