package com.example.lab9productservice.query;

import com.example.lab11core.core.command.ReserveProductCommand;
import com.example.lab11core.core.event.ProductReservedEvent;
import com.example.lab9productservice.core.ProductEntity;
import com.example.lab9productservice.core.data.ProductRepository;
import com.example.lab9productservice.core.event.ProductCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductEventsHandler {

    private final ProductRepository productRepository;

    public ProductEventsHandler(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event){
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(event, productEntity);
        productRepository.save(productEntity);
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent){
        System.out.println("ติด0");
        ProductEntity productEntity = productRepository.findByProductId(productReservedEvent.getProductId());
        System.out.println("ติด1");
        productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
        System.out.println("ติด2");
        productRepository.save(productEntity);
    }
}
