package ru.gb.zaripov.cart.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.zaripov.api.CartDto;
import ru.gb.zaripov.cart.utils.Cart;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDto toCartDto(Cart currentCart) {
        return new CartDto(
                currentCart.getItems().stream()
                        .map(cartItemConverter::toCartItemDto)
                        .collect(Collectors.toList()),
                currentCart.getTotalPrice()
        );
    }
}
