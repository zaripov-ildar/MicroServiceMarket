package ru.gb.zaripov.cart.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal totalPrice;

    public void incrementQuantity() {
        quantity++;
        totalPrice = totalPrice.add(pricePerProduct);
    }
}
