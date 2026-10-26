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

            ProductStore createdProductStore = productStoreService.createProductStore(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ProductStoreDTO(createdProductStore));

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
    public ResponseEntity<ProductStoreDTO> updateProductStore(@PathVariable Long productStoreId, @Valid @RequestBody UpdateProductStoreRequest request) {

        ProductStore productStore = productStoreService.updateProductStore(productStoreId, request);
        return ResponseEntity.ok(new ProductStoreDTO(productStore));

    }




}
