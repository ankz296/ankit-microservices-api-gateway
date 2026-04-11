package dev.ankit.platform.api_gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final RateLimiterConfig rateLimiterConfig;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("user-service", r -> r
                        .path("/api/v1/auth/**")
                        .filters(f -> f
                                .requestRateLimiter(config -> {
                                    config.setRateLimiter(rateLimiterConfig.ipRateLimiter());
                                    config.setKeyResolver(rateLimiterConfig.ipKeyResolver());
                                    config.setDenyEmptyKey(false);
                                    config.setEmptyKeyStatus("429");
                                })
                        )
                        .uri("lb://user-service"))

                .route("user-service", r -> r
                        .path("/api/v1/users/**")
                        .filters(f -> f
                                .requestRateLimiter(config -> {
                                    config.setRateLimiter(rateLimiterConfig.userRateLimiter());
                                    config.setKeyResolver(rateLimiterConfig.userKeyResolver());
                                    config.setDenyEmptyKey(false);
                                    config.setEmptyKeyStatus("429");
                                })
                        )
                        .uri("lb://user-service"))

                .route("product-service", r -> r
                        .path("/api/v1/products/**")
                        .filters(f -> f
                                .requestRateLimiter(config -> {
                                    config.setRateLimiter(rateLimiterConfig.userRateLimiter());
                                    config.setKeyResolver(rateLimiterConfig.userKeyResolver());
                                    config.setDenyEmptyKey(false);
                                    config.setEmptyKeyStatus("429");
                                })
                        )
                        .uri("lb://product-service"))

                .route("order-service", r -> r
                        .path("/api/v1/orders/**")
                        .filters(f -> f
                                .requestRateLimiter(config -> {
                                    config.setRateLimiter(rateLimiterConfig.userRateLimiter());
                                    config.setKeyResolver(rateLimiterConfig.userKeyResolver());
                                    config.setDenyEmptyKey(false);
                                    config.setEmptyKeyStatus("429");
                                })
                        )
                        .uri("lb://order-service"))

                .route("payment-service", r -> r
                        .path("/api/v1/payments/**")
                        .filters(f -> f
                                .requestRateLimiter(config -> {
                                    config.setRateLimiter(rateLimiterConfig.userRateLimiter());
                                    config.setKeyResolver(rateLimiterConfig.userKeyResolver());
                                    config.setDenyEmptyKey(false);
                                    config.setEmptyKeyStatus("429");
                                })
                        )
                        .uri("lb://payment-service"))

                .build();
    }
}
