package com.solstice.azure.springcloud.inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InventoryController {

    private final InventoryRepository inventoryRepository;
    private final FeatureFlag featureFlag;

    @Autowired
    private FetchDataByQuery fetchDataByQuery;

    public InventoryController(InventoryRepository repository, FeatureFlag featureFlag) {
        this.inventoryRepository = repository;
        this.featureFlag = featureFlag;
    }

    @PostMapping("/inventory")
    public ResponseEntity<String> updateInventory(@RequestBody List<Inventory> inventory) {
        if(featureFlag.isSaveInventory()) {
            inventoryRepository.saveAll(inventory);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<>("Successfully updated inventory", HttpStatus.OK);
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<Inventory>> getAllInventories() throws InterruptedException {

        fetchDataByQuery.fetchRecordFromDb();
        List<Inventory> inventories = (List<Inventory>) inventoryRepository.findAll();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }
}
