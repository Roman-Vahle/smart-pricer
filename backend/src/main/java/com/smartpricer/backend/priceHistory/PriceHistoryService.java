package com.smartpricer.backend.priceHistory;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;

    public PriceHistoryService(PriceHistoryRepository priceHistoryRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
    }

    public PriceHistory createPriceHistory(PriceHistory priceHistory) {
        return priceHistoryRepository.save(priceHistory);
    }

    public List<PriceHistory> getAllPriceHistories() {
        return priceHistoryRepository.findAll();
    }

    public Optional<PriceHistory> getPriceHistoryById (Long priceHistoryId) {
        return priceHistoryRepository.findById(priceHistoryId);
    }

    public boolean deletePriceHistory (Long priceHistoryId){
        if (priceHistoryRepository.existsById(priceHistoryId)){
            priceHistoryRepository.deleteById(priceHistoryId);
            return true;
        }
        return false;
    }

    public List<PriceHistory> getPriceHistoriesForProductStore(Long productStoreId) {
        return priceHistoryRepository.findByProductStoreProductStoreIdOrderByRecordedAtAsc(productStoreId);
    }

}
