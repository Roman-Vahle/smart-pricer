package com.smartpricer.backend.productstore;
import com.smartpricer.backend.exception.ResourceConflictException;
import com.smartpricer.backend.exception.ResourceNotFoundException;
import com.smartpricer.backend.user.User;
import com.smartpricer.backend.user.UserDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
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
    public ResponseEntity<ProductStoreDTO> createProductStore(@Valid @RequestBody CreateProductStoreRequest request) {

        try {
            ProductStore createdProductStore = productStoreService.createProductStore(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ProductStoreDTO(createdProductStore));
        }
        catch (ResourceConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{productStoreId}")
    public ResponseEntity<Void> deleteProductStore(@PathVariable Long productStoreId) {

        if(productStoreService.deleteProductStore(productStoreId)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping
    public List<ProductStoreDTO> getAllProductStores() {
        List<ProductStoreDTO> productStoreDTOs = new ArrayList<>();
        for (ProductStore productStore : productStoreService.getAllProductStores()) {
            ProductStoreDTO productStoreDTO = new ProductStoreDTO(productStore);
            productStoreDTOs.add(productStoreDTO);
        }
        return productStoreDTOs;
    }

    @GetMapping ("/{productStoreId}")
    public ResponseEntity<ProductStoreDTO> getProductStoreById(@PathVariable Long productStoreId) {

        Optional <ProductStore> productStore = productStoreService.findById(productStoreId);

        if (productStore.isPresent()) {
            return ResponseEntity.ok(new ProductStoreDTO(productStore.get()));
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
