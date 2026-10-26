package com.smartpricer.backend.priceAlert;

import com.smartpricer.backend.exception.ResourceNotFoundException;
import com.smartpricer.backend.product.Product;
import com.smartpricer.backend.product.ProductRepository;
import com.smartpricer.backend.productstore.ProductStore;
import com.smartpricer.backend.productstore.ProductStoreRepository;
import com.smartpricer.backend.user.User;
import com.smartpricer.backend.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceAlertService {

    private final PriceAlertRepository priceAlertRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductStoreRepository productStoreRepository;

    public PriceAlertService(PriceAlertRepository priceAlertRepository, UserRepository userRepository, ProductRepository productRepository,  ProductStoreRepository productStoreRepository) {
        this.priceAlertRepository = priceAlertRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productStoreRepository = productStoreRepository;
    }

    @Transactional
    public PriceAlert createPriceAlert(CreatePriceAlertRequest request, Long authenticatedId) {

        Optional<User> OptionalUser = userRepository.findById(authenticatedId);
        Optional<Product> OptionalProduct = productRepository.findById(request.getProductId());

        if (OptionalUser.isEmpty()){
            throw new ResourceNotFoundException("User id not found with id " + authenticatedId);
        }

        if (OptionalProduct.isEmpty()){
            throw new ResourceNotFoundException("Product id not found with id " + request.getProductId());
        }

        User user = OptionalUser.get();
        Product product = OptionalProduct.get();

        PriceAlert priceAlert = new PriceAlert(user, product, request.getTargetPrice(), LocalDateTime.now());

        return priceAlertRepository.save(priceAlert);

    }

    @Transactional(readOnly = true)
    public List<PriceAlertDTO> getPriceAlertsForUser(Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()){
            throw new ResourceNotFoundException("User id not found with id " + userId);
        }

       List<PriceAlertDTO> priceAlertDTOList = new ArrayList<>();
       List<PriceAlert> priceAlertList = priceAlertRepository.findAllByUserUserId(userId);

       for (PriceAlert priceAlert : priceAlertList){
           priceAlertDTOList.add(new PriceAlertDTO(priceAlert));
       }

       return priceAlertDTOList;
    }

    public boolean deletePriceAlert(Long  priceAlertId, Long authenticatedUserId) throws AccessDeniedException {

        Optional<PriceAlert> OptionalpriceAlert = priceAlertRepository.findById(priceAlertId);

        if (OptionalpriceAlert.isEmpty()){
            return false;
        }

        PriceAlert priceAlert = OptionalpriceAlert.get();

        if (!priceAlertRepository.existsById(priceAlertId)){
            return false;
        }

        if (!priceAlert.getUser().getUserId().equals(authenticatedUserId)){
            throw new AccessDeniedException("You do not own this price alert");
        }

        priceAlertRepository.deleteById(priceAlertId);

        return true;
    }

    @Transactional(readOnly = true)
    public List<MatchingStoreDTO> getMatchingStores(Long priceAlertId, Long authenticatedUserId) throws AccessDeniedException {

        PriceAlert priceAlert = priceAlertRepository.findById(priceAlertId).orElseThrow(() ->
        new ResourceNotFoundException("Price alert not found with id " + priceAlertId));

        Long userId = priceAlert.getUser().getUserId();

        if (!userId.equals(authenticatedUserId)){
            throw new AccessDeniedException("You do not own this price alert");
        }

        List<MatchingStoreDTO> matchingStoreDTOS = new ArrayList<>();

        for (ProductStore productStore : productStoreRepository.findAllByProductProductId(priceAlert.getProduct().getProductId())) {

            if (productStore.getPrice().compareTo(priceAlert.getTargetPrice()) <= 0) {
                matchingStoreDTOS.add(new MatchingStoreDTO(productStore));
            }

        }

        return matchingStoreDTOS;

    }








}
