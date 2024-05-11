package org.eventhub.main.config;

import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.eventhub.main.dto.AuthenticationResponce;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "iPKnNcxX01Ri/mf4dCFbMGX9rIMiYUL38jrne4m2+MM82wVqUzU7sTDDG43i6CUtChYxk/nyko7sWpcgoF1rZFnQvCbxXrmt9bVWgwkeMz8JnVDbHfNwEnPlqIwG59jCiEAEwUQZlTd7ZJg+VkSdZE/21b0cc1YNoEzeWMBdWvZtb5GQoeaAJ1u7bJ+AB6y0JZQLy0Jw1Pqvh1R/cNA17M+4IjDY/3xofCaTEaCIGaBP2pmpavRfUTfSDqHcSEdP9lshpeLfTTT5zQOrKYJUKM1hBIufEVIBvHO/pb1GRrCWcfkXY5KMhIxPkwP0XMA12V/9wbcGl+EyC63brrrKlq8Yatmhcw4r69nu5tgiZok=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public UUID getId(String token) {
        String validToken = token.substring(7);
        String id = extractClaim(validToken, claims -> {
            return claims.get("id", String.class);
        });
        return UUID.fromString(id);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 120))
                .signWith(getSingInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public Date expDate(String token) {
        return extractExpiration(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSingInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
