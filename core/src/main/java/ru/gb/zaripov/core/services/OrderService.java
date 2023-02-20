package ru.gb.zaripov.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.zaripov.api.CartDto;
import ru.gb.zaripov.core.converters.CartItemConverter;
import ru.gb.zaripov.core.entities.Order;
import ru.gb.zaripov.core.integrations.CartServiceIntegration;
import ru.gb.zaripov.core.repositories.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemConverter cartItemConverter;
    private final CartServiceIntegration cartServiceIntegration;


    public void createNewOrder(String username) {
        CartDto cartDto = cartServiceIntegration.getUserCart(username);
        Order order = new Order();
        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setItemList(
                cartDto.getCartItems().stream()
                        .map(cartItemDto -> cartItemConverter.toOrderItem(cartItemDto, order))
                        .collect(Collectors.toList())
        );
        orderRepository.save(order);
        cartServiceIntegration.clear(username);
    }

    public List<Order> findByUserName(String username) {
        return orderRepository.findAllByUsername(username);
    }
}
