package org.copyria2.order_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
@RequiredArgsConstructor
public class CarApiConfig {
    private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;
//    @Bean("userAuthApiClient")
//    public ApiClient
}
