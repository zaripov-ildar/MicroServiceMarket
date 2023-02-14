package ru.gb.zaripov.cart.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CartItemTest {

    @Test
    void incrementQuantity() {
        int quantity = 13;
        CartItem cartItem = new CartItem(1L, "title", 1, BigDecimal.ONE, BigDecimal.ONE);
        for (int i = 2; i <= quantity; i++) {
            cartItem.incrementQuantity();
            assertEquals(
                    new CartItem(1L,"title", i, BigDecimal.ONE,BigDecimal.valueOf(i)),
                    cartItem);
        }
    }
}