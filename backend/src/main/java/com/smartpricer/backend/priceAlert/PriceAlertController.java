package com.smartpricer.backend.priceAlert;

import com.smartpricer.backend.priceHistory.PriceHistory;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price-alerts")
public class PriceAlertController {

    private PriceAlertService priceAlertService;

    public PriceAlertController(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @PostMapping
    public ResponseEntity<PriceAlertDTO> createPriceAlert(@Valid @RequestBody CreatePriceAlertRequest request) {

        PriceAlert priceAlert = priceAlertService.createPriceAlert(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PriceAlertDTO(priceAlert));

    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PriceAlertDTO>> getPriceAlertsForUser(@PathVariable Long userId) {

        List<PriceAlertDTO> priceAlertDTOs = priceAlertService.getPriceAlertsForUser(userId);

        return ResponseEntity.ok(priceAlertDTOs);

    }

    @GetMapping("/{priceAlertId}/matching-stores")
    public ResponseEntity<List<MatchingStoreDTO>> getMatchingStores(@PathVariable Long priceAlertId) {

        List<MatchingStoreDTO> matchingStoreDTOs = priceAlertService.getMatchingStores(priceAlertId);

        return ResponseEntity.ok(matchingStoreDTOs);

    }

    @DeleteMapping("/{priceAlertId}")
    public ResponseEntity<Void> deletePriceAlert(@PathVariable Long priceAlertId) {

        boolean result = priceAlertService.deletePriceAlert(priceAlertId);

        if (result) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }



}
