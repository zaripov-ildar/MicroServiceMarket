package ru.gb.zaripov.core.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.gb.zaripov.core.properties.CartServiceIntegrationProperties;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(
        CartServiceIntegrationProperties.class
)
@RequiredArgsConstructor
public class CoreAppConfig {
    private final CartServiceIntegrationProperties cartSIP;

    @Bean
    public WebClient cartServiceWebClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, cartSIP.getConnectTimeout())
                .doOnConnected(connection -> connection.addHandlerLast(new ReadTimeoutHandler(cartSIP.getReadTimeout(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(cartSIP.getWriteTimeout(), TimeUnit.MILLISECONDS)));

        return WebClient.builder()
                .baseUrl(cartSIP.getUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
