package com.smartpricer.backend.priceAlert;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceAlertDTO {

    private Long priceAlertId;
    private Long userId;
    private Long productId;
    private BigDecimal targetPrice;
    private LocalDateTime createdAt;

    public PriceAlertDTO() {}

    public PriceAlertDTO(PriceAlert priceAlert) {
        this.priceAlertId = priceAlert.getPriceAlertId();
        this.userId = priceAlert.getUser().getUserId();
        this.productId = priceAlert.getProduct().getProductId();
        this.targetPrice = priceAlert.getTargetPrice();
        this.createdAt = priceAlert.getCreatedAt();
    }

    public Long getPriceAlertId() {
        return priceAlertId;
    }
    public void setPriceAlertId(Long priceAlertId) {
        this.priceAlertId = priceAlertId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }
    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
