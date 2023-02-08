package ru.gb.zaripov.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.zaripov.api.CartDto;
import ru.gb.zaripov.core.integrations.CartServiceIntegration;
import ru.gb.zaripov.core.services.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {
    private final CartServiceIntegration cartServiceIntegration;
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestHeader String username){
        CartDto cartDto = cartServiceIntegration.getUserCart(username);
        orderService.createNewOrder(username, cartDto);
    }
}
