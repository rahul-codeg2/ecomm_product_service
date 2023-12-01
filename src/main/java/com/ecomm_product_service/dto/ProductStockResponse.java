package com.ecomm_product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStockResponse
{
    private int product_id;
    private int available_stock;
    private double price;
}
