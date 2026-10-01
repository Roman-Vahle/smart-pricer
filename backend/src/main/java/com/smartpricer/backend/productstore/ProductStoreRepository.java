package com.smartpricer.backend.productstore;
import org.springframework.data.jpa.repository.JpaRepository;
import com.smartpricer.backend.product.Product;
import com.smartpricer.backend.store.Store;

public interface ProductStoreRepository extends JpaRepository<ProductStore, Long> {
    boolean existsByProductAndStore(Product product, Store store);

}
