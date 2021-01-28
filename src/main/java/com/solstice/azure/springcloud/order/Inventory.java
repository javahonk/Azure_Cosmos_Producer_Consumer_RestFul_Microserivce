package com.solstice.azure.springcloud.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Inventory {
    private String id;
    private String productCategory;
    private String productName;
    private Integer quantity;
}
