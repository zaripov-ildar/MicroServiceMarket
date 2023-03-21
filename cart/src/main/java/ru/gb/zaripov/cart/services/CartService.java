package ru.gb.zaripov.cart.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.cart.integrations.ProductServiceIntegration;
import ru.gb.zaripov.cart.utils.Cart;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    //вот он redis
    private final RedisTemplate<String, Object> redisTemplate;


    public Cart getCurrentCart(String cartId) {
        if (!redisTemplate.hasKey(cartId)) {
            redisTemplate.opsForValue().set(cartId, new Cart());
        }
        return (Cart)redisTemplate.opsForValue().get(cartId);
    }

    public void addToCart(String cartId, Long productId) {
        execute(cartId, cart -> {
            ProductDto productDto = productServiceIntegration.getProductDtoById(productId);
            cart.add(productDto);
        });
    }

    public void clear(String cartId) {
        execute(cartId, Cart::clear);
    }

    private void execute(String cartId, Consumer<Cart> action) {
        Cart cart = getCurrentCart(cartId);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartId, cart);
    }
}
