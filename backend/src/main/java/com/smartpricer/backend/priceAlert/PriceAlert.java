package com.smartpricer.backend.priceAlert;

import com.smartpricer.backend.product.Product;
import com.smartpricer.backend.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_alerts")
public class PriceAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long priceAlertId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal targetPrice;

    private LocalDateTime createdAt;

    public PriceAlert() {}

    public PriceAlert(User user, Product product, BigDecimal targetPrice, LocalDateTime createdAt) {
        this.user = user;
        this.product = product;
        this.targetPrice = targetPrice;
        this.createdAt = createdAt;
    }

    public Long getPriceAlertId() {
        return priceAlertId;
    }
    public void setPriceAlertId(Long priceAlertId) {
        this.priceAlertId = priceAlertId;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
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
    public void setcreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }




}
