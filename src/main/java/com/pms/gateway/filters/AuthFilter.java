package com.pms.gateway.filters;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        if(routeValidator.isSecured.test(request)){
            if (isAuthMissing(request)){
                return onError(exchange, "Authorization header is missing in request", HttpStatus.UNAUTHORIZED);
            }

            final String token = request.getHeaders()
                    .getOrEmpty("Authorization")
                    .get(0)
                    .replace("Bearer ", "");

            if(!jwtUtils.isValidToken(token)){
                return onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
            }

            // Populate Request with Headers

            Claims claims = jwtUtils.extractAllClaims(token);

            exchange.getRequest().mutate()
                    .header("user", claims.getSubject())
                    .build();
        }

        return chain.filter(exchange);
    }


    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }


    private boolean isAuthMissing(ServerHttpRequest request){
        return !request.getHeaders().containsKey("Authorization");
    }
}
