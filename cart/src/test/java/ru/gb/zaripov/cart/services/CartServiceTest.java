package ru.gb.zaripov.cart.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.api.exceptions.ResourceNotFoundException;
import ru.gb.zaripov.cart.integrations.ProductServiceIntegration;
import ru.gb.zaripov.cart.utils.Cart;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = CartService.class)
class CartServiceTest {

    private CartService cartService;
    @MockBean
    private ProductServiceIntegration productServiceIntegration;
    private Cart cart;

    @BeforeEach
    void setUp() {
        ProductDto firstProduct = new ProductDto(1L, "product #1", BigDecimal.ONE, "food");
        ProductDto secondProduct = new ProductDto(2L, "product #2", BigDecimal.TWO, "drinks");
        cart = new Cart();
        cart.add(firstProduct);
        cart.add(firstProduct);
        cart.add(secondProduct);
        given(productServiceIntegration.getProductDtoById(1L))
                .willReturn(firstProduct);
        given(productServiceIntegration.getProductDtoById(2L))
                .willReturn(secondProduct);
        cartService = new CartService(productServiceIntegration);
        cartService.init();
        cartService.addToCart(1L);
        cartService.addToCart(1L);
        cartService.addToCart(2L);
    }

    @Test
    void addToCart() {
        assertEquals(cart, cartService.getCurrentCart());
    }

    @Test
    void addWrongIdToCart() {
        assertThrows(ResourceNotFoundException.class, () -> cartService.addToCart(100500L));
    }

    @Test
    void clear() {
        cartService.clear("username");//FIXME: change when cartService structure will be explained
        Cart currentCart = cartService.getCurrentCart();
        assertTrue(currentCart.getItems().isEmpty());
        assertEquals(BigDecimal.ZERO, currentCart.getTotalPrice());
    }
}