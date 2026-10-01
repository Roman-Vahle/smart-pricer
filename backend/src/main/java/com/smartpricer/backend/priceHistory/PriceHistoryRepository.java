package com.smartpricer.backend.priceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    List<PriceHistory> findByProductStoreProductStoreIdOrderByRecordedAtAsc(Long productStoreId);

}