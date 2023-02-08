package ru.gb.zaripov.cart.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.gb.zaripov.api.ProductDto;
import ru.gb.zaripov.api.exceptions.ResourceNotFoundException;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productWebClient;


    public ProductDto getProductDtoById(Long productId) {
        return productWebClient
                .get()
                .uri("/api/v1/products/" + productId)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ResourceNotFoundException.class)
                                .map(body -> new ResourceNotFoundException("Something wrong on product service: " + clientResponse.statusCode())) //FIXME: handle all possible exceptions
                )
                .bodyToMono(ProductDto.class)
                .block();

    }
}
