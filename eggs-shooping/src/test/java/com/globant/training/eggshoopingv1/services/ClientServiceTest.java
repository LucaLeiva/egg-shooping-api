package com.globant.training.eggshoopingv1.services;

import com.globant.training.eggshoopingv1.LoadDatabase;
import com.globant.training.eggshoopingv1.entities.Client;
import com.globant.training.eggshoopingv1.exception.ClientAlreadyExistsException;
import com.globant.training.eggshoopingv1.exception.ClientNotFoundException;
import com.globant.training.eggshoopingv1.repository.ClientRepository;
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
class ClientServiceTest {
    @MockBean
    private ClientRepository clientRepository;
    private ClientService clientService;

    public static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @BeforeEach
    void setUp(){
        clientService = new ClientService(clientRepository);
    }

    @Test
    void whenGetAllClientsThenOk(){ // ✔
        Client clientMock1 = Mockito.mock(Client.class);
        Client clientMock2 = Mockito.mock(Client.class);
        Client clientMock3 = Mockito.mock(Client.class);

        List<Client> listMock = Arrays.asList(clientMock1, clientMock2, clientMock3);

        when(clientRepository.findAll()).thenReturn(listMock);

        assertEquals(listMock, clientService.getAllClients());
    }

    @Test
    void whenGetOneClientByIdThenOk(){ // ✔
        Client clientMock = Mockito.mock(Client.class);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientMock));

        assertEquals(clientMock, clientService.getOneClientById(1L));
    }

    @Test
    void whenGetOneClientByIdThenFail(){ // ✔
        assertThrows(
                ClientNotFoundException.class,
                () -> {
                    clientService.getOneClientById(99L);
                }
                );
    }

    @Test
    void whenGetAllClientsByNameThenOk(){
        // no sirve de nada setear los nombres asi que no si se tiene que hacer de otra forma
        Client clientMock1 = Mockito.mock(Client.class);
        //clientMock1.setFirstName("Juan");
        Client clientMock2 = Mockito.mock(Client.class);
        //clientMock2.setFirstName("Juan");
        Client clientMock3 = Mockito.mock(Client.class);
        //clientMock3.setFirstName("Juan");

        List<Client> listMock = Arrays.asList(clientMock1, clientMock2, clientMock3);

        when(clientRepository.findAllByFirstName("Juan")).thenReturn(listMock);

        assertEquals(listMock, clientService.getAllClientsByName("Juan"));
    }

    @Test
    void whenGetOneClientByUserNameThenOk(){
        Client clientMock = Mockito.mock(Client.class);

        when(clientRepository.findByUserName("juan77")).thenReturn(Optional.of(clientMock));

        assertEquals(clientMock, clientService.getOneClientByUserName("juan77"));
    }

    @Test
    void whenGetOneClientByUserNameThenFail(){
        assertThrows(
                ClientNotFoundException.class,
                () -> {
                    clientService.getOneClientByUserName("juan77");
                }
        );
    }

    @Test
    void whenCreateNewClientThenOk(){
        Client clientMock = Mockito.mock(Client.class);

        //when(clientMock.getUserName()).thenReturn("Pedro333");
        //when(clientMock.getId()).thenReturn(1L);

        when(clientRepository.existsByUserName(clientMock.getUserName())).thenReturn(false);
        when(clientRepository.existsById(clientMock.getId())).thenReturn(false);
        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(clientMock);

        assertEquals(clientMock, clientService.createNewClient(clientMock));
    }

    @Test
    void whenCreateNewClientThenFailByRepeatedUsername(){
        Client clientMock = Mockito.mock(Client.class);

        when(clientMock.getUserName()).thenReturn("juan77");

        when(clientRepository.existsByUserName("juan77")).thenReturn(true);
        when(clientRepository.existsById(clientMock.getId())).thenReturn(false);
        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(clientMock);

        assertThrows(
                ClientAlreadyExistsException.class,
                () -> {
                    clientService.createNewClient(clientMock);
                }
        );
    }

    @Test
    void whenCreateNewClientThenFailByRepeatedId(){
        Client clientMock = Mockito.mock(Client.class);

        when(clientMock.getId()).thenReturn(1L);

        when(clientRepository.existsById(1L)).thenReturn(true);
        when(clientRepository.existsByUserName(clientMock.getUserName())).thenReturn(false);
        when(clientRepository.save(Mockito.any(Client.class))).thenReturn(clientMock);

        assertThrows(
                ClientAlreadyExistsException.class,
                () -> {
                    clientService.createNewClient(clientMock);
                }
        );
    }

    @Test
    void whenCreateNewClientThenFailByEmptyField(){
        // ?
    }

    @Test
    void whenModifyAnExistentClientOrCreateNewOneIfItDoesntExistThenModify(){
        Client clientExistent = Mockito.mock(Client.class);

        Client clientModify = Mockito.mock(Client.class);

        when(clientModify.getFirstName()).thenReturn("Jose");
        when(clientModify.getLastName()).thenReturn("Antonio");
        when(clientModify.getUserName()).thenReturn("tony765");
        when(clientModify.getAge()).thenReturn(25);

        when(clientExistent.getUserName()).thenReturn("Aldo33");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientExistent));
        when(clientRepository.existsByUserName("tony765")).thenReturn(false);
        when(clientRepository.save(clientExistent)).thenReturn(clientExistent);

        assertEquals(clientExistent, clientService.modifyAnExistentClientOrCreateNewOneIfItDoesntExist(clientModify));
    }

    @Test
    void whenModifyAnExistentClientOrCreateNewOneIfItDoesntExistThenFailModifyByRepeatedUserName(){
        Client clientExistent = Mockito.mock(Client.class);

        Client clientModify = Mockito.mock(Client.class);

        when(clientModify.getFirstName()).thenReturn("Jose");
        when(clientModify.getLastName()).thenReturn("Antonio");
        when(clientModify.getUserName()).thenReturn("tony765");
        when(clientModify.getAge()).thenReturn(25);

        when(clientExistent.getUserName()).thenReturn("Aldo33");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(clientExistent));
        when(clientRepository.existsByUserName("tony765")).thenReturn(true);
        when(clientRepository.save(clientExistent)).thenReturn(clientExistent);

        assertThrows(
                ClientAlreadyExistsException.class,
                () -> {
                    clientService.modifyAnExistentClientOrCreateNewOneIfItDoesntExist(clientModify);
                }
        );
    }

    @Test
    void whenModifyAnExistentClientOrCreateNewOneIfItDoesntExistThenCreate(){
        Client clientModify = Mockito.mock(Client.class);

        when(clientModify.getUserName()).thenReturn("tony65");

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        when(clientRepository.existsByUserName("tony65")).thenReturn(false);
        when(clientRepository.save(clientModify)).thenReturn(clientModify);

        assertEquals(clientModify, clientService.modifyAnExistentClientOrCreateNewOneIfItDoesntExist(clientModify));
    }

    @Test
    void whenModifyAnExistentClientOrCreateNewOneIfItDoesntExistThenFailCreateByRepeatedUserName(){
        Client clientModify = Mockito.mock(Client.class);

        when(clientModify.getUserName()).thenReturn("tony65");

        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        when(clientRepository.existsByUserName("tony65")).thenReturn(true);
        when(clientRepository.save(clientModify)).thenReturn(clientModify);

        assertThrows(
                ClientAlreadyExistsException.class,
                () -> {
                    clientService.modifyAnExistentClientOrCreateNewOneIfItDoesntExist(clientModify);
                }
        );
    }

    @Test
    void whenModifyAnExistentClientOrCreateNewOneIfItDoesntExistThenFailCreateByEmptyField(){
        // ?
    }

    @Test
    void whenDeleteClientByIdThenOk(){
        // ?
    }

    @Test
    void whenDeleteClientByIdThenFail(){
        when(clientRepository.existsById(1L)).thenReturn(false);

        assertThrows(
                ClientNotFoundException.class,
                () -> {
                    clientService.deleteClientById(1L);
                }
        );
    }
}