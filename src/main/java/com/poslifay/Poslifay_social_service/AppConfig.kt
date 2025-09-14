package com.poslifay.Poslifay_social_service

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
open class AppConfig {

    @Bean
    @LoadBalanced
    open fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder()
    }

    // JwkProvider için genel WebClient bean'i
    @Bean
    open fun webClient(): WebClient {
        return WebClient.builder().build()
    }

    // LoadBalanced WebClient bean'i (isteğe bağlı)
    @Bean
    @LoadBalanced
    open fun loadBalancedWebClient(webClientBuilder: WebClient.Builder): WebClient {
        return webClientBuilder.build()
    }
}