package ru.gb.zaripov.core.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.zaripov.api.CartDto;
import ru.gb.zaripov.api.OrderDto;
import ru.gb.zaripov.core.converters.OrderConverter;
import ru.gb.zaripov.core.integrations.CartServiceIntegration;
import ru.gb.zaripov.core.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestHeader String username){

        orderService.createNewOrder(username);

    }

    @GetMapping()
    public List<OrderDto> getOrders(@RequestHeader String username){
        return orderService.findByUserName(username)
                .stream()
                .map(orderConverter::toOrderDto)
                .toList();
    }
}
