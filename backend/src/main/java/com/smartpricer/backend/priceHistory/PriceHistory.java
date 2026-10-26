package com.smartpricer.backend.priceHistory;

import com.smartpricer.backend.productstore.ProductStore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table (name = "price_histories")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceHistoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "product_store_id")
    private ProductStore productStore;

    private BigDecimal price;

    private LocalDateTime recordedAt;

    public PriceHistory() {}

    public PriceHistory(ProductStore productStore, BigDecimal price, LocalDateTime recordedAt) {
        this.productStore = productStore;
        this.price = price;
        this.recordedAt = recordedAt;
    }

    public Long getPriceHistoryId() {
        return priceHistoryId;
    }

    public void setPrice (BigDecimal price) {
        this.price = price;
    }
    public BigDecimal getPrice() {
        return price;
    }

    public ProductStore getProductStore() {
        return productStore;
    }
    public void setProductStore(ProductStore productStore) {
        this.productStore = productStore;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }
    public void setRecordedAt(LocalDateTime recordedAt){
        this.recordedAt = recordedAt;
    }


}
