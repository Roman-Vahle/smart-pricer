package com.smartpricer.backend.priceHistory;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/price-histories")
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    public PriceHistoryController(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    @GetMapping
    public List<PriceHistory> getAllPriceHistories() {
        return priceHistoryService.getAllPriceHistories();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PriceHistory createPriceHistory(@RequestBody PriceHistory priceHistory) {
        return priceHistoryService.createPriceHistory(priceHistory);
    }

    @GetMapping ("/{priceHistoryId}")
    public ResponseEntity<PriceHistory> getPriceHistoryById (@PathVariable Long priceHistoryId){
        Optional<PriceHistory> priceHistory = priceHistoryService.getPriceHistoryById(priceHistoryId);

        if(priceHistory.isPresent()){
            return ResponseEntity.ok(priceHistory.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping ("/{priceHistoryId}")
    public ResponseEntity<PriceHistory> deletePriceHistoryById (@PathVariable Long priceHistoryId){

        if (priceHistoryService.deletePriceHistory(priceHistoryId)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping ("/product-store/{productStoreId}")
    public List<PriceHistoryDTO>  getPriceHistoryForProductStore(@PathVariable Long productStoreId){

        List<PriceHistoryDTO> priceHistoryDTOs = new ArrayList<>();

        for (PriceHistory priceHistory : priceHistoryService.getPriceHistoriesForProductStore(productStoreId)){
            PriceHistoryDTO priceHistoryDTO = new PriceHistoryDTO(priceHistory);
            priceHistoryDTOs.add(priceHistoryDTO);
        }
        return priceHistoryDTOs;
    }

}
