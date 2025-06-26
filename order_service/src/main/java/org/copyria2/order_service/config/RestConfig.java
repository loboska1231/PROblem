package org.copyria2.order_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestConfig {
    @Value("${app.privat.url}")
    private  String baseUrl;
    @Bean
    public RestClient changeRestClient(){
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}
