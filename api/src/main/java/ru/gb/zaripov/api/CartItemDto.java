package ru.gb.zaripov.api;

import java.math.BigDecimal;

public class CartItemDto {
    private Long productId;
    private String productTitle;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private int quantity;

    public CartItemDto(Long productId, String productTitle, BigDecimal price, BigDecimal totalPrice, int quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.price = price;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public CartItemDto() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
