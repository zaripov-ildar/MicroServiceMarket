package ru.gb.zaripov.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.zaripov.api.CartDto;
import ru.gb.zaripov.core.converters.CartItemConverter;
import ru.gb.zaripov.core.entities.Order;
import ru.gb.zaripov.core.repositories.OrderRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemConverter cartItemConverter;


    public void createNewOrder(String username, CartDto cartDto) {
        Order order = new Order();
        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setItemList(
                cartDto.getCartItems().stream()
                        .map(cartItemDto -> cartItemConverter.toOrderItem(cartItemDto, order))
                        .collect(Collectors.toList())
        );
        orderRepository.save(order);
    }
}
