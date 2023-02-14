package ru.gb.zaripov.cart.utils;

import lombok.Data;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.api.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public void add(ProductDto p) {
        for (CartItem item : items) {
            if (item.getProductId().equals(p.getId())) {
                item.incrementQuantity();
                recalculate();
                return;
            }
        }
        CartItem cartItem = new CartItem(p.getId(), p.getTitle(), 1, p.getPrice(), p.getPrice());
        items.add(cartItem);
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = BigDecimal.ZERO;
    }

    private void recalculate() {
        totalPrice = BigDecimal.ZERO;
        items.forEach(i -> totalPrice = totalPrice.add(i.getTotalPrice()));
    }

    public Cart() {
        items = new ArrayList<>();
        totalPrice = BigDecimal.ZERO;
    }
}
