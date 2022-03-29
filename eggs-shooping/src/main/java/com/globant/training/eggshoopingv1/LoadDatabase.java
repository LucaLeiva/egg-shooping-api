package com.globant.training.eggshoopingv1;

import com.globant.training.eggshoopingv1.entities.Client;
import com.globant.training.eggshoopingv1.entities.Order;
import com.globant.training.eggshoopingv1.entities.OrderLine;
import com.globant.training.eggshoopingv1.entities.Product;
import com.globant.training.eggshoopingv1.services.ClientService;
import com.globant.training.eggshoopingv1.services.OrderLineService;
import com.globant.training.eggshoopingv1.services.OrderService;
import com.globant.training.eggshoopingv1.services.ProductService;
import com.globant.training.eggshoopingv1.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LoadDatabase {
    public static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ClientService clientService, OrderService orderService, ProductService productService, OrderLineService orderLineService) {
        return args -> {
            Client client1 = new Client("Bilbo",  "Baggins", "juan234", 20);
            Client client2 = new Client("Frodo", "Baggins", "pedro555", 23);
            client1.setId(1L);
            client2.setId(2L);
            log.info("Preloading Client " + clientService.createNewClient(client1));
            log.info("Preloading Client " + clientService.createNewClient(client2));

            Order order1 = new Order(Status.IN_PROGRESS, "Pedido 1");
            Order order2 = new Order(Status.IN_PROGRESS, "Pedido 1");
            order1.setId(1L);
            order2.setId(2L);
            log.info("Preloading Order " + orderService.createNewOrder(1L, order1));
            log.info("Preloading Order " + orderService.createNewOrder(2L, order2));

            Product product1 = new Product("Coca Cola", "Coca Cola de 2,5 litros", 180.0f, 500);
            Product product2 = new Product("Agua Mineral", "Agua Mineral de 2,5 litros", 100.0f, 300);
            product1.setId(1L);
            product2.setId(2L);
            log.info("Preloading Product " + productService.createNewProduct(product1));
            log.info("Preloading Product " + productService.createNewProduct(product2));

            OrderLine orderLine1 = new OrderLine(23, order1, product1);
            OrderLine orderLine2 = new OrderLine(50, order1, product2);
            OrderLine orderLine3 = new OrderLine(14, order2, product1);
            orderLine1.setId(1L);
            orderLine2.setId(2L);
            orderLine3.setId(3L);
            log.info("Preloading orderLine " + orderLineService.createNewOrderLine(orderLine1.getOrder(), orderLine1.getProduct().getId(), orderLine1));
            log.info("Preloading orderLine " + orderLineService.createNewOrderLine(orderLine2.getOrder(), orderLine2.getProduct().getId(), orderLine2));
            log.info("Preloading orderLine " + orderLineService.createNewOrderLine(orderLine3.getOrder(), orderLine3.getProduct().getId(), orderLine3));
        };
    }

}
