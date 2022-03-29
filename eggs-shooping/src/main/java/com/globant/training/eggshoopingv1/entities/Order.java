package com.globant.training.eggshoopingv1.entities;

import com.globant.training.eggshoopingv1.util.Status;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "ORDERS")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // creo que para este deberia ser true
    @JoinColumn(name = "client_id", nullable = false) // JoinColumn especifica la clave foranea de la otra entidad
    private Client client;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<OrderLine> orderLines;

    Order(){}

    public Order(Status status, String description){
        this.status = status;
        this.description = description;
    }

    public Long getClient() {
        return client.getId(); // hago esto en vez de devolver el cliente para que no se vuelva recursivo
    }
}
