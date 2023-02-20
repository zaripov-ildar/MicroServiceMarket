package ru.gb.zaripov.core.converters;

import org.springframework.stereotype.Component;
import ru.gb.zaripov.api.OrderItemDto;
import ru.gb.zaripov.core.entities.OrderItem;

@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem item) {
        return new OrderItemDto(
                item.getProduct().getId(),
                item.getProduct().getTitle(),
                item.getQuantity(),
                item.getPricePerProduct(),
                item.getPrice()
        );
    }
}
