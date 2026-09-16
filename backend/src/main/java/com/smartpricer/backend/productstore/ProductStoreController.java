package com.smartpricer.backend.productstore;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/productStores")
public class ProductStoreController {

    private final ProductStoreService productStoreService;

    public ProductStoreController(ProductStoreService productStoreService) {
        this.productStoreService = productStoreService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductStore createProductStore(@RequestBody ProductStore productStore) {
        return productStoreService.createProductStore(productStore);
    }

    @DeleteMapping("/{productStoreId}")
    public ResponseEntity<Void> deleteProductStore(@PathVariable Long productStoreId) {

        if(productStoreService.deleteProductStore(productStoreId)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping
    public List<ProductStore> getAllProductStores() {
        return productStoreService.getAllProductStores();
    }

    @GetMapping ("/{productStoreId}")
    public ResponseEntity<ProductStore> getProductStoreById(@PathVariable Long productStoreId) {

        Optional <ProductStore> productStore = productStoreService.findById(productStoreId);

        if (productStore.isPresent()) {
            return ResponseEntity.ok(productStore.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{productStoreId}")
    public ResponseEntity<ProductStore> updateProductStore(@PathVariable Long productStoreId, @RequestBody ProductStore updatedProductStore) {

        Optional <ProductStore> productStore = productStoreService.updateProductStore(productStoreId, updatedProductStore);
        if (productStore.isPresent()) {
            return ResponseEntity.ok(productStore.get());
        }
        return ResponseEntity.notFound().build();
    }


}
