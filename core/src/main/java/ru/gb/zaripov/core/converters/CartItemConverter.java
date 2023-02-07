package ru.gb.zaripov.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.zaripov.api.CartItemDto;
import ru.gb.zaripov.core.entities.Order;
import ru.gb.zaripov.core.entities.OrderItem;
import ru.gb.zaripov.core.services.ProductService;

@RequiredArgsConstructor
@Component
public class CartItemConverter {
    private final ProductService productService;

    public OrderItem toOrderItem(CartItemDto cartItemDto, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setPricePerProduct(cartItemDto.getPrice());
        orderItem.setQuantity(cartItemDto.getQuantity());
        orderItem.setPrice(cartItemDto.getTotalPrice());
        orderItem.setProduct(
                productService.findById(cartItemDto.getProductId())
        );
        return orderItem;
    }
}
