package com.globant.training.eggshoopingv1.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ORDER_LINE")
public class OrderLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int ammountOfProduct;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne//(mappedBy = "orderLine", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    OrderLine(){}

    public OrderLine(int ammountOfProduct, Order order, Product product){
        this.ammountOfProduct = ammountOfProduct;
        this.order = order;
        this.product = product;
    }

    public Long getOrder(){
        return order.getId();
    }
}
