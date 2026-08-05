package com.canteen.management.controller;

import com.canteen.management.entity.Inventory;
import com.canteen.management.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inventory")
@CrossOrigin("*")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping("/branch/{branchId}")
    public List<Inventory> getInventoryByBranch(@PathVariable Long branchId) {
        return inventoryRepository.findByBranchId(branchId);
    }

    @PostMapping("/add")
    public Inventory addInventoryItem(@RequestBody Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @PutMapping("/update-stock")
    public ResponseEntity<Inventory> updateStock(@RequestParam Long id, @RequestParam Double quantity) {
        return inventoryRepository.findById(id)
                .map(item -> {
                    item.setQuantity(quantity);
                    Inventory updated = inventoryRepository.save(item);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
