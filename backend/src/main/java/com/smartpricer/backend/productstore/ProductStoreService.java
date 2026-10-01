package com.smartpricer.backend.productstore;

import com.smartpricer.backend.exception.ResourceConflictException;
import com.smartpricer.backend.exception.ResourceNotFoundException;
import com.smartpricer.backend.product.Product;
import com.smartpricer.backend.product.ProductRepository;
import com.smartpricer.backend.store.Store;
import com.smartpricer.backend.store.StoreRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductStoreService {

    private final ProductStoreRepository productStoreRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    public ProductStoreService(ProductStoreRepository productStoreRepository, ProductRepository productRepository, StoreRepository storeRepository) {
        this.productStoreRepository = productStoreRepository;
        this.productRepository = productRepository;
        this.storeRepository = storeRepository;
    }

    public List<ProductStore> getAllProductStores() {
        return productStoreRepository.findAll();
    }

    public Optional<ProductStore> findById(Long id) {
        return productStoreRepository.findById(id);
    }

    public ProductStore createProductStore(CreateProductStoreRequest request) {

        Optional<Product> optionalProduct = productRepository.findById(request.getProductId());
        Optional<Store> optionalStore = storeRepository.findById(request.getStoreId());

        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("Product does not exist.");
        }

        if (optionalStore.isEmpty()) {
            throw new ResourceNotFoundException("Store does not exist.");
        }

        Product product = optionalProduct.get();
        Store store = optionalStore.get();

        if (productStoreRepository.existsByProductAndStore(product, store)){
            throw new ResourceConflictException("ProductStore already exists.");
        }

        ProductStore productStore = new ProductStore(product, store, request.getPrice());
        return  productStoreRepository.save(productStore);

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
