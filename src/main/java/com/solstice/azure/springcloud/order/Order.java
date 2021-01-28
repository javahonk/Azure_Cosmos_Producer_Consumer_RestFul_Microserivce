package com.solstice.azure.springcloud.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {
    private String inventoryId;
    private String productName;
    private String productCategory;
    private Integer quantity;
}
