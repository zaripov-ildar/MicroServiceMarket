package ru.gb.zaripov.core.converters;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.zaripov.api.OrderDto;
import ru.gb.zaripov.core.entities.Order;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class OrderConverter {

    private final OrderItemConverter orderItemConverter;

    public OrderDto toOrderDto(Order order) {


        return new OrderDto(
                order.getId(),
                order.getItemList()
                        .stream()
                        .map(orderItemConverter::entityToDto)
                        .collect(Collectors.toList()), order.getTotalPrice());
    }
}
