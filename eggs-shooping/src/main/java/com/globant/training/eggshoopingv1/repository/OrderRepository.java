package com.globant.training.eggshoopingv1.repository;

import com.globant.training.eggshoopingv1.entities.Order;
import com.globant.training.eggshoopingv1.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // simplemente creando este repositorio ya se puede crear, actualizar, borrar y buscar (uno, todos, por busqueda
    // simple o compleja) orden/es

    List<Order> findAllByStatus(Status status);
}
