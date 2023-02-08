package ru.gb.zaripov.cart.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gb.zaripov.api.CartDto;
import ru.gb.zaripov.cart.converters.CartConverter;
import ru.gb.zaripov.cart.services.CartService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
@CrossOrigin("*")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;
    @GetMapping
    public CartDto getCurrentCart(){
        return cartConverter.toCartDto(cartService.getCurrentCart());
    }

    @GetMapping("/add/{productId}")
    public void addProductToCart(@PathVariable Long productId){
        cartService.addToCart(productId);
    }
    @GetMapping("/clear")
    public void clear(){
        cartService.clear();
    }
}
