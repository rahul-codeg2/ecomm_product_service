package com.ecomm_product_service.service;

import com.ecomm_product_service.dto.UserResponse;
import com.ecomm_product_service.model.Product;
import com.ecomm_product_service.dto.ProductStockResponse;
import com.ecomm_product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService
{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WebClient webClient;



    public ResponseEntity<List<Product>> getAllProducts()
    {
        List<Product> list=productRepository.findAllProducts();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    public ResponseEntity<List<ProductStockResponse>> getProductsById(List<Integer> product_ids)
    {
        List<ProductStockResponse> productList=new ArrayList<>();
        for (Integer productId : product_ids) {
            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                ProductStockResponse productStockResponse = new ProductStockResponse(product.getProduct_id(), product.getStock(),product.getPrice());
                productList.add(productStockResponse);
            }
        }

        return ResponseEntity.ok(productList);
    }

    public void reduceStock(List<ProductStockResponse> productListWithAvailableStock)
    {
        try
        {
          for(ProductStockResponse p:productListWithAvailableStock)
          {
              Optional<Product> optionalProduct = productRepository.findById(p.getProduct_id());
              if (optionalProduct.isPresent()) {
                  Product product = optionalProduct.get();
                  product.setStock(product.getStock()-1);
                  productRepository.save(product);
              }
          }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public UserResponse authenticate(String token)
    {
        String userServiceUrl = "http://localhost:9000/validate-token";

        UserResponse userResponse=webClient.post().uri(userServiceUrl)
            .header("Authorization", token)
            .retrieve()
            .bodyToMono(UserResponse.class).block();


        if(userResponse!=null )
        {
            return userResponse;
        }
        else
        {
            throw new RuntimeException("Invalid JWT token");
        }
    }
}
