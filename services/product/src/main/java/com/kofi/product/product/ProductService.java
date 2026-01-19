package com.kofi.product.product;

import com.kofi.product.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.netflix.eureka.RestTemplateTimeoutProperties;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public Integer createProduct(@Valid ProductRequest request) {
        var product = mapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        // 1. extracting id's
        var productIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        //2.check whether all the product are available in the database
        var storedProducts = productRepository.findAllByIdInOrderById(productIds);
        //3 check if the size of product id different from the stored one
        //eg if you want to buy product 1,2,3 and there's only 1,3 we throw an exception
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more product does not exist");
        }
        //4 after getting what we want to buy in our database
        var storedRequest = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for(int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i); // check for the available quantity
            var productRequest = storedRequest.get(i); // is it less or equal to the available quantity
            if (product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID:: " + product.getId());
            }
            // update the database with the new quantity left
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProducts.add(mapper.toProductPurchaseResponse(product,productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return productRepository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(()-> new EntityNotFoundException("Product not found with the Id:: " + productId));
    }

    public List<ProductResponse> findAll() {
            return productRepository.findAll()
                    .stream()
                    .map(mapper::toProductResponse)
                    .collect(Collectors.toList());
    }
}
