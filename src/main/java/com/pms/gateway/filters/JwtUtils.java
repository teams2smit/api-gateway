package com.pms.gateway.filters;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String SECRET;

    //check if the token has expired
    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    // retrieve expiration date from jwt token
    public Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    //for retrieveing any information from token we will need the secret key
    public Claims extractAllClaims(String token){
        try {
            return Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e){
            throw new RuntimeException("INVALID_CREDENTIALS", e);
        } catch (ExpiredJwtException ex){
            throw ex;
        }
    }

    //validate token
    public Boolean isValidToken(String token) {
        return !isTokenExpired(token);
    }
}
