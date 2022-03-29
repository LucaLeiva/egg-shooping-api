package com.globant.training.eggshoopingv1.repository;

import com.globant.training.eggshoopingv1.entities.Order;
import com.globant.training.eggshoopingv1.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByNameContains(String name);

    boolean existsByName(String name);
}
