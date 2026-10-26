package com.smartpricer.backend.priceHistory;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceHistoryDTO {
    private Long productStoreId;
    private Long priceHistoryId;
    private BigDecimal price;
    private LocalDateTime recordedAt;

    public PriceHistoryDTO(){}

    public PriceHistoryDTO(PriceHistory priceHistory) {
        this.productStoreId = priceHistory.getProductStore().getProductStoreId();
        this.priceHistoryId = priceHistory.getPriceHistoryId();
        this.price = priceHistory.getPrice();
        this.recordedAt = priceHistory.getRecordedAt();
    }

    public Long getProductStoreId() {
        return productStoreId;
    }
    public void setProductStoreId(Long productStoreId) {
        this.productStoreId = productStoreId;
    }

    public Long getPriceHistoryId() {
        return priceHistoryId;
    }
    public void setPriceHistoryId(Long priceHistoryId) {
        this.priceHistoryId = priceHistoryId;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }
    public void setRecordedAt(LocalDateTime recordedAt) {
        this.recordedAt = recordedAt;
    }

}
