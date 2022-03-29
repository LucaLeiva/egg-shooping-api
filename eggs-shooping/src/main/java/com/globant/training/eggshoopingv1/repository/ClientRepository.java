package com.globant.training.eggshoopingv1.repository;

import com.globant.training.eggshoopingv1.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    // simplemente creando este repositorio ya se puede crear, actualizar, borrar y buscar (uno, todos, por busqueda
    // simple o compleja) cliente/s

    List<Client> findAllByFirstName(String firstName);

    Optional<Client> findByUserName(String userName);

    boolean existsByUserName(String userName);
}
