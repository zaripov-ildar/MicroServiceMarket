package ru.gb.zaripov.api;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {
    private List<CartItemDto> cartItems;
    private BigDecimal totalPrice;

    public CartDto(List<CartItemDto> cartItems, BigDecimal totalPrice) {
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }

    public CartDto() {
    }

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
