package com.globant.training.eggshoopingv1.repository;

import com.globant.training.eggshoopingv1.entities.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
