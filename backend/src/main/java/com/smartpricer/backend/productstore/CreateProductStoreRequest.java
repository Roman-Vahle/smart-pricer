package com.smartpricer.backend.productstore;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class CreateProductStoreRequest {

    @NotNull
    @Positive
    private Long productId;

    @NotNull
    @Positive
    private Long storeId;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    public CreateProductStoreRequest() {}

    public CreateProductStoreRequest(Long productId, Long storeId, BigDecimal price) {
        this.productId = productId;
        this.storeId = storeId;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getStoreId() {
        return storeId;
    }
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
