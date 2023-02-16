package dev.sriharsha.WeBlog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper {
    private static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 30;

    @Value("${SECRET_KEY}")
    private String SECRET;

    /* Retrieve the Claims i.e. payload information from JWT Token
    using Jwts Parser by passing secret and the token */
    public Claims getTokenFromClaims(String token) {
        return Jwts.parser().setSigningKey(getSignKey())
                .parseClaimsJws(token.replace("{", "")
                        .replace("}", "")).getBody();
    }

    //Retrieve username from the token
    public String getUsernameFromToken(String token) {
        return getTokenFromClaims(token).getSubject();
    }

    //Retrieve expiration date from the token
    public Date getExpirationDateFromToken(String token) {
        return getTokenFromClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(username)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSignKey())
                .compact();
    }

    private Key getSignKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public boolean ValidateToken(String token, UserDetails userDetails) {
        String usernameFromToken = getUsernameFromToken(token);
        return (usernameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
