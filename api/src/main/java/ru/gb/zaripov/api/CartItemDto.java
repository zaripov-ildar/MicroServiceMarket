package ru.gb.zaripov.api;

import java.math.BigDecimal;

public class CartItemDto {
    private Long productId;
    private String productTitle;
    private BigDecimal pricePerProduct;
    private BigDecimal totalPrice;
    private int quantity;

    public CartItemDto(Long productId, String productTitle, BigDecimal pricePerProduct, BigDecimal totalPrice, int quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.pricePerProduct = pricePerProduct;
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

    public BigDecimal getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(BigDecimal pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
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
