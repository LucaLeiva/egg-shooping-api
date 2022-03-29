package com.globant.training.eggshoopingv1.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

// aca estoy mezclando dos capas, la de entidades y la de dominio. Mirar ModelMapper y arquitectura hexagonal

@Entity
@Table(name = "CLIENTS")
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private int age;

    // mappedBy = "client" indica el nombre del atributo mapeo-asociacion de la otra entidad, sea lo que signifique eso,
    // para hacerla bidireccional
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Order> orders;

    Client(){}

    public Client(String firstName, String lastName, String username, int age){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.userName = username;
    }
}
