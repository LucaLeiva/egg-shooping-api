package com.globant.training.eggshoopingv1.services;

import com.globant.training.eggshoopingv1.entities.Order;
import com.globant.training.eggshoopingv1.entities.OrderLine;
import com.globant.training.eggshoopingv1.entities.Product;
import com.globant.training.eggshoopingv1.exception.*;
import com.globant.training.eggshoopingv1.repository.OrderLineRepository;
import com.globant.training.eggshoopingv1.repository.OrderRepository;
import com.globant.training.eggshoopingv1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// pensar en diseñar antes, con casos de usos por ejemplo

@Service
@RequiredArgsConstructor
public class OrderLineService {
    @Autowired
    private final OrderLineRepository orderLineRepository;
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final ProductRepository productRepository;


    // obtener todas las lineas de ordenes
    public List<OrderLine> getAllOrderLines(){
        return orderLineRepository.findAll();
    }

    // obtener una linea de orden
    public OrderLine getOneOrderLineById(Long id){
        return orderLineRepository.findById(id)
                .orElseThrow(() -> new OrderLineNotFoundException(id));
    }

    // obtener todas las lineas de ordenes de una orden
    public List<OrderLine> getAllOrderLinesOfOneOrder(Long id_order){
        Order order = orderRepository.findById(id_order)
                .orElseThrow(() -> new OrderNotFoundException(id_order));
        return order.getOrderLines();
    }

    // crear una nueva linea de orden
    public OrderLine createNewOrderLine(Long id_order, Long id_product, OrderLine newOrderLine){
        if(!(orderLineRepository.existsById(newOrderLine.getId()))){
            newOrderLine.setOrder(
                    orderRepository.findById(id_order)
                            .orElseThrow(() -> new OrderNotFoundException(id_order))
            );
            Product product = productRepository.findById(id_product)
                    .orElseThrow(() -> new ProductNotFoundException(id_product));

            // esto reduce el stock existente del producto, restandole lo que necesita el orderLine
            if(newOrderLine.getAmmountOfProduct() <= product.getAmmountInStock()){
                product.setAmmountInStock(product.getAmmountInStock() - newOrderLine.getAmmountOfProduct());
                productRepository.save(product);
                newOrderLine.setProduct(product);
            }else{
                throw new InsufficientStockException(product.getName());
            }

            return orderLineRepository.save(newOrderLine);
        }else{
            throw new OrderLineAlreadyExistsException(newOrderLine.getId());
        }

    }

    // modifico una linea de orden existente o creo una nueva si no existe
    public OrderLine modifyAnExistentOrderLineOrCreateNewOneIfItDoesntExist(OrderLine newOrderLine, Long id_product, Long id_order){
        return (orderLineRepository.findById(newOrderLine.getId())
                .map(orderLine -> {
                    Product product = productRepository.findById(id_product)
                                    .orElseThrow(() -> new ProductNotFoundException(id_product));

                    if(newOrderLine.getAmmountOfProduct() > 0){
                        if(newOrderLine.getAmmountOfProduct() > orderLine.getAmmountOfProduct()){
                            if(newOrderLine.getAmmountOfProduct() <= product.getAmmountInStock()){
                                product.setAmmountInStock(product.getAmmountInStock() - (newOrderLine.getAmmountOfProduct() - orderLine.getAmmountOfProduct()));
                                productRepository.save(product);
                            }else{
                                throw new InsufficientStockException(product.getName());
                            }
                        } else{
                            product.setAmmountInStock(product.getAmmountInStock() + (orderLine.getAmmountOfProduct() - newOrderLine.getAmmountOfProduct()));
                            productRepository.save(product);
                        }
                        orderLine.setAmmountOfProduct(newOrderLine.getAmmountOfProduct());
                    }

                    // no permito que se modifique el producto
                    // no permito que se modifique la orden
                    return orderLineRepository.save(orderLine);
                })
                .orElseGet(() -> {
                    newOrderLine.setId(newOrderLine.getId());
                    newOrderLine.setOrder(
                            orderRepository.findById(id_order)
                                    .orElseThrow(() -> new OrderNotFoundException(id_order))
                    );

                    Product product = productRepository.findById(id_product)
                            .orElseThrow(() -> new ProductNotFoundException(id_product));

                    if(newOrderLine.getAmmountOfProduct() <= product.getAmmountInStock()){
                        product.setAmmountInStock(product.getAmmountInStock() - newOrderLine.getAmmountOfProduct());
                        productRepository.save(product);
                        newOrderLine.setProduct(product);
                    }else{
                        throw new InsufficientStockException(product.getName());
                    }

                    return orderLineRepository.save(newOrderLine);
                })
        );
    }

    public void deleteOrderLineById(Long id){
            OrderLine orderLine = orderLineRepository.findById(id)
                            .orElseThrow(() -> new OrderLineNotFoundException(id));
            Product product = productRepository.findById(orderLine.getProduct().getId())
                            .orElseThrow(() -> new ProductNotFoundException(orderLine.getProduct().getId()));
            product.setAmmountInStock(
                    product.getAmmountInStock() + orderLine.getAmmountOfProduct()
            );
            productRepository.save(product);
            orderLineRepository.deleteById(id);
    }
}
