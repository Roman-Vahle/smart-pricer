package com.smartpricer.backend.priceHistory;

import com.smartpricer.backend.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
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
    public List<PriceHistoryDTO> getAllPriceHistories() {
        List<PriceHistoryDTO> priceHistoryDTOs = new ArrayList<>();
        for (PriceHistory priceHistory : priceHistoryService.getAllPriceHistories()) {
            priceHistoryDTOs.add(new PriceHistoryDTO(priceHistory));
        }
        return priceHistoryDTOs;
    }


    @GetMapping ("/{priceHistoryId}")
    public PriceHistoryDTO getPriceHistoryById (@PathVariable Long priceHistoryId){
        PriceHistoryDTO priceHistoryDTO = new PriceHistoryDTO(priceHistoryService.getPriceHistoryById(priceHistoryId));
        return priceHistoryDTO;
    }

    @GetMapping ("/product-store/{productStoreId}")
    public List<PriceHistoryDTO>  getPriceHistoryForProductStore(@PathVariable Long productStoreId){

        List<PriceHistoryDTO> priceHistoryDTOs = new ArrayList<>();

        for (PriceHistory priceHistory : priceHistoryService.getPriceHistoriesForProductStore(productStoreId)){
            priceHistoryDTOs.add(new PriceHistoryDTO(priceHistory));
        }
        return priceHistoryDTOs;
    }

}
