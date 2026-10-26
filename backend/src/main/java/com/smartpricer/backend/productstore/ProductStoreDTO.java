package com.smartpricer.backend.productstore;

import java.math.BigDecimal;

public class ProductStoreDTO {

    private Long productStoreId;
    private Long productId;
    private String productName;
    private Long storeId;
    private String storeName;
    private BigDecimal price;

    public ProductStoreDTO() {}

    public ProductStoreDTO(ProductStore productStore) {
        this.productStoreId = productStore.getProductStoreId();
        this.productId = productStore.getProduct().getProductId();
        this.productName = productStore.getProduct().getProductName();
        this.storeId = productStore.getStore().getStoreId();
        this.storeName = productStore.getStore().getStoreName();
        this.price = productStore.getPrice();
    }

    public Long getProductStoreId() {
        return productStoreId;
    }
    public void setProductStoreId(Long productStoreId) {
        this.productStoreId = productStoreId;
    }

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getStoreId() {
        return storeId;
    }
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
