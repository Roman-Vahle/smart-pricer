package com.smartpricer.backend.store;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Optional<Store> getStoreById(Long id) {
        return storeRepository.findById(id);
    }

    public boolean deleteStore (Long storeId) {
        if (storeRepository.existsById(storeId)) {
            storeRepository.deleteById(storeId);
            return true;
        }
        return false;
    }

    public Optional<Store> updateStore(Long storeId, Store updatedStore) {
        Optional<Store> optionalStore = storeRepository.findById(storeId);
        if (optionalStore.isPresent()) {
            Store store = optionalStore.get();
            store.setStoreName(updatedStore.getStoreName());
            store.setStoreLocation(updatedStore.getStoreLocation());
            store.setStoreSite(updatedStore.getStoreSite());
            return Optional.of(storeRepository.save(store));
        }
        return Optional.empty();
    }

}
