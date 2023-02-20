package ru.gb.zaripov.cart.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.cart.integrations.ProductServiceIntegration;
import ru.gb.zaripov.cart.utils.Cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductServiceIntegration productServiceIntegration;
    private Map<String, Cart> carts;


    @PostConstruct
    public void init() {
        carts = new HashMap<>();
    }

    public Cart getCurrentCart(String cartId) {
        if (!carts.containsKey(cartId)) {
            carts.put(cartId, new Cart());
        }
        return carts.get(cartId);
    }

    public void addToCart(String cartId, Long productId) {
        execute(cartId, cart -> {
            ProductDto productDto = productServiceIntegration.getProductDtoById(productId);
            cart.add(productDto);

        });
    }

    public void removeFromCart(String cartId, Long productId) {
        execute(cartId, cart -> cart.remove(productId));
    }

    public void clear(String cartId) {
        execute(cartId, Cart::clear);
    }

    private void execute(String cartId, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartId);
        action.accept(cart);
    }

    public void assignName(String cartId, String username) {
        carts.put(username, carts.remove(cartId));
    }
}
