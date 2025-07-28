package laryssa.bino.products_api.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import laryssa.bino.products_api.model.Product;
import laryssa.bino.products_api.service.StorageService;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private final StorageService storage;

    public ProductController(StorageService storage) {
        this.storage = storage;
    }

    @PutMapping("/{store_name}")
    public ResponseEntity<?> saveProducts(
        @PathVariable("store_name") String storeName,
        @RequestBody @Size(min = 1, max = 10) List<@Valid Product> products) {
        try {
            storage.save(storeName, products);
            return ResponseEntity.ok("Products saved successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error saving: " + e.getMessage());
        }
    }

    @GetMapping("/{store_name}")
    public ResponseEntity<?> getProducts(@PathVariable("store_name") String storeName) {
        try {
            return ResponseEntity.ok(storage.load(storeName));
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(404).body("Store not found.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error loading data.");
        }
    }
}

