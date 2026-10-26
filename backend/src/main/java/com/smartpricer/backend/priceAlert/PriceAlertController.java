package com.smartpricer.backend.priceAlert;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/price-alerts")
public class PriceAlertController {

    private PriceAlertService priceAlertService;

    public PriceAlertController(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @PostMapping
    public ResponseEntity<PriceAlertDTO> createPriceAlert(@Valid @RequestBody CreatePriceAlertRequest request
            , @AuthenticationPrincipal Jwt jwt) {

        Long authenticatedId = Long.valueOf(jwt.getSubject());

        PriceAlert priceAlert = priceAlertService.createPriceAlert(request, authenticatedId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new PriceAlertDTO(priceAlert));

    }

    @GetMapping("/me")
    public ResponseEntity<List<PriceAlertDTO>> getPriceAlertsForUser(@AuthenticationPrincipal Jwt jwt) {

        Long authenticatedUserId = Long.valueOf(jwt.getSubject());

        List<PriceAlertDTO> priceAlertDTOs = priceAlertService.getPriceAlertsForUser(authenticatedUserId);

        return ResponseEntity.ok(priceAlertDTOs);

    }

    @GetMapping("/{priceAlertId}/matching-stores")
    public ResponseEntity<List<MatchingStoreDTO>> getMatchingStores(@PathVariable Long priceAlertId, @AuthenticationPrincipal Jwt jwt) throws AccessDeniedException {

        Long authenticatedUserId = Long.valueOf(jwt.getSubject());

        List<MatchingStoreDTO> matchingStoreDTOs = priceAlertService.getMatchingStores(priceAlertId,  authenticatedUserId);

        return ResponseEntity.ok(matchingStoreDTOs);

    }

    @DeleteMapping("/{priceAlertId}")
    public ResponseEntity<Void> deletePriceAlert(@PathVariable Long priceAlertId, @AuthenticationPrincipal Jwt jwt) throws AccessDeniedException {

        Long authenticatedUserId = Long.valueOf(jwt.getSubject());

        boolean result = priceAlertService.deletePriceAlert(priceAlertId, authenticatedUserId);

        if (result) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();

    }

}
