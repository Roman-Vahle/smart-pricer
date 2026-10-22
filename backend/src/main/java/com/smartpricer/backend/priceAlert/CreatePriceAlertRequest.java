package com.smartpricer.backend.priceAlert;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreatePriceAlertRequest {

    @NotNull
    private Long productId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal targetPrice;

    public CreatePriceAlertRequest() {}

    public CreatePriceAlertRequest(Long productId, BigDecimal targetPrice) {

        this.productId = productId;
        this.targetPrice = targetPrice;
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


}
