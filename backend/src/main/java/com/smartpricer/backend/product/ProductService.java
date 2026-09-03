package com.smartpricer.backend.product;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }


    public boolean deleteProduct (Long productId){
        if(productRepository.existsById(productId)){
            productRepository.deleteById(productId);
            return true;
        }

        return false;
    }

    public Optional<Product> updateProduct(Long productId, Product updatedProduct) {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setProductName(updatedProduct.getProductName());
                product.setProductType(updatedProduct.getProductType());
                return Optional.of(productRepository.save(product));
            }
        return Optional.empty();
    }


}