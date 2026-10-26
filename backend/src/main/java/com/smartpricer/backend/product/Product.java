package com.smartpricer.backend.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long productId;

    @Column(nullable = false, length = 200)

    private String productName;

    @Column(nullable = false, length = 100)
    private String productType;

    public Product(){}

    public Product(String productName, String productType) {
        this.productName = productName;
        this.productType = productType;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }


}