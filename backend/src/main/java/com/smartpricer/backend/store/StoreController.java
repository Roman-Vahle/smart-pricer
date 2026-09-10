package com.smartpricer.backend.store;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/stores")

public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List <Store> getAllStores() {
        return storeService.getAllStores();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Store createStore(@RequestBody Store store) {
        return storeService.createStore(store);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long storeId) {
        Optional<Store> store = storeService.getStoreById(storeId);

        if (store.isPresent()) {
            return ResponseEntity.ok(store.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long storeId) {

        if(storeService.deleteStore(storeId)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<Store> updateStore(@PathVariable Long storeId, @RequestBody Store updatedStore) {

        Optional<Store> store = storeService.updateStore(storeId, updatedStore);

        if (store.isPresent()) {
            return ResponseEntity.ok(store.get());
        }
        return ResponseEntity.notFound().build();
    }

}