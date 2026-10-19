package com.smartpricer.backend.priceAlert;

import com.smartpricer.backend.productstore.ProductStore;

import java.math.BigDecimal;

public class MatchingStoreDTO {

    private Long storeId;
    private String storeName;
    private BigDecimal currentPrice;

    public MatchingStoreDTO() {}

    public MatchingStoreDTO(ProductStore productStore) {
        this.storeId = productStore.getStore().getStoreId();
        this.storeName = productStore.getStore().getStoreName();
        this.currentPrice = productStore.getPrice();
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

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }
    public void setCurrentPrice(BigDecimal currentPrice) {
        currentPrice = currentPrice;
    }

}
