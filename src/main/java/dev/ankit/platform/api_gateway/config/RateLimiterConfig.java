package dev.ankit.platform.api_gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {

    /**
     * KEY RESOLVER — decides "kaun hai ye request?"
     *
     * Strategy:
     * - JWT header present hai (X-User-Id set by JwtAuthFilter) → userId use karo
     * - Anonymous request → IP address use karo
     *
     * WHY X-User-Id?
     * JwtAuthFilter already JWT validate karke X-User-Id header set karta hai.
     * Toh yahaan dobara JWT parse karne ki zaroorat nahi.
     */
    
    @Bean
    @Primary
    public KeyResolver userKeyResolver() {
        return exchange -> Mono.justOrEmpty(exchange.getRequest()
                        .getHeaders()
                        .getFirst("X-User-Id"))
                .filter(userId -> !userId.isBlank())
                .map(userId -> "user:" + userId)
                .switchIfEmpty(Mono.just("anonymous"));
    }

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> {
            String ip = exchange.getRequest().getRemoteAddress() != null
                    ? exchange.getRequest().getRemoteAddress().getAddress().getHostAddress()
                    : "unknown";
            return Mono.just("ip:" + ip);
        };
    }
    /**
     * Per-USER rate limiter — 100 req/min
     *
     * replenishRate = tokens per second refill = 100/60 ≈ 1.6 → 2 (round up)
     * burstCapacity = max tokens bucket mein = 100
     * requestedTokens = har request pe consume = 1
     */
    @Bean
    @Primary
    public RedisRateLimiter userRateLimiter() {
        return new RedisRateLimiter(2, 100, 1);
    }
    /**
     * Per-IP rate limiter — 200 req/min
     *
     * replenishRate = 200/60 ≈ 3
     * burstCapacity = 200
     */
    @Bean
    public RedisRateLimiter ipRateLimiter() {
        return new RedisRateLimiter(3, 200, 1);
    }
}
