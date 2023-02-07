package ru.gb.zaripov.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.zaripov.api.CartDto;
import ru.gb.zaripov.core.exceptions.ResourceNotFoundException;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public CartDto getUserCart(String username){
        return cartServiceWebClient
                .get()
                .uri("/api/v1/cart/0")
                .header("username", username)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ResourceNotFoundException.class)
                                .map(body->new ResourceNotFoundException("Something wrong on cart service: " + clientResponse.statusCode())) //FIXME: handle all possible exceptions
                )
                .bodyToMono(CartDto.class)
                .block();
    }
}
