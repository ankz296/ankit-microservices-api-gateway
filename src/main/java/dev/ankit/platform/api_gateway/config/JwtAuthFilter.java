package dev.ankit.platform.api_gateway.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
public class JwtAuthFilter implements WebFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${gateway.secret}")
    private String gatewaySecret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             WebFilterChain chain) {

        String path = exchange.getRequest()
                .getPath().toString();

        // Public paths — skip JWT check
        // But STILL add gateway secret!
        if (path.startsWith("/api/v1/auth/") ||
                path.startsWith("/actuator/")) {

            ServerWebExchange mutated = exchange.mutate()
                    .request(r -> r
                            .header("X-Gateway-Secret", gatewaySecret)
                    )
                    .build();
            return chain.filter(mutated);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // No token
        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String userId = claims.getSubject();
            String role   = claims.get("role", String.class);
            String email  = claims.get("email", String.class);

            log.debug("JWT valid — userId={}, role={}",
                    userId, role);

            // Downstream headers — userId + gateway secret
            ServerWebExchange mutatedExchange = exchange
                    .mutate()
                    .request(r -> r
                            .header("X-User-Id",        userId)
                            .header("X-User-Role",       role)
                            .header("X-User-Email",      email)
                            .header("X-Gateway-Secret",  gatewaySecret)
                    )
                    .build();

            // Security context set karo
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userId, null,
                            List.of(new SimpleGrantedAuthority(role))
                    );

            return chain.filter(mutatedExchange)
                    .contextWrite(
                            ReactiveSecurityContextHolder
                                    .withAuthentication(auth));

        } catch (ExpiredJwtException e) {
            log.warn("Access token expired");
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            exchange.getResponse()
                    .getHeaders()
                    .add("X-Token-Expired", "true");
            return exchange.getResponse().setComplete();

        } catch (Exception e) {
            log.warn("JWT validation failed: {}",
                    e.getMessage());
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }
}