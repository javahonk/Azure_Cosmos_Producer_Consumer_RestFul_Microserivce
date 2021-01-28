package com.solstice.azure.springcloud.order;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {

    @Value("${producer.endpoint}")
    private String producerEndPoint;

    public OrderController(InventoryServiceClient inventoryServiceClient) {
    }

    @PostMapping("/order")
    public ResponseEntity<String> submitOrder(@RequestBody List<Order> orders) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        List<Inventory> inventory = createInventoryRequest(orders);
        ResponseEntity<String> result = restTemplate.postForEntity(new URI(producerEndPoint), inventory, String.class);
        return new ResponseEntity<>("Order Submitted Successfully: ", HttpStatus.OK);
    }

    private List<Inventory> createInventoryRequest(List<Order> orders) {
        List<Inventory> inventories = new ArrayList<>();
        orders.forEach(order -> inventories.add(Inventory.builder()
                .id(order.getInventoryId())
                .productName(order.getProductName())
                .productCategory(order.getProductCategory())
                .quantity(order.getQuantity())
                .build()));

        return inventories;
    }
}
