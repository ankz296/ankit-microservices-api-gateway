package dev.ankit.platform.api_gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RequestLoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        long startTime = System.currentTimeMillis();

        log.info("Incoming request method={}, path={}", method, path);

        return chain.filter(exchange)
                .doOnSuccess(aVoid -> {
                    long duration = System.currentTimeMillis() - startTime;

                    log.info("Request completed method={}, path={}, durationMs={}",
                            method, path, duration);
                })
                .doOnError(error -> {
                    long duration = System.currentTimeMillis() - startTime;

                    log.error("Request failed method={}, path={}, durationMs={}, error={}",
                            method, path, duration, error.getMessage(), error);
                });
    }
}
