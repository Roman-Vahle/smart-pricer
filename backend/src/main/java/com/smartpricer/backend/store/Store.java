package com.smartpricer.backend.store;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    @Column(nullable = false, length = 500)
    private String storeName;
    @Column(nullable = false, length = 500)
    private String storeLocation;
    @Column(nullable = false, length = 500)
    private String storeSite;

    public Store(){}

    public Store(String storeName, String storeLocation, String storeSite) {
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.storeSite = storeSite;
    }

    public Long getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getStoreSite() {
        return storeSite;
    }

    public void setStoreSite(String storeSite) {
        this.storeSite = storeSite;
    }




}