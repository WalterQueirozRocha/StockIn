package com.otaviowalter.stockin.dto.stock;

import com.otaviowalter.stockin.dto.products.ProductStockDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {

    private ProductStockDTO product;
    private int currentQuantity;
}