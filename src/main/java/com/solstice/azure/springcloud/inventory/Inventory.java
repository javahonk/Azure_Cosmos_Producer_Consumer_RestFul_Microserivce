package com.solstice.azure.springcloud.inventory;

import com.microsoft.azure.spring.data.cosmosdb.core.mapping.PartitionKey;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Inventory {
    private String id;
    private String productCategory;
    private String productName;
    private Integer quantity;

}
