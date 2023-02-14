package ru.gb.zaripov.cart.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.api.exceptions.ResourceNotFoundException;
import ru.gb.zaripov.cart.integrations.ProductServiceIntegration;
import ru.gb.zaripov.cart.utils.Cart;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productServiceIntegration;

    private Cart cart;

    @PostConstruct
    public void init() {
        cart = new Cart();
    }

    public Cart getCurrentCart() {
        return cart;
    }

    public void addToCart(Long productId) {
        ProductDto productDto = productServiceIntegration.getProductDtoById(productId);
        if (productDto == null) {
            throw new ResourceNotFoundException(String.format("Product with id %s doesn't exist", productId));
        }
        cart.add(productDto);
    }

    public void clear(String username) {
        cart.clear();
    }
}
