package com.smartpricer.backend.productstore;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class UpdateProductStoreRequest {

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    public UpdateProductStoreRequest() {}

    public UpdateProductStoreRequest(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
