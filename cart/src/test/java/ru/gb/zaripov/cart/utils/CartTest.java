package ru.gb.zaripov.cart.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.gb.zaripov.api.ProductDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    private static Cart cart;
    private static ProductDto firstProduct;
    private static ProductDto secondProduct;

    @BeforeAll
    static void setUp(){
        firstProduct = new ProductDto(1L, "product1", BigDecimal.TEN, "drinks");
        secondProduct = new ProductDto(2L, "product2", BigDecimal.ONE, "food");
        cart = new Cart();
        cart.setItems(new ArrayList<>());
        cart.setTotalPrice(BigDecimal.ZERO);
        cart.add(firstProduct);
        cart.add(firstProduct);
        cart.add(secondProduct);
    }

    @Test
    void add() {
        BigDecimal totalPriceExpected = firstProduct.getPrice()
                .multiply(BigDecimal.valueOf(2))
                .add(secondProduct.getPrice());
        CartItem firstCartItem = new CartItem(
                firstProduct.getId(),
                firstProduct.getTitle(),
                2,
                firstProduct.getPrice(),
                firstProduct.getPrice().multiply(BigDecimal.TWO));
        CartItem secondCartItem = new CartItem(
                secondProduct.getId(),
                secondProduct.getTitle(),
                1,
                secondProduct.getPrice(),
                secondProduct.getPrice());
        List<CartItem> expectedCartItems = new ArrayList<>();
        expectedCartItems.add(firstCartItem);
        expectedCartItems.add(secondCartItem);
        assertEquals(totalPriceExpected, cart.getTotalPrice());
        assertEquals(2, cart.getItems().size());
        assertEquals(expectedCartItems, cart.getItems());
    }

    @Test
    void clear() {
        cart.clear();
        assertTrue(cart.getItems().isEmpty());
        assertEquals(BigDecimal.ZERO, cart.getTotalPrice());
    }
}