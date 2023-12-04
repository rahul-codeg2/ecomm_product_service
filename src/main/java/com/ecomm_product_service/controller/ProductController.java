package com.ecomm_product_service.controller;

import com.ecomm_product_service.model.Product;
import com.ecomm_product_service.dto.ProductStockResponse;
import com.ecomm_product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController
{
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product> >getAllProducts(@RequestHeader("Authorization") String token)
    {
        return productService.getAllProducts(token);

    }
    @PostMapping("/check-stock")
    public ResponseEntity<List<ProductStockResponse>> getProductById(@RequestBody List<Integer> product_ids)
    {

        return productService.getProductsById(product_ids);

    }
    @PostMapping("/reduce-stock")
    public void reduceStock(@RequestBody List<ProductStockResponse> productListWithAvailableStock )
    {
        productService.reduceStock(productListWithAvailableStock);

    }

}
