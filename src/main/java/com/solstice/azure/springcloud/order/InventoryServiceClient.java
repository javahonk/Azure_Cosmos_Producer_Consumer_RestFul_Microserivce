package com.solstice.azure.springcloud.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {

    @PostMapping("/inventory")
    void updateInventory(List<Inventory> inventory);
}
