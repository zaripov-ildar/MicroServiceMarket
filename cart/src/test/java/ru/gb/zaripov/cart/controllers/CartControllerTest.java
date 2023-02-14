package ru.gb.zaripov.cart.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.cart.integrations.ProductServiceIntegration;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartControllerTest {
    private final String USERNAME_1 = "bob";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;
    private ProductDto firstProduct;
    private ProductDto secondProduct;

    @BeforeEach
    void setUp() {
        firstProduct = new ProductDto(1L, "product1", BigDecimal.valueOf(10), "drinks");
        secondProduct = new ProductDto(2L, "product2", BigDecimal.valueOf(20), "food");
        given(productServiceIntegration.getProductDtoById(eq(2L))).willReturn(secondProduct);
        given(productServiceIntegration.getProductDtoById(eq(1L))).willReturn(firstProduct);
    }

    @Test
    @Order(1)
    void addWrongIdToCartTest() throws Exception {
        mvc.perform(get("/api/v1/cart/add/100500"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(2)
    void addOneProductToCartTest() throws Exception {
        mvc
                .perform(get("/api/v1/cart/add/1"))
                .andExpect(status().isOk());
        mvc.perform(get("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice", is(firstProduct.getPrice().intValue())));
    }

    @Test
    @Order(3)
    void addProductsAndCheckCart() throws Exception {
        BigDecimal sum = firstProduct.getPrice()
                .multiply(BigDecimal.valueOf(2))
                .add(secondProduct.getPrice());
        mvc.perform(get("/api/v1/cart/add/1"));
        mvc.perform(get("/api/v1/cart/add/2"));
        mvc.perform(get("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItems").isArray())
                .andExpect(jsonPath("$.cartItems", hasSize(2)))
                .andExpect(jsonPath("$.totalPrice", is(sum.intValue())))
                .andExpect(jsonPath("$.cartItems[0].productId", is(firstProduct.getId().intValue())))
                .andExpect(jsonPath("$.cartItems[0].productTitle", is(firstProduct.getTitle())))
                .andExpect(jsonPath("$.cartItems[0].pricePerProduct", is(firstProduct.getPrice().intValue())))
                .andExpect(jsonPath("$.cartItems[0].quantity", is(2)))
                .andExpect(jsonPath("$.cartItems[0].totalPrice", is(firstProduct.getPrice().intValue() * 2)));
    }

    @Test
    @Order(4)
    void clear() throws Exception {
        mvc.perform(get("/api/v1/cart/clear/" + USERNAME_1))
                .andExpect(status().isOk());
        mvc.perform(get("/api/v1/cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cartItems").isArray())
                .andExpect(jsonPath("$.cartItems", hasSize(0)))
                .andExpect(jsonPath("$.totalPrice", is(BigDecimal.ZERO.intValue())));
    }
}