package com.globant.training.eggshoopingv1.services;

import com.globant.training.eggshoopingv1.entities.Client;
import com.globant.training.eggshoopingv1.exception.ClientAlreadyExistsException;
import com.globant.training.eggshoopingv1.exception.ClientNotFoundException;
import com.globant.training.eggshoopingv1.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.PropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Service seria por asi decirlo un paso intermedio entre el Controller y el Repository para hacer acciones complejas

/*
Explicacion de la diferencia entre Servicio, Controlador y Repositorio

Separation of concerns is the key:

-The controller (presentation layer, or port) is a protocol interface which exposes application functionality as RESTful
web services. It should to that and nothing more.
-The repository (persistence layer, or adapter) abstracts persistence operations: find (by id or other criteria), save
(create, update) and delete records. It should to that and nothing more.
-The service layer (domain) contains your business logic. It defines which functionalities you provide, how they are
accessed, and what to pass and get in return - independent on any port (of which there may be multiple: web services,
message queues, scheduled events) and independent on its internal workings (it's nobody's business that the service uses
the repository, or even how data is represented in a repository). The service layer may translate 1:1 from the
repositiory data, or may apply filtering, transformation or aggregation of additional data.

The business logic may start simple in the beginning, and offer not more that simple CRUD operations, but that doesn't
mean it will forever stay this way. As soon as you need to deal with access rights, it's no longer a matter of routing
requests from the controller directly to the repository, but checking access and filtering data as well. Requests may
need validation and consistency checks before hitting the database, rules and additional operations may be applied, so
your services get more value over time.

Even for simple CRUD cases, I'd introduce a service layer, which at least translates from DTOs to Entities and vice
versa.

Keep your controllers/repositories (or ports and adapters) stupid, and your services smart, and you get a maintainable
and well-testable solution.
 */


@Service
@RequiredArgsConstructor
public class ClientService {
    @Autowired
    private final ClientRepository repository;

    // obtener todos los clientes
    public List<Client> getAllClients(){
        return repository.findAll();
    }

    // obtener un cliente por id
    public Client getOneClientById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        // podria haber puesto la respuesta cuando no encuentra al cliente aca, pero creo que es mejor que este separado
    }

    // obtener un cliente por nombre
    public List<Client> getAllClientsByName(String firstName){
        return repository.findAllByFirstName(firstName);
    }

    // obtener un cliente por username
    public Client getOneClientByUserName(String userName){
        return repository.findByUserName(userName)
                .orElseThrow(() -> new ClientNotFoundException(userName));
    }

    // crear un nuevo cliente
    public Client createNewClient(Client newClient){
        if((repository.existsByUserName(newClient.getUserName())) == false){ // pregunto si el username ya existe
            if((repository.existsById(newClient.getId())) == false){ // pregunto si el id ya existe
                return repository.save(newClient);
            }else{
                throw new ClientAlreadyExistsException(newClient.getId()); // si ya existe un cliente con el mismo id
            }
        }else{
            throw new ClientAlreadyExistsException(newClient.getUserName()); // si ya existe un cliente con el mismo username
        }
    }

    // modifico un cliente existente o creo uno nuevo si no existe
    public Client modifyAnExistentClientOrCreateNewOneIfItDoesntExist(Client newClient){
        return (repository.findById(newClient.getId())
                .map(client -> {
                    if(newClient.getFirstName() != null){
                        client.setFirstName(newClient.getFirstName());
                    }
                    if(!(newClient.getLastName() == null)){
                        client.setLastName(newClient.getLastName());
                    }
                    if(!(newClient.getUserName() == null)) { // si es nulo, no hace nada
                        if(!(client.getUserName().equalsIgnoreCase(newClient.getUserName()))) { // si los nombres de usuario son iguales, no hace nada
                            if((repository.existsByUserName(newClient.getUserName())) == false) {
                                client.setUserName(newClient.getUserName());
                            }else{
                                throw new ClientAlreadyExistsException(newClient.getUserName());
                            }
                        }
                    }
                    if(!(newClient.getAge() < 0)){
                        client.setAge(newClient.getAge());
                    }
                    return repository.save(client);
                })
                .orElseGet(() -> {
                    //newClient.setId(id);
                    if(repository.existsByUserName(newClient.getUserName())){
                        throw new ClientAlreadyExistsException(newClient.getUserName());
                    }
                    return repository.save(newClient);
                }));
    }

    // debe devolver algo, aunque sea un booleano
    public void deleteClientById(Long id){
        if(repository.existsById(id)) {
            repository.deleteById(id); // no se porque deleteById no deja poner orElseThrow,

        } else{
            throw new ClientNotFoundException(id);
        }
    }
}