package com.ecomm_product_service.controller;

import com.ecomm_product_service.model.Product;
import com.ecomm_product_service.model.ProductStockResponse;
import com.ecomm_product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ecomm")
public class ProductController
{
    @Autowired
    private ProductService productService;

    @GetMapping("/home/get-all-products")
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();

    }
    @PostMapping("/home/check-stock")
    public ResponseEntity<List<ProductStockResponse>> getProductById(@RequestBody List<Integer> product_ids)
    {
        return productService.getProductsById(product_ids);

    }
    @PostMapping("/home/reduce-stock")
    public void reduceStock(@RequestBody List<ProductStockResponse> productListWithAvailableStock )
    {
        productService.reduceStock(productListWithAvailableStock);

    }

}
