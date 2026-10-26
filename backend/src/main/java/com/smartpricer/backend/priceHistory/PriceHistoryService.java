package com.smartpricer.backend.priceHistory;

import com.smartpricer.backend.exception.ResourceConflictException;
import com.smartpricer.backend.exception.ResourceNotFoundException;
import com.smartpricer.backend.productstore.ProductStoreRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;
    private final ProductStoreRepository productStoreRepository;

    public PriceHistoryService(PriceHistoryRepository priceHistoryRepository, ProductStoreRepository productStoreRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
        this.productStoreRepository = productStoreRepository;
    }

    public PriceHistory createPriceHistory(PriceHistory priceHistory) {
        return priceHistoryRepository.save(priceHistory);
    }

    public List<PriceHistory> getAllPriceHistories() {
        return priceHistoryRepository.findAll();
    }

    public PriceHistory getPriceHistoryById(Long priceHistoryId) {
        Optional<PriceHistory> priceHistory = priceHistoryRepository.findById(priceHistoryId);

        if (priceHistory.isPresent()) {
            return priceHistory.get();
        }

        throw new ResourceNotFoundException("Price History Not Found");

    }

    public boolean deletePriceHistory(Long priceHistoryId) {
        if (priceHistoryRepository.existsById(priceHistoryId)) {
            priceHistoryRepository.deleteById(priceHistoryId);
            return true;
        }
        return false;
    }

    public List<PriceHistory> getPriceHistoriesForProductStore(Long productStoreId) {
        if (productStoreRepository.existsById(productStoreId)) {
            return priceHistoryRepository.findByProductStoreProductStoreIdOrderByRecordedAtAsc(productStoreId);
        }
        throw new ResourceNotFoundException("Product store not found");
    }
}
