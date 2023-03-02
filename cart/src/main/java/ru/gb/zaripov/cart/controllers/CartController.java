package ru.gb.zaripov.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.zaripov.api.CartDto;
import ru.gb.zaripov.api.StringResponse;
import ru.gb.zaripov.cart.converters.CartConverter;
import ru.gb.zaripov.cart.services.CartService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/{userCartId}")
    public CartDto getCurrentCart(@PathVariable String userCartId) {
        return cartConverter.toCartDto(cartService.getCurrentCart(userCartId));
    }

    @GetMapping("/{userCartId}/add/{productId}")
    public void addProductToCart(@PathVariable String userCartId, @PathVariable Long productId) {
        cartService.addToCart(userCartId, productId);
    }

    @GetMapping("/clear/{userCartId}")
    public void clear(@PathVariable String userCartId) {
        cartService.clear(userCartId);
    }

    @GetMapping("/generateId")
    public StringResponse generateId() {
        return new StringResponse(UUID.randomUUID().toString());
    }


}
