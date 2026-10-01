package com.smartpricer.backend.productstore;
import com.smartpricer.backend.product.Product;
import com.smartpricer.backend.store.Store;
import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(
        name = "product_stores",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"product_id", "store_id"})
        }
)
public class ProductStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productStoreId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "product_id")
    private Product product;

    private BigDecimal price;

    public ProductStore() {
    }

    public ProductStore(Product product, Store store, BigDecimal price) {
        this.product = product;
        this.store = store;
        this.price = price;
    }

    public Long getProductStoreId() {
        return productStoreId;
    }

    public Product getProduct () {
        return product;
    }
    public void setProduct (Product product) {
        this.product = product;
    }

    public Store getStore () {
        return store;
    }
    public void setStore (Store store) {
        this.store = store;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice (BigDecimal price) {
        this.price = price;
    }

}
