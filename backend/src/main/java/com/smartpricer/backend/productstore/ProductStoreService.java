package com.smartpricer.backend.productstore;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductStoreService {

    private final ProductStoreRepository productStoreRepository;

    public ProductStoreService(ProductStoreRepository productStoreRepository) {
        this.productStoreRepository = productStoreRepository;
    }

    public List<ProductStore> getAllProductStores() {
        return productStoreRepository.findAll();
    }

    public Optional<ProductStore> findById(Long id) {
        return productStoreRepository.findById(id);
    }

    public ProductStore createProductStore(ProductStore productStore) {
        return productStoreRepository.save(productStore);
    }


    public Optional<ProductStore> updateProductStore(Long productStoreId, ProductStore updatedProductStore) {
        Optional<ProductStore> optionalProductStore = productStoreRepository.findById(productStoreId);
        if (optionalProductStore.isPresent()) {
            ProductStore productStore = optionalProductStore.get();
            productStore.setPrice(updatedProductStore.getPrice());
            productStore.setProduct(updatedProductStore.getProduct());
            productStore.setStore(updatedProductStore.getStore());
            return Optional.of(productStoreRepository.save(productStore));
        }
        return Optional.empty();
    }

    public boolean deleteProductStore(Long productStoreId) {
        if(productStoreRepository.existsById(productStoreId)){
            productStoreRepository.deleteById(productStoreId);
            return true;
        }

        return false;
    }

}
