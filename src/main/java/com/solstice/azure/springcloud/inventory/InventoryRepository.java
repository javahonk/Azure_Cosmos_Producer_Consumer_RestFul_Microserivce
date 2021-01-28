package com.solstice.azure.springcloud.inventory;

import com.microsoft.azure.spring.data.cosmosdb.repository.CosmosRepository;

public interface InventoryRepository extends CosmosRepository<Inventory, String> {
}
