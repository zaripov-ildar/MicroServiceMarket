package ru.gb.zaripov.cart.converters;

import org.springframework.stereotype.Component;
import ru.gb.zaripov.api.CartItemDto;
import ru.gb.zaripov.cart.utils.CartItem;

@Component
public class CartItemConverter {

    public CartItemDto toCartItemDto(CartItem cartItem) {
        return new CartItemDto(
                cartItem.getProductId(),
                cartItem.getProductTitle(),
                cartItem.getPricePerProduct(),
                cartItem.getTotalPrice(),
                cartItem.getQuantity()
        );
    }
}
