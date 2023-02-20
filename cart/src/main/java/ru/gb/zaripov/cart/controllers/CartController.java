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
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/{userCartId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) String username, @PathVariable String userCartId) {
        String currentCartId = selectCartId(username, userCartId);
        return cartConverter.toCartDto(cartService.getCurrentCart(currentCartId));
    }

    @GetMapping("/{userCartId}/add/{productId}")
    public void addProductToCart(@RequestHeader (required = false) String username, @PathVariable String userCartId, @PathVariable Long productId) {
        String currentCartId = selectCartId(username, userCartId);
        cartService.addToCart(currentCartId, productId);
    }

    @GetMapping("/clear/{username}")
    public void clear(@PathVariable String username) {
        cartService.clear(username);
    }

    @GetMapping("/generateId")
    public StringResponse generateId() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/name/{cartId}")
    public void nameCart(@PathVariable String cartId, @RequestHeader String username){
        cartService.assignName(cartId, username);
    }

    private static String selectCartId(String username, String userCartId) {
        return username == null ? userCartId : username;
    }
}
